

package com.ionoscloud.s3.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/** Helper class to denote event metadata for {@link EventMetadata}. */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
    value = {"UwF", "UuF"},
    justification =
        "Everything in this class is initialized by JSON unmarshalling "
            + "and s3SchemaVersion/configurationId are available for completeness.")
public class EventMetadata {
  @JsonProperty private String s3SchemaVersion;
  @JsonProperty private String configurationId;
  @JsonProperty private BucketMetadata bucket;
  @JsonProperty private ObjectMetadata object;

  public String bucketName() {
    if (bucket == null) {
      return null;
    }

    return bucket.name();
  }

  public String bucketOwner() {
    if (bucket == null) {
      return null;
    }

    return bucket.owner();
  }

  public String bucketArn() {
    if (bucket == null) {
      return null;
    }

    return bucket.arn();
  }

  public String objectName() {
    if (object == null) {
      return null;
    }

    return object.key();
  }

  public long objectSize() {
    if (object == null) {
      return -1;
    }

    return object.size();
  }

  public String etag() {
    if (object == null) {
      return null;
    }

    return object.etag();
  }

  public String objectVersionId() {
    if (object == null) {
      return null;
    }

    return object.versionId();
  }

  public String sequencer() {
    if (object == null) {
      return null;
    }

    return object.sequencer();
  }

  public Map<String, String> userMetadata() {
    if (object == null) {
      return null;
    }

    return object.userMetadata();
  }
}
