package com.ionoscloud.s3.messages;

import java.io.Serializable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/** Object representation of error response XML of any S3 REST APIs. */
@Root(name = "ErrorResponse", strict = false)
@Namespace(reference = "http://s3.amazonaws.com/doc/2006-03-01/")
public class ErrorResponse implements Serializable {
  private static final long serialVersionUID = 1905162041950251407L; // fix SE_BAD_FIELD

  @Element(name = "Code")
  protected String code;

  @Element(name = "Message", required = false)
  protected String message;

  @Element(name = "BucketName", required = false)
  protected String bucketName;

  @Element(name = "Key", required = false)
  protected String objectName;

  @Element(name = "Resource", required = false)
  protected String resource;

  @Element(name = "RequestId", required = false)
  protected String requestId;

  @Element(name = "HostId", required = false)
  protected String hostId;

  public ErrorResponse() {}

  /**
   * Constructs a new ErrorResponse object with error code, bucket name, object name, resource,
   * request ID and host ID.
   */
  public ErrorResponse(
      String code,
      String message,
      String bucketName,
      String objectName,
      String resource,
      String requestId,
      String hostId) {
    this.code = code;
    this.message = message;
    this.bucketName = bucketName;
    this.objectName = objectName;
    this.resource = resource;
    this.requestId = requestId;
    this.hostId = hostId;
  }

  /** Returns error code. */
  public String code() {
    return this.code;
  }

  /** Returns error message. */
  public String message() {
    return this.message;
  }

  /** Returns bucket name. */
  public String bucketName() {
    return bucketName;
  }

  /** Returns object name. */
  public String objectName() {
    return objectName;
  }

  /** Returns host ID. */
  public String hostId() {
    return hostId;
  }

  /** Returns request ID. */
  public String requestId() {
    return requestId;
  }

  /** Returns resource. */
  public String resource() {
    return resource;
  }

  /** Returns string representation of this object. */
  public String toString() {
    return "ErrorResponse(code = "
        + code
        + ", "
        + "message = "
        + message
        + ", "
        + "bucketName = "
        + bucketName
        + ", "
        + "objectName = "
        + objectName
        + ", "
        + "resource = "
        + resource
        + ", "
        + "requestId = "
        + requestId
        + ", "
        + "hostId = "
        + hostId
        + ")";
  }
}
