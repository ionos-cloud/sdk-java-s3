import com.ionoscloud.s3.ApiClient;
import com.ionoscloud.s3.DeleteObjectTaggingArgs;
import com.ionoscloud.s3.errors.ApiException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DeleteObjectTagging {
  /** ApiClient.deleteObjectTags() example. */
  public static void main(String[] args)
      throws IOException, NoSuchAlgorithmException, InvalidKeyException {
    try {

      ApiClient apiClient =
          ApiClient.builder()
              .endpoint(System.getenv("IONOS_API_URL"))
              .credentials(System.getenv("IONOS_ACCESS_KEY"), System.getenv("IONOS_SECRET_KEY"))
              .build();

      /* Amazon S3: */
      // ApiClient apiClient =
      //     ApiClient.builder()
      //         .endpoint("https://s3.amazonaws.com")
      //         .credentials("YOUR-ACCESSKEY", "YOUR-SECRETACCESSKEY")
      //         .build();

      apiClient.deleteObjectTags(
          DeleteObjectTaggingArgs.builder()
              .bucket("my-bucketname")
              .object("my-objectname")
              .build());
      System.out.println("Object tags deleted successfully");
    } catch (ApiException e) {
      System.out.println("Error occurred: " + e);
    }
  }
}