package org.jws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.jws.activity.GetPageActivity;
import org.jws.activity.WritePageActivity;
import org.jws.exception.ValidationException;
import org.jws.model.*;
import org.jws.model.Error;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.Executors;

import static org.jws.exception.Messages.FIELD_MUST_NOT_BE_BLANK;
import static org.jws.exception.Messages.PAGE_DOES_NOT_EXIST;
import static org.jws.validation.RequestValidator.validateGetPageRequest;
import static org.jws.validation.RequestValidator.validateWritePageRequest;

@Log4j2
public class PagesServer {
    private final ObjectMapper objectMapper;
    private final WritePageActivity writePageActivity;
    private final GetPageActivity getPageActivity;

    @Inject
    public PagesServer(final WritePageActivity writePageActivity, final GetPageActivity getPageActivity, final ObjectMapper objectMapper) {
        this.writePageActivity = writePageActivity;
        this.getPageActivity = getPageActivity;
        this.objectMapper = objectMapper;
    }

    public void start() throws IOException {
        log.info("Starting Pages server");
        final HttpServer server = HttpServer.create(new InetSocketAddress(8080), 2);
        server.createContext("/pages/writePage", this::handleWritePage);
        server.createContext("/pages/getPage", this::handleGetPage);
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
        log.info("Pages server has started");
    }

    private void handleGetPage(final HttpExchange httpExchange) throws IOException {
        try {
            setRequestIdInThreadContext();
            log.info("Starting GetPage handler.");
            if ("POST".equals(httpExchange.getRequestMethod())) {
                final InputStream inputStream = httpExchange.getRequestBody();
                final GetPageRequest request = objectMapper.readValue(inputStream, GetPageRequest.class);
                validateGetPageRequest(request);
                final GetPageResponse response = getPageActivity.get(request);
                sendResponse(httpExchange, HttpURLConnection.HTTP_ACCEPTED, response);
                log.info("Finished GetPage handler with success.");
            } else {
                log.info("GetPage request sent but is not POST. Returning 404.");
                sendError(httpExchange, HttpURLConnection.HTTP_NOT_FOUND, "Request must be POST");
            }
        } catch (final ValidationException e) {
            log.info("Encountered validation exception in GetPage handler [{}]", e.toString());
            if (e.getMessage().contains(FIELD_MUST_NOT_BE_BLANK)) {
                sendError(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST, e.getMessage());
            } else if (e.getMessage().contains(PAGE_DOES_NOT_EXIST)) {
                sendError(httpExchange, HttpURLConnection.HTTP_NOT_FOUND, e.getMessage());
            }
        } catch (final Exception e) {
            log.error("Failed to get page with exception {}", e.toString());
            sendError(httpExchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Encountered error. " +
                    "Please try your request again.");
        } finally {
            clearRequestId();
        }
    }

    private void handleWritePage(final HttpExchange httpExchange) throws IOException {
        try {
            setRequestIdInThreadContext();
            log.info("Starting WritePage activity.");
            if ("POST".equals(httpExchange.getRequestMethod())) {
                final InputStream inputStream = httpExchange.getRequestBody();
                final WritePageRequest request = objectMapper.readValue(inputStream, WritePageRequest.class);
                validateWritePageRequest(request);
                final WritePageResponse response = writePageActivity.write(request);
                sendResponse(httpExchange, HttpURLConnection.HTTP_ACCEPTED, response);
                log.info("Finished WritePage activity with success.");
            } else {
                log.info("WritePage request sent but is not POST. Returning 404.");
                sendError(httpExchange, HttpURLConnection.HTTP_NOT_FOUND, "Request must be POST");
            }
        } catch (final ValidationException e) {
            log.info("Encountered validation exception in WritePage handler [{}]", e.toString());
            if (e.getMessage().contains(FIELD_MUST_NOT_BE_BLANK)) {
                sendError(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST, e.getMessage());
            }
        } catch (final Exception e) {
            log.error("Failed to write page with exception {}", e.toString());
            sendError(httpExchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Encountered error. " +
                    "Please try your request again.");
        } finally {
            clearRequestId();
        }
    }

    private void sendError(final HttpExchange httpExchange, final int statusCode, final String errorMessage) throws IOException {
        final Error error = ImmutableError.builder().error(errorMessage).build();
        sendResponse(httpExchange, statusCode, error);
    }

    private void sendResponse(final HttpExchange httpExchange, final int statusCode, final ReturnableModel model) throws IOException {
        try {
            final byte[] responseBytes = objectMapper.writeValueAsBytes(model);
            httpExchange.sendResponseHeaders(statusCode, responseBytes.length);
            final OutputStream responseBody = httpExchange.getResponseBody();
            responseBody.write(responseBytes);
            responseBody.close();
        } catch (Exception e) {
            log.error("Failed to send response. {}", e.toString());
        }
    }

    private void setRequestIdInThreadContext() {
        final String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
    }

    private void clearRequestId() {
        ThreadContext.clearAll();
    }
}
