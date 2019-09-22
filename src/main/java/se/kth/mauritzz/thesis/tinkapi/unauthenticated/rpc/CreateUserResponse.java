package se.kth.mauritzz.thesis.tinkapi.unauthenticated.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;
import se.kth.mauritzz.thesis.annotations.JsonObject;

@JsonObject
@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal", "unused"})
public class CreateUserResponse {

    @JsonProperty("user_id")
    private String userId;

    public String getUserId() {
        return userId;
    }
}
