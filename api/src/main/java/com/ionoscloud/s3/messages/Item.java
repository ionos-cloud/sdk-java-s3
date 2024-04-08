

package com.ionoscloud.s3.messages;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Map;
import org.simpleframework.xml.Element;

/**
 * Helper class to denote Object information in {@link ListBucketResultV1}, {@link
 * ListBucketResultV2} and {@link ListVersionsResult}.
 */
public abstract class Item {
  private static final String UTF_8 = StandardCharsets.UTF_8.toString();

  @Element(name = "ETag", required = false)
  private String etag; // except DeleteMarker

  @Element(name = "Key")
  private String objectName;

  @Element(name = "LastModified")
  private ResponseDate lastModified;

  @Element(name = "Owner", required = false)
  private Owner owner;

  @Element(name = "Size", required = false)
  private long size; // except DeleteMarker

  @Element(name = "StorageClass", required = false)
  private String storageClass; // except DeleteMarker

  @Element(name = "IsLatest", required = false)
  private boolean isLatest; // except ListObjects V1

  @Element(name = "VersionId", required = false)
  private String versionId; // except ListObjects V1

  @Element(name = "UserMetadata", required = false)
  private Metadata userMetadata;

  @Element(name = "UserTags", required = false)
  private String userTags;

  private boolean isDir = false;
  private String encodingType = null;

  public Item() {}

  /** Constructs a new Item for prefix i.e. directory. */
  public Item(String prefix) {
    this.objectName = prefix;
    this.isDir = true;
  }

  public void setEncodingType(String encodingType) {
    this.encodingType = encodingType;
  }

  /** Returns object name. */
  public String objectName() {
    try {
      return "url".equals(encodingType) ? URLDecoder.decode(objectName, UTF_8) : objectName;
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  /** Returns last modified time of the object. */
  public ZonedDateTime lastModified() {
    return lastModified.zonedDateTime();
  }

  /** Returns ETag of the object. */
  public String etag() {
    return etag;
  }

  /** Returns object size. */
  public long size() {
    return size;
  }

  /** Returns storage class of the object. */
  public String storageClass() {
    return storageClass;
  }

  /** Returns owner object of given the object. */
  public Owner owner() {
    return owner;
  }

  /** Returns user metadata. */
  public Map<String, String> userMetadata() {
    return (userMetadata == null) ? null : userMetadata.get();
  }

  public String userTags() {
    return userTags;
  }

  /** Returns whether this version ID is latest. */
  public boolean isLatest() {
    return isLatest;
  }

  /** Returns version ID. */
  public String versionId() {
    return versionId;
  }

  /** Returns whether this item is a directory or not. */
  public boolean isDir() {
    return isDir;
  }

  /** Returns whether this item is a delete marker or not. */
  public boolean isDeleteMarker() {
    return this instanceof DeleteMarker;
  }
}