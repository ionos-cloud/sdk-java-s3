import com.ionoscloud.s3.ApiClient;
import com.ionoscloud.s3.CopyObjectArgs;
import com.ionoscloud.s3.CopySource;
import com.ionoscloud.s3.ServerSideEncryption;
import com.ionoscloud.s3.ServerSideEncryptionCustomerKey;
import com.ionoscloud.s3.ServerSideEncryptionKms;
import com.ionoscloud.s3.ServerSideEncryptionS3;
import com.ionoscloud.s3.errors.ApiException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.KeyGenerator;

public class CopyObject {
  /** ApiClient.copyObject() example. */
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

      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      keyGen.init(256);
      ServerSideEncryptionCustomerKey ssec =
          new ServerSideEncryptionCustomerKey(keyGen.generateKey());

      Map<String, String> myContext = new HashMap<>();
      myContext.put("key1", "value1");
      ServerSideEncryption sseKms = new ServerSideEncryptionKms("Key-Id", myContext);

      ServerSideEncryption sseS3 = new ServerSideEncryptionS3();

      Map<String, String> headers = new HashMap<>();
      headers.put("Content-Type", "application/json");
      headers.put("x-amz-meta-my-project", "Project One");

      String etag = "9855d05ab7a1cfd5ea304f0547c24496";

      {
        // Create object "my-objectname" in bucket "my-bucketname" by copying from object
        // "my-objectname" in bucket "my-source-bucketname".
        apiClient.copyObject(
            CopyObjectArgs.builder()
                .bucket("my-bucketname")
                .object("my-objectname")
                .source(
                    CopySource.builder()
                        .bucket("my-source-bucketname")
                        .object("my-objectname")
                        .build())
                .build());
        System.out.println(
            "my-source-bucketname/my-objectname copied "
                + "to my-bucketname/my-objectname successfully");
      }

      {
        // Create object "my-objectname" in bucket "my-bucketname" by copying from object
        // "my-source-objectname" in bucket "my-source-bucketname".
        apiClient.copyObject(
            CopyObjectArgs.builder()
                .bucket("my-bucketname")
                .object("my-objectname")
                .source(
                    CopySource.builder()
                        .bucket("my-source-bucketname")
                        .object("my-source-objectname")
                        .build())
                .build());
        System.out.println(
            "my-source-bucketname/my-source-objectname copied "
                + "to my-bucketname/my-objectname successfully");
      }

      {
        // Create object "my-objectname" in bucket "my-bucketname" with SSE-KMS server-side
        // encryption by copying from object "my-objectname" in bucket "my-source-bucketname".
        apiClient.copyObject(
            CopyObjectArgs.builder()
                .bucket("my-bucketname")
                .object("my-objectname")
                .source(
                    CopySource.builder()
                        .bucket("my-source-bucketname")
                        .object("my-objectname")
                        .build())
                .sse(sseKms) // Replace with actual key.
                .build());
        System.out.println(
            "my-source-bucketname/my-objectname copied "
                + "to my-bucketname/my-objectname successfully");
      }

      {
        // Create object "my-objectname" in bucket "my-bucketname" with SSE-S3 server-side
        // encryption by copying from object "my-objectname" in bucket "my-source-bucketname".
        apiClient.copyObject(
            CopyObjectArgs.builder()
                .bucket("my-bucketname")
                .object("my-objectname")
                .source(
                    CopySource.builder()
                        .bucket("my-source-bucketname")
                        .object("my-objectname")
                        .build())
                .sse(sseS3) // Replace with actual key.
                .build());
        System.out.println(
            "my-source-bucketname/my-objectname copied "
                + "to my-bucketname/my-objectname successfully");
      }

      {
        // Create object "my-objectname" in bucket "my-bucketname" with SSE-C server-side encryption
        // by copying from object "my-objectname" in bucket "my-source-bucketname".
        apiClient.copyObject(
            CopyObjectArgs.builder()
                .bucket("my-bucketname")
                .object("my-objectname")
                .source(
                    CopySource.builder()
                        .bucket("my-source-bucketname")
                        .object("my-objectname")
                        .build())
                .sse(ssec) // Replace with actual key.
                .build());
        System.out.println(
            "my-source-bucketname/my-objectname copied "
                + "to my-bucketname/my-objectname successfully");
      }

      {
        // Create object "my-objectname" in bucket "my-bucketname" by copying from SSE-C encrypted
        // object "my-source-objectname" in bucket "my-source-bucketname".
        apiClient.copyObject(
            CopyObjectArgs.builder()
                .bucket("my-bucketname")
                .object("my-objectname")
                .source(
                    CopySource.builder()
                        .bucket("my-source-bucketname")
                        .object("my-source-objectname")
                        .ssec(ssec) // Replace with actual key.
                        .build())
                .build());
        System.out.println(
            "my-source-bucketname/my-source-objectname copied "
                + "to my-bucketname/my-objectname successfully");
      }

      {
        // Create object "my-objectname" in bucket "my-bucketname" with custom headers conditionally
        // by copying from object "my-objectname" in bucket "my-source-bucketname".
        apiClient.copyObject(
            CopyObjectArgs.builder()
                .bucket("my-bucketname")
                .object("my-objectname")
                .source(
                    CopySource.builder()
                        .bucket("my-source-bucketname")
                        .object("my-objectname")
                        .matchETag(etag) // Replace with actual etag.
                        .build())
                .headers(headers) // Replace with actual headers.
                .build());
        System.out.println(
            "my-source-bucketname/my-objectname copied "
                + "to my-bucketname/my-objectname successfully");
      }
    } catch (ApiException e) {
      System.out.println("Error occurred: " + e);
    }
  }
}
