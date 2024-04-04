/*
 * MinIO Java SDK for Amazon S3 Compatible Cloud Storage, (C) 2015 MinIO, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.ionoscloud.s3.CloseableIterator;
import com.ionoscloud.s3.ListenBucketNotificationArgs;
import com.ionoscloud.s3.MinioClient;
import com.ionoscloud.s3.Result;
import com.ionoscloud.s3.errors.MinioException;
import com.ionoscloud.s3.messages.Event;
import com.ionoscloud.s3.messages.NotificationRecords;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ListenBucketNotification {
  /** MinioClient.listenBucketNotification() example. */
  public static void main(String[] args)
      throws IOException, NoSuchAlgorithmException, InvalidKeyException {
    try {
      /* play.min.io for test and development. */
      MinioClient minioClient =
          MinioClient.builder()
              .endpoint("https://play.min.io")
              .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
              .build();

      /* Amazon S3: */
      // MinioClient minioClient =
      //     MinioClient.builder()
      //         .endpoint("https://s3.amazonaws.com")
      //         .credentials("YOUR-ACCESSKEY", "YOUR-SECRETACCESSKEY")
      //         .build();

      String[] events = {"s3:ObjectCreated:*", "s3:ObjectAccessed:*"};
      try (CloseableIterator<Result<NotificationRecords>> ci =
          minioClient.listenBucketNotification(
              ListenBucketNotificationArgs.builder()
                  .bucket("bucketName")
                  .prefix("")
                  .suffix("")
                  .events(events)
                  .build())) {
        while (ci.hasNext()) {
          NotificationRecords records = ci.next().get();
          Event event = records.events().get(0);
          System.out.println(event.bucketName() + "/" + event.objectName() + " has been created");
        }
      } catch (IOException e) {
        System.out.println("Error occurred: " + e);
      }
    } catch (MinioException e) {
      System.out.println("Error occurred: " + e);
    }
  }
}
