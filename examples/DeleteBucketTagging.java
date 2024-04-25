import com.ionoscloud.s3.ApiClient;
import com.ionoscloud.s3.DeleteBucketTaggingArgs;
import com.ionoscloud.s3.errors.ApiException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DeleteBucketTagging {
  /** ApiClient.deleteBucketTagging() example. */
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

      apiClient.deleteBucketTagging(
          DeleteBucketTaggingArgs.builder().bucket("my-bucketname").build());
      System.out.println("Bucket tags deleted successfully");
    } catch (ApiException e) {
      System.out.println("Error occurred: " + e);
    }
  }
}