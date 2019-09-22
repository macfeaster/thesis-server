package se.kth.mauritzz.thesis.tinkapi.rpc;

import se.kth.mauritzz.thesis.annotations.JsonObject;

import java.util.Map;

@JsonObject
public class CreateCredentialRequest {

    private String providerName;
    private Map<String, String> fields;

    public CreateCredentialRequest(String providerName, Map<String, String> fields) {
        this.providerName = providerName;
        this.fields = fields;
    }
}
