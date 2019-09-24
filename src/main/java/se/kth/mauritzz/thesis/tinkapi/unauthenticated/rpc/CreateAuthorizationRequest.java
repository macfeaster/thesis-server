package se.kth.mauritzz.thesis.tinkapi.unauthenticated.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import se.kth.mauritzz.thesis.annotations.JsonObject;

@JsonObject
@Getter
public class CreateAuthorizationRequest {

    private final String scope = "accounts:read,credentials:read,credentials:refresh,credentials:write,transactions:read,user:read,providers:read";

    @JsonProperty("user_id")
    private String userId;

    public CreateAuthorizationRequest(String userId) {
        this.userId = userId;
    }
}
