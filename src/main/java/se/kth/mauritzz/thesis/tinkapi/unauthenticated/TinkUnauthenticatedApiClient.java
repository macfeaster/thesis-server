package se.kth.mauritzz.thesis.tinkapi.unauthenticated;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import se.kth.mauritzz.thesis.Config;
import se.kth.mauritzz.thesis.tinkapi.ApiBinding;
import se.kth.mauritzz.thesis.tinkapi.unauthenticated.rpc.CreateAuthorizationRequest;
import se.kth.mauritzz.thesis.tinkapi.unauthenticated.rpc.CreateAuthorizationResponse;
import se.kth.mauritzz.thesis.tinkapi.unauthenticated.rpc.CreateUserRequest;
import se.kth.mauritzz.thesis.tinkapi.unauthenticated.rpc.CreateUserResponse;

public class TinkUnauthenticatedApiClient extends ApiBinding {

    private final Config config;

    public TinkUnauthenticatedApiClient(String accessToken, Config config) {
        super(accessToken);
        this.config = config;
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        return restTemplate.postForObject(API_URL + "/user/create", request, CreateUserResponse.class);
    }

    public CreateAuthorizationResponse getUserCode(String userId) {
        var authRequest = new CreateAuthorizationRequest(userId);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("scope", authRequest.getScope());
        map.add("user_id", authRequest.getUserId());
        return postForm(API_URL + "/oauth/authorization-grant", map, CreateAuthorizationResponse.class);
    }

    public TokenResponse authenticateUser(String userCode) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("client_id", config.getClientId());
        body.add("client_secret", config.getClientSecret());
        body.add("code", userCode);
        body.add("grant_type", "authorization_code");
        body.add("scope", "accounts:read,accounts:write,credentials:read,credentials:refresh,credentials:write,settings:read,statistics:read,transactions:read,user:read,user:write,properties:read,properties:write,providers:read");

        return postForm(API_URL + "/oauth/token", body, TokenResponse.class);
    }

}
