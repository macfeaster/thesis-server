package se.kth.mauritzz.thesis.tinkapi.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import se.kth.mauritzz.thesis.annotations.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@JsonObject
@Getter
public class Credential {

    public enum Status {
        CREATED, AUTHENTICATING, AWAITING_MOBILE_BANKID_AUTHENTICATION, AWAITING_SUPPLEMENTAL_INFORMATION,
        UPDATING, UPDATED, AUTHENTICATION_ERROR, TEMPORARY_ERROR, PERMANENT_ERROR,
        AWAITING_THIRD_PARTY_APP_AUTHENTICATION, DELETED, SESSION_EXPIRED
    }

    private Map<String, String> fields;
    private String id;
    private String providerName;
    private long sessionExpiryDate;
    private Status status;
    private String statusPayload;
    private long statusUpdated;
    private String supplementalInformation;
    private String type;
    private long updated;
    private String userId;

    public List<Field> getSupplementalFields() {
        if (supplementalInformation == null || status == Status.AWAITING_MOBILE_BANKID_AUTHENTICATION)
            return List.of();

        try {
            return List.of(new ObjectMapper().readValue(supplementalInformation, Field[].class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
