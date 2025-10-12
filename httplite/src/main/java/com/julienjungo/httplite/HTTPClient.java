package com.julienjungo.httplite;

import com.julienjungo.httplite.model.Response;
import com.julienjungo.httplite.model.StatusCode;
import com.julienjungo.httplite.tls.TLSClientConfig;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPClient {

  private final HttpClient client;

  private HTTPClient() {
    this.client = HttpClient.newHttpClient();
  }

  private HTTPClient(SSLContext sslContext) {
    this.client = HttpClient.newBuilder().sslContext(sslContext).build();
  }

  public Response get(String url) throws IOException, InterruptedException {
    HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

    HttpResponse<byte[]> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());

    Response response = new Response();
    response.setStatusCode(new StatusCode(httpResponse.statusCode()));
    response.setBody(httpResponse.body());

    return response;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private TLSClientConfig tlsConfig;

    private Builder() {}

    public Builder withTLS(TLSClientConfig tlsConfig) {
      this.tlsConfig = tlsConfig;
      return this;
    }

    public HTTPClient build() throws Exception {
      if (this.tlsConfig == null) {
        return new HTTPClient();
      }

      return new HTTPClient(this.tlsConfig.sslContext());
    }
  }
}
