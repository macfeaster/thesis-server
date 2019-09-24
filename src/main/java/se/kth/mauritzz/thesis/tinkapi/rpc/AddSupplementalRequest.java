package se.kth.mauritzz.thesis.tinkapi.rpc;

import se.kth.mauritzz.thesis.annotations.JsonObject;

import java.util.Map;

@JsonObject
public class AddSupplementalRequest {

    private Map<String, String> information;

    public AddSupplementalRequest(Map<String, String> information) {
        this.information = information;
    }
}
