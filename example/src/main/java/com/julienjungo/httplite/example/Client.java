package com.julienjungo.httplite.example;

import com.julienjungo.httplite.HTTPClient;
import com.julienjungo.httplite.model.Response;
import com.julienjungo.httplite.tls.P12TrustStoreConfig;
import com.julienjungo.httplite.tls.TLSClientConfig;

import java.net.URL;

public class Client {

  public static void main(String[] args) throws Exception {
    String trustStorePath = System.getenv("TRUSTSTORE_PATH");
    String trustStorePass = System.getenv("TRUSTSTORE_PASS");

    if (trustStorePath == null) {
      trustStorePath = getDefaultTrustStorePath();
    }

    if (trustStorePass == null) {
      trustStorePass = getDefaultTrustStorePass();
    }

    TLSClientConfig tlsConfig = new P12TrustStoreConfig(
            trustStorePath,
            trustStorePass
    );

    HTTPClient client = HTTPClient.builder()
            .withTLS(tlsConfig)
            .build();

    Response res = client.get("https://localhost:8080");

    System.out.println("Response: " + new String(res.getBody()));
  }

  private static String getDefaultTrustStorePath() {
    URL trustStoreURL = Client.class.getResource("truststore.p12");
    return trustStoreURL == null ? null : trustStoreURL.getPath();
  }

  private static String getDefaultTrustStorePass() {
    return "changeit";
  }
}
