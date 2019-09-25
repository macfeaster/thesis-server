package se.kth.mauritzz.thesis.tinkapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import se.kth.mauritzz.thesis.models.entities.Transaction;
import se.kth.mauritzz.thesis.tinkapi.entity.Credential;
import se.kth.mauritzz.thesis.tinkapi.provider.ProviderRepository;
import se.kth.mauritzz.thesis.tinkapi.provider.rpc.ProviderList;
import se.kth.mauritzz.thesis.tinkapi.entity.TransactionEntity;
import se.kth.mauritzz.thesis.tinkapi.rpc.AddSupplementalRequest;
import se.kth.mauritzz.thesis.tinkapi.rpc.CreateCredentialRequest;
import se.kth.mauritzz.thesis.tinkapi.rpc.CredentialList;
import se.kth.mauritzz.thesis.tinkapi.rpc.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TinkApi extends ApiBinding {

    private static final Logger logger = LoggerFactory.getLogger(TinkApi.class);

    private ProviderRepository providerRepository;

    public TinkApi(String accessToken) {
        super(accessToken);
    }

    public User getUser() {
        return restTemplate.getForObject(API_URL + "/user", User.class);
    }

    public User updateUser(User user) {
        HttpEntity<User> request = new HttpEntity<>(user);
        final var result = restTemplate.exchange(API_URL + "/user", HttpMethod.PUT, request, User.class);
        return result.getBody();
    }

    public ProviderRepository getProviderRepository() {
        if (providerRepository == null) {
            final var providerList = restTemplate.getForObject(API_URL + "providers", ProviderList.class);
            providerRepository = new ProviderRepository(providerList);
        }

        return providerRepository;
    }

    public Credential createCredentials(CreateCredentialRequest request) {
        return restTemplate.postForObject(API_URL + "credentials", request, Credential.class);
    }

    public List<Credential> getCredentials() {
        return Optional.ofNullable(restTemplate.getForObject(API_URL + "credentials/list", CredentialList.class))
                .map(CredentialList::getCredentials)
                .orElse(List.of());
    }

    public Credential getCredential(String id) {
        return restTemplate.getForObject(API_URL + "credentials/" + id, Credential.class);
    }

    public List<Transaction> getTransactions() {
        return Optional.ofNullable(restTemplate.getForObject(API_URL + "transactions?limit=20000&sort=DATE&order=DESC", TransactionEntity[].class))
                .map(Arrays::asList)
                .orElse(List.of())
                .stream()
                .map(TransactionEntity::toPersistence)
                .collect(Collectors.toList());
    }

    public void refreshCredential(String id) {
        restTemplate.postForObject(API_URL + "credentials/" + id + "/refresh", null, String.class);
    }

    public void addSupplemental(String id, AddSupplementalRequest request) {
        restTemplate.postForObject(API_URL + "credentials/" + id + "/supplemental-information", request, String.class);
    }

    public void deleteCredential(String id) {
        restTemplate.delete(API_URL + "credentials/" + id);
    }
}
