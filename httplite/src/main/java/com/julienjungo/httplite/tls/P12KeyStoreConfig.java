package com.julienjungo.httplite.tls;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;

public record P12KeyStoreConfig(String keyStorePath, String keyStorePassword) implements TLSServerConfig {

  @Override
  public SSLContext sslContext() throws Exception {

    KeyStore keyStore = KeyStore.getInstance("PKCS12");
    try (FileInputStream in = new FileInputStream(keyStorePath)) {
      keyStore.load(in, keyStorePassword.toCharArray());
    }

    String defaultAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(defaultAlgorithm);
    keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

    return sslContext;
  }
}
