package com.julienjungo.httplite.tls;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

public record P12TrustStoreConfig (String trustStorePath, String trustStorePassword) implements TLSClientConfig {

  @Override
  public SSLContext sslContext() throws Exception {

    KeyStore trustStore = KeyStore.getInstance("PKCS12");
    try (FileInputStream in = new FileInputStream(trustStorePath)) {
      trustStore.load(in, trustStorePassword.toCharArray());
    }

    String defaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(defaultAlgorithm);
    trustManagerFactory.init(trustStore);

    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

    return sslContext;
  }
}
