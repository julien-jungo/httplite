package com.julienjungo.httplite.tls;

import javax.net.ssl.SSLContext;

public sealed interface TLSServerConfig permits P12KeyStoreConfig {
  SSLContext sslContext() throws Exception;
}
