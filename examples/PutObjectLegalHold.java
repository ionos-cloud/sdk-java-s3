import com.ionoscloud.s3.ApiClient;
import com.ionoscloud.s3.PutObjectLegalHoldArgs;
import com.ionoscloud.s3.errors.ApiException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class PutObjectLegalHold {
  /** ApiClient.putObjectLegalHold() example. */
  public static void main(String[] args)
      throws IOException, NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException {
    try {

      ApiClient apiClient =
          ApiClient.builder()
              .endpoint(System.getenv("IONOS_API_URL"))
              .credentials(
                  System.getenv("IONOS_S3_ACCESS_KEY"), System.getenv("IONOS_S3_SECRET_KEY"))
              .build();

      /* Amazon S3: */
      // ApiClient apiClient =
      //     ApiClient.builder()
      //         .endpoint("https://s3.amazonaws.com")
      //         .credentials("YOUR-ACCESSKEY", "YOUR-SECRETACCESSKEY")
      //         .build();

      // Enable object legal hold.
      apiClient.putObjectLegalHold(
          PutObjectLegalHoldArgs.builder()
              .bucket("my-bucketname")
              .object("my-objectname")
              .versionId("object-versionId")
              .build());

      System.out.println("Legal hold enabled on object successfully ");
    } catch (ApiException e) {
      System.out.println("Error occurred: " + e);
    }
  }
}
