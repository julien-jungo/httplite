package com.julienjungo.httplite.example;

import com.julienjungo.httplite.model.ContentType;
import com.julienjungo.httplite.model.ServerPort;
import com.julienjungo.httplite.model.StatusCode;
import com.julienjungo.httplite.HTTPServer;
import com.julienjungo.httplite.tls.P12KeyStoreConfig;
import com.julienjungo.httplite.tls.TLSServerConfig;

import java.net.URL;

public class Server {

  public static void main(String[] args) throws Exception {
    String keyStorePath = System.getenv("KEYSTORE_PATH");
    String keyStorePass = System.getenv("KEYSTORE_PASS");

    if (keyStorePath == null) {
      keyStorePath = getDefaultKeyStorePath();
    }

    if (keyStorePass == null) {
      keyStorePass = getDefaultKeyStorePass();
    }

    ServerPort serverPort = ServerPort.DEFAULT;

    TLSServerConfig tlsConfig = new P12KeyStoreConfig(
            keyStorePath,
            keyStorePass
    );

    HTTPServer server = HTTPServer.builder()
            .withPort(serverPort)
            .withTLS(tlsConfig)
            .build();

    server.handle("/", (_, res) -> {
      res.setStatusCode(StatusCode.OK);
      res.setContentType(ContentType.APPLICATION_JSON);
      res.setBody("{\"msg\": \"Hello World\"}".getBytes());
    });

    server.start();

    System.out.println("Server started on port " + serverPort.value());
  }

  private static String getDefaultKeyStorePath() {
    URL keyStoreURL = Server.class.getResource("keystore.p12");
    return keyStoreURL == null ? null : keyStoreURL.getPath();
  }

  private static String getDefaultKeyStorePass() {
    return "changeit";
  }
}
