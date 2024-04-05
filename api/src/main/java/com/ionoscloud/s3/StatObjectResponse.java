

package com.ionoscloud.s3;

import com.ionoscloud.s3.messages.LegalHold;
import com.ionoscloud.s3.messages.ResponseDate;
import com.ionoscloud.s3.messages.RetentionMode;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import okhttp3.Headers;

/** Response of {@link S3Base#statObjectAsync}. */
public class StatObjectResponse extends GenericResponse {
  private String etag;
  private long size;
  private ZonedDateTime lastModified;
  private RetentionMode retentionMode;
  private ZonedDateTime retentionRetainUntilDate;
  private LegalHold legalHold;
  private boolean deleteMarker;
  private Map<String, String> userMetadata;

  public StatObjectResponse(Headers headers, String bucket, String region, String object) {
    super(headers, bucket, region, object);
    String value;

    value = headers.get("ETag");
    this.etag = (value != null ? value.replaceAll("\"", "") : "");

    value = headers.get("Content-Length");
    this.size = (value != null ? Long.parseLong(value) : -1);

    this.lastModified =
        ZonedDateTime.parse(headers.get("Last-Modified"), Time.HTTP_HEADER_DATE_FORMAT);

    value = headers.get("x-amz-object-lock-mode");
    this.retentionMode = (value != null ? RetentionMode.valueOf(value) : null);

    value = headers.get("x-amz-object-lock-retain-until-date");
    this.retentionRetainUntilDate =
        (value != null ? ResponseDate.fromString(value).zonedDateTime() : null);

    this.legalHold = new LegalHold("ON".equals(headers.get("x-amz-object-lock-legal-hold")));

    this.deleteMarker = Boolean.parseBoolean(headers.get("x-amz-delete-marker"));

    Map<String, String> userMetadata = new HashMap<>();
    for (String key : headers.names()) {
      if (key.toLowerCase(Locale.US).startsWith("x-amz-meta-")) {
        userMetadata.put(
            key.toLowerCase(Locale.US).substring("x-amz-meta-".length(), key.length()),
            headers.get(key));
      }
    }

    this.userMetadata = Collections.unmodifiableMap(userMetadata);
  }

  public String etag() {
    return etag;
  }

  public long size() {
    return size;
  }

  public ZonedDateTime lastModified() {
    return lastModified;
  }

  public RetentionMode retentionMode() {
    return retentionMode;
  }

  public ZonedDateTime retentionRetainUntilDate() {
    return retentionRetainUntilDate;
  }

  public LegalHold legalHold() {
    return legalHold;
  }

  public boolean deleteMarker() {
    return deleteMarker;
  }

  public String versionId() {
    return this.headers().get("x-amz-version-id");
  }

  public String contentType() {
    return this.headers().get("Content-Type");
  }

  public Map<String, String> userMetadata() {
    return userMetadata;
  }

  @Override
  public String toString() {
    return "ObjectStat{"
        + "bucket="
        + bucket()
        + ", object="
        + object()
        + ", last-modified="
        + lastModified
        + ", size="
        + size
        + "}";
  }
}
