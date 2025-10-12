package com.julienjungo.httplite.model;

public record ServerPort(int value) {
  public static ServerPort DEFAULT = new ServerPort(8080);
}
