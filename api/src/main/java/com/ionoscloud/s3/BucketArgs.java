

package com.ionoscloud.s3;

import com.ionoscloud.s3.http.HttpUtils;
import com.ionoscloud.s3.org.apache.commons.validator.routines.InetAddressValidator;
import java.util.Objects;
import java.util.regex.Pattern;

/** Base argument class holds bucket name and region. */
public abstract class BucketArgs extends BaseArgs {
  protected String bucketName;
  protected String region;

  public String bucket() {
    return bucketName;
  }

  public String region() {
    return region;
  }

  /** Base argument builder class for {@link BucketArgs}. */
  public abstract static class Builder<B extends Builder<B, A>, A extends BucketArgs>
      extends BaseArgs.Builder<B, A> {
    private static final Pattern BUCKET_NAME_REGEX =
        Pattern.compile("^[a-z0-9][a-z0-9\\.\\-]{1,61}[a-z0-9]$");

    protected void validateBucketName(String name) {
      validateNotNull(name, "bucket name");

      if (!BUCKET_NAME_REGEX.matcher(name).find()) {
        throw new IllegalArgumentException(
            "bucket name '"
                + name
                + "' does not follow Amazon S3 standards. For more information refer "
                + "https://docs.aws.amazon.com/AmazonS3/latest/userguide/bucketnamingrules.html");
      }

      if (InetAddressValidator.getInstance().isValidInet4Address(name)) {
        throw new IllegalArgumentException(
            "bucket name '" + name + "' must not be formatted as an IP address");
      }

      if (name.contains("..") || name.contains(".-") || name.contains("-.")) {
        throw new IllegalArgumentException(
            "bucket name '" + name + "' cannot contain successive characters '..', '.-' and '-.'");
      }
    }

    private void validateRegion(String region) {
      if (region != null && !HttpUtils.REGION_REGEX.matcher(region).find()) {
        throw new IllegalArgumentException("invalid region " + region);
      }
    }

    @Override
    protected void validate(A args) {
      validateBucketName(args.bucketName);
    }

    @SuppressWarnings("unchecked") // Its safe to type cast to B as B extends this class.
    public B bucket(String name) {
      validateBucketName(name);
      operations.add(args -> args.bucketName = name);
      return (B) this;
    }

    @SuppressWarnings("unchecked") // Its safe to type cast to B as B extends this class.
    public B region(String region) {
      validateRegion(region);
      operations.add(args -> args.region = region);
      return (B) this;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BucketArgs)) return false;
    if (!super.equals(o)) return false;
    BucketArgs that = (BucketArgs) o;
    return Objects.equals(bucketName, that.bucketName) && Objects.equals(region, that.region);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), bucketName, region);
  }
}
