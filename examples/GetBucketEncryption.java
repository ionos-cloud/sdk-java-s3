/*
 * MinIO Java SDK for Amazon S3 Compatible Cloud Storage, (C) 2020 MinIO, Inc.
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

import com.ionoscloud.s3.GetBucketEncryptionArgs;
import com.ionoscloud.s3.MinioClient;
import com.ionoscloud.s3.errors.MinioException;
import com.ionoscloud.s3.messages.SseAlgorithm;
import com.ionoscloud.s3.messages.SseConfiguration;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class GetBucketEncryption {
  /** MinioClient.getBucketEncryption() example. */
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

      SseConfiguration config =
          minioClient.getBucketEncryption(
              GetBucketEncryptionArgs.builder().bucket("my-bucketname").build());
      if (config.rule() != null) {
        System.out.println("Rule SSE algorithm: " + config.rule().sseAlgorithm());
        if (config.rule().sseAlgorithm() == SseAlgorithm.AWS_KMS) {
          System.out.println("Rule KMS master key ID: " + config.rule().kmsMasterKeyId());
        }
      } else {
        System.out.println("No rule is set in SSE configuration.");
      }
    } catch (MinioException e) {
      System.out.println("Error occurred: " + e);
    }
  }
}
