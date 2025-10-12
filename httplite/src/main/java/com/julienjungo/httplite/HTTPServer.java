package com.julienjungo.httplite;

import com.julienjungo.httplite.model.Handler;
import com.julienjungo.httplite.model.Request;
import com.julienjungo.httplite.model.Response;
import com.julienjungo.httplite.model.ServerPort;
import com.julienjungo.httplite.tls.TLSServerConfig;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HTTPServer {

  private final HttpServer server;

  private HTTPServer(int port) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.setExecutor(null);
    this.server = server;
  }

  private HTTPServer(int port, HttpsConfigurator config) throws IOException {
    HttpsServer server = HttpsServer.create(new InetSocketAddress(port), 0);
    server.setHttpsConfigurator(config);
    server.setExecutor(null);
    this.server = server;
  }

  public void start() {
    this.server.start();
  }

  public void handle(String path, Handler handler) {
    this.server.createContext(path, exchange -> {
      Request request = new Request();
      Response response = new Response();

      handler.handle(request, response);

      if (response.getStatusCode() == null) {
        throw new IllegalStateException();
      }

      if (response.getContentType() == null && response.getBody() == null) {
        return;
      }

      if (response.getContentType() == null || response.getBody() == null) {
        throw new IllegalStateException();
      }

      exchange.getResponseHeaders().add("Content-Type", response.getContentType().value());
      exchange.sendResponseHeaders(response.getStatusCode().value(), response.getLength());

      try (OutputStream out = exchange.getResponseBody()) {
        out.write(response.getBody());
      }
    });
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private ServerPort port;
    private TLSServerConfig tlsConfig;

    private Builder() {}

    public Builder withPort(ServerPort port) {
      this.port = port;
      return this;
    }

    public Builder withTLS(TLSServerConfig tlsConfig) {
      this.tlsConfig = tlsConfig;
      return this;
    }

    public HTTPServer build() throws Exception {
      if (this.port == null) {
        throw new IllegalStateException();
      }

      if (this.tlsConfig == null) {
        return new HTTPServer(this.port.value());
      }

      return new HTTPServer(this.port.value(), new HttpsConfigurator(this.tlsConfig.sslContext()));
    }
  }
}
