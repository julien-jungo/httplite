package com.julienjungo.httplite.model;

public record ContentType(String value) {
  public static final ContentType APPLICATION_JSON = new ContentType("application/json");
}
