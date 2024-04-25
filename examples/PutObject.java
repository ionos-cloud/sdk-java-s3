

import com.ionoscloud.s3.ApiClient;
import com.ionoscloud.s3.PutObjectArgs;
import com.ionoscloud.s3.ServerSideEncryption;
import com.ionoscloud.s3.ServerSideEncryptionCustomerKey;
import com.ionoscloud.s3.ServerSideEncryptionKms;
import com.ionoscloud.s3.ServerSideEncryptionS3;
import com.ionoscloud.s3.errors.ApiException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.KeyGenerator;

public class PutObject {
  /** ApiClient.putObject() example. */
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

      // Create some content for the object.
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < 1000; i++) {
        builder.append(
            "Sphinx of black quartz, judge my vow: Used by Adobe InDesign to display font samples. ");
        builder.append("(29 letters)\n");
        builder.append(
            "Jackdaws love my big sphinx of quartz: Similarly, used by Windows XP for some fonts. ");
        builder.append("(31 letters)\n");
        builder.append(
            "Pack my box with five dozen liquor jugs: According to Wikipedia, this one is used on ");
        builder.append("NASAs Space Shuttle. (32 letters)\n");
        builder.append(
            "The quick onyx goblin jumps over the lazy dwarf: Flavor text from an Unhinged Magic Card. ");
        builder.append("(39 letters)\n");
        builder.append(
            "How razorback-jumping frogs can level six piqued gymnasts!: Not going to win any brevity ");
        builder.append("awards at 49 letters long, but old-time Mac users may recognize it.\n");
        builder.append(
            "Cozy lummox gives smart squid who asks for job pen: A 41-letter tester sentence for Mac ");
        builder.append("computers after System 7.\n");
        builder.append(
            "A few others we like: Amazingly few discotheques provide jukeboxes; Now fax quiz Jack! my ");
        builder.append("brave ghost pled; Watch Jeopardy!, Alex Trebeks fun TV quiz game.\n");
        builder.append("---\n");
      }

      {
        // Create a InputStream for object upload.
        ByteArrayInputStream bais = new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));

        // Create object 'my-objectname' in 'my-bucketname' with content from the input stream.
        apiClient.putObject(
            PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
                    bais, bais.available(), -1)
                .build());
        bais.close();
        System.out.println("my-objectname is uploaded successfully");
      }

      {
        // Create a InputStream for object upload.
        ByteArrayInputStream bais = new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));

        // Generate a new 256 bit AES key - This key must be remembered by the client.
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        ServerSideEncryptionCustomerKey ssec =
            new ServerSideEncryptionCustomerKey(keyGen.generateKey());

        // Create encrypted object 'my-objectname' using SSE-C in 'my-bucketname' with content from
        // the input stream.
        apiClient.putObject(
            PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
                    bais, bais.available(), -1)
                .sse(ssec)
                .build());
        bais.close();
        System.out.println("my-objectname is uploaded successfully");
      }

      {
        // Create a InputStream for object upload.
        ByteArrayInputStream bais = new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));

        Map<String, String> myContext = new HashMap<>();
        myContext.put("key1", "value1");
        ServerSideEncryption sseKms = new ServerSideEncryptionKms("Key-Id", myContext);

        // Create encrypted object 'my-objectname' using SSE-KMS in 'my-bucketname' with content
        // from the input stream.
        apiClient.putObject(
            PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
                    bais, bais.available(), -1)
                .sse(sseKms)
                .build());
        bais.close();
        System.out.println("my-objectname is uploaded successfully");
      }

      {
        // Create a InputStream for object upload.
        ByteArrayInputStream bais = new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));

        ServerSideEncryption sseS3 = new ServerSideEncryptionS3();

        // Create encrypted object 'my-objectname' using SSE-S3 in 'my-bucketname' with content
        // from the input stream.
        apiClient.putObject(
            PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
                    bais, bais.available(), -1)
                .sse(sseS3)
                .build());
        bais.close();
        System.out.println("my-objectname is uploaded successfully");
      }

      {
        // Create a InputStream for object upload.
        ByteArrayInputStream bais = new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));

        // Create headers
        Map<String, String> headers = new HashMap<>();
        // Add custom content type
        headers.put("Content-Type", "application/octet-stream");
        // Add storage class
        headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");

        // Add custom/user metadata
        Map<String, String> userMetadata = new HashMap<>();
        userMetadata.put("My-Project", "Project One");

        // Create object 'my-objectname' with user metadata and other properties in 'my-bucketname'
        // with content
        // from the input stream.
        apiClient.putObject(
            PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
                    bais, bais.available(), -1)
                .headers(headers)
                .userMetadata(userMetadata)
                .build());
        bais.close();
        System.out.println("my-objectname is uploaded successfully");
      }

      {
        // Create object name ending with '/' (mostly called folder or directory).
        apiClient.putObject(
            PutObjectArgs.builder().bucket("my-bucketname").object("path/to/").stream(
                    new ByteArrayInputStream(new byte[] {}), 1, -1)
                .build());
        System.out.println("path/to/ is created successfully");
      }
    } catch (ApiException e) {
      System.out.println("Error occurred: " + e);
    }
  }
}
