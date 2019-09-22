package se.kth.mauritzz.thesis.tinkapi.unauthenticated.rpc;

import lombok.Getter;
import se.kth.mauritzz.thesis.annotations.JsonObject;

@JsonObject
@Getter
public class CreateAuthorizationResponse {

    private String code;

}
