package se.kth.mauritzz.thesis.tinkapi.unauthenticated.rpc;

import se.kth.mauritzz.thesis.annotations.JsonObject;

@JsonObject
@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal", "unused"})
public class CreateUserRequest {

    private final String locale;
    private final String market;

    public CreateUserRequest(String locale, String market) {
        this.locale = locale;
        this.market = market;
    }
}
