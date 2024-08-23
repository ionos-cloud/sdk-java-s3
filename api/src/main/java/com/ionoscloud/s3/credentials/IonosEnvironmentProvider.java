package com.ionoscloud.s3.credentials;

/** Credential provider using IONOS server specific environment variables. */
public class IonosEnvironmentProvider extends EnvironmentProvider {
  @Override
  public Credentials fetch() {
    return new Credentials(
        getProperty("IONOS_S3_ACCESS_KEY"), getProperty("IONOS_S3_SECRET_KEY"), null, null);
  }
}
