

import com.ionoscloud.s3.ApiClient;
import com.ionoscloud.s3.SnowballObject;
import com.ionoscloud.s3.UploadSnowballObjectsArgs;
import com.ionoscloud.s3.errors.ApiException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UploadSnowballObjects {
  /** ApiClient.uploadSnowballObjects() example. */
  public static void main(String[] args)
      throws IOException, NoSuchAlgorithmException, InvalidKeyException {
    try {
      /* play.min.io for test and development. */
      ApiClient apiClient =
          ApiClient.builder()
              .endpoint("https://play.min.io")
              .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
              .build();

      /* Amazon S3: */
      // ApiClient apiClient =
      //     ApiClient.builder()
      //         .endpoint("https://s3.amazonaws.com")
      //         .credentials("YOUR-ACCESSKEY", "YOUR-SECRETACCESSKEY")
      //         .build();

      // Upload snowball objects.
      List<SnowballObject> objects = new ArrayList<SnowballObject>();
      objects.add(
          new SnowballObject(
              "my-object-one",
              new ByteArrayInputStream("hello".getBytes(StandardCharsets.UTF_8)),
              5,
              null));
      objects.add(
          new SnowballObject(
              "my-object-two",
              new ByteArrayInputStream("java".getBytes(StandardCharsets.UTF_8)),
              4,
              null));
      apiClient.uploadSnowballObjects(
          UploadSnowballObjectsArgs.builder().bucket("my-bucketname").objects(objects).build());
      System.out.println("my-object-one and my-object-two are successfully uploaded");
    } catch (ApiException e) {
      System.out.println("Error occurred: " + e);
    }
  }
}
