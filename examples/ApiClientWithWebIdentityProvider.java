import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.ionoscloud.s3.ApiClient;
import com.ionoscloud.s3.StatObjectArgs;
import com.ionoscloud.s3.StatObjectResponse;
import com.ionoscloud.s3.credentials.Jwt;
import com.ionoscloud.s3.credentials.Provider;
import com.ionoscloud.s3.credentials.WebIdentityProvider;
import java.io.IOException;
import java.security.ProviderException;
import java.util.Objects;
import javax.annotation.Nonnull;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClientWithWebIdentityProvider {
  static Jwt getJwt(
      @Nonnull String clientId,
      @Nonnull String clientSecret,
      @Nonnull String idpClientId,
      @Nonnull String idpEndpoint) {
    Objects.requireNonNull(clientId, "Client id must not be null");
    Objects.requireNonNull(clientSecret, "ClientSecret must not be null");

    RequestBody requestBody =
        new FormBody.Builder()
            .add("username", clientId)
            .add("password", clientSecret)
            .add("grant_type", "password")
            .add("client_id", idpClientId)
            .build();

    Request request = new Request.Builder().url(idpEndpoint).post(requestBody).build();

    OkHttpClient client = new OkHttpClient();
    try (Response response = client.newCall(request).execute()) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      mapper.setVisibility(
          VisibilityChecker.Std.defaultInstance()
              .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
      return mapper.readValue(response.body().charStream(), Jwt.class);
    } catch (IOException e) {
      throw new ProviderException(e);
    }
  }

  public static void main(String[] args) throws Exception {
    // IDP endpoint.
    String idpEndpoint =
        "https://IDP-HOST:IDP-PORT/auth/realms/master/protocol/openid-connect/token";

    // Client-ID to fetch JWT.
    String clientId = "USER-ID";

    // Client secret to fetch JWT.
    String clientSecret = "PASSWORD";

    // Client-ID of IONOS service on IDP.
    String idpClientId = "IONOS-CLIENT-ID";

    // STS endpoint usually point to IONOS server.
    String stsEndpoint = "http://STS-HOST:STS-PORT/";

    // Role ARN if available.
    String roleArn = "ROLE-ARN";

    // Role session name if available.
    String roleSessionName = "ROLE-SESSION-NAME";

    Provider provider =
        new WebIdentityProvider(
            () -> getJwt(clientId, clientSecret, idpClientId, idpEndpoint),
            stsEndpoint,
            null,
            null,
            roleArn,
            roleSessionName,
            null);

    ApiClient apiClient =
        ApiClient.builder()
            .endpoint("https://IONOS-HOST:IONOS-PORT")
            .credentialsProvider(provider)
            .build();

    // Get information of an object.
    StatObjectResponse stat =
        apiClient.statObject(
            StatObjectArgs.builder().bucket("my-bucketname").object("my-objectname").build());
    System.out.println(stat);
  }
}
