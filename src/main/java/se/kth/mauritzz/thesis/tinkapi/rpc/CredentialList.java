package se.kth.mauritzz.thesis.tinkapi.rpc;

import se.kth.mauritzz.thesis.annotations.JsonObject;
import se.kth.mauritzz.thesis.tinkapi.rpc.entity.Credential;

import java.util.List;
import java.util.Optional;

@JsonObject
public class CredentialList {

    private List<Credential> credentials;

    public List<Credential> getCredentials() {
        return Optional.ofNullable(credentials).orElse(List.of());
    }

}
