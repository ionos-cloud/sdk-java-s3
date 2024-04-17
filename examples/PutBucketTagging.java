

import com.ionoscloud.s3.ApiClient;
import com.ionoscloud.s3.PutBucketTaggingArgs;
import com.ionoscloud.s3.errors.ApiException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PutBucketTagging {
  /** ApiClient.putBucketTagging() example. */
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

      Map<String, String> map = new HashMap<>();
      map.put("Project", "Project One");
      map.put("User", "jsmith");
      apiClient.putBucketTagging(
          PutBucketTaggingArgs.builder().bucket("my-bucketname").tags(map).build());
    } catch (ApiException e) {
      System.out.println("Error occurred: " + e);
    }
  }
}