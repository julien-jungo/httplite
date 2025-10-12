package com.julienjungo.httplite.tls;

import javax.net.ssl.SSLContext;

public sealed interface TLSClientConfig permits P12TrustStoreConfig {
  SSLContext sslContext() throws Exception;
}
