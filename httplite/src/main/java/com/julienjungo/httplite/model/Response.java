package com.julienjungo.httplite.model;

public class Response {

  private StatusCode statusCode;
  private ContentType contentType;

  private byte[] body;

  public void setStatusCode(StatusCode statusCode) {
    this.statusCode = statusCode;
  }

  public void setContentType(ContentType contentType) {
    this.contentType = contentType;
  }

  public void setBody(byte[] body) {
    this.body = body.clone();
  }

  public StatusCode getStatusCode() {
    return this.statusCode;
  }

  public ContentType getContentType() {
    return this.contentType;
  }

  public byte[] getBody() {
    return this.body;
  }

  public int getLength() {
    return this.body == null ? 0 : this.body.length;
  }
}
