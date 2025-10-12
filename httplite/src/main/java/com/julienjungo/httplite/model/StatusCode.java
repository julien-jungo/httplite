package com.julienjungo.httplite.model;

public record StatusCode(int value) {
  public static final StatusCode OK = new StatusCode(200);
}
