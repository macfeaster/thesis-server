package se.kth.mauritzz.thesis.tinkapi.unauthenticated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import se.kth.mauritzz.thesis.Config;
import se.kth.mauritzz.thesis.models.entities.User;
import se.kth.mauritzz.thesis.tinkapi.ApiBinding;
import se.kth.mauritzz.thesis.tinkapi.unauthenticated.rpc.CreateUserRequest;

@Component
public class TinkUnauthenticatedApi extends ApiBinding {

    private static final Logger logger = LoggerFactory.getLogger(TinkUnauthenticatedApi.class);

    private final Config config;
    private TinkUnauthenticatedApiClient apiClient;
    private TokenResponse cachedToken;

    @Autowired
    public TinkUnauthenticatedApi(Config config) {
        super(null);
        this.config = config;
    }

    private TinkUnauthenticatedApiClient getApiClient() {
        if (apiClient == null || cachedToken.isExpired())
            apiClient = new TinkUnauthenticatedApiClient(getUnauthenticatedToken().getAccessToken(), config);

        return apiClient;
    }

    private TokenResponse getUnauthenticatedToken() {
        if (cachedToken == null || cachedToken.isExpired()) {
            var body = new LinkedMultiValueMap<String, String>();
            body.add("client_id", config.getClientId());
            body.add("client_secret", config.getClientSecret());
            body.add("grant_type", "client_credentials");
            body.add("scope", "accounts:read,accounts:write,credentials:read,credentials:refresh,credentials:write,settings:read,statistics:read,transactions:read,user:read,user:write,properties:read,properties:write,providers:read,user:create,user:delete,user:password:reset,authorization:read,authorization:grant,authorization:revoke");

            cachedToken = postForm(API_URL + "oauth/token", body, TokenResponse.class);
            logger.info("Acquired new client_credentials access_token {} expiring {}", cachedToken.getTokenPart(), cachedToken.getExpires());
        }

        return cachedToken;
    }
    public User createUser() {
        var createUserRequest = new CreateUserRequest("en_US", "SE");
        var userId = getApiClient().createUser(createUserRequest).getUserId();

        return new User(userId);
    }

    public TokenResponse authenticateUser(User user) {
        final var userCode = getApiClient().getUserCode(user.getId());
        return getApiClient().authenticateUser(userCode.getCode());
    }

}
