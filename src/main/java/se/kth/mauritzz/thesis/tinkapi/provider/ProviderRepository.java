package se.kth.mauritzz.thesis.tinkapi.provider;

import se.kth.mauritzz.thesis.tinkapi.provider.rpc.ProviderList;
import se.kth.mauritzz.thesis.tinkapi.provider.rpc.entity.Provider;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProviderRepository {

    public static final List<String> autoProviders = List.of(
            "alandsbanken",
            "americanexpress",
            "danskebank",
            "forex",
            "handelsbanken",
            "icabanken-bankid",
            "nordea-password",
            "nordnet",
            "saseurobonusamericanexpress",
            "se-revolut-password",
            "swedbank-token",
            "savingsbank-token",
            "swedbank-payment-revamp-bankid",
            "se-nordea-ob",
            "se-sbab-ob",
            "se-icabanken-ob",
            "se-handelsbanken-ob"
    );

    private final List<Provider> providers;

    public ProviderRepository(ProviderList providerList) {
        if (providerList == null) throw new NoSuchElementException();

        providers = providerList.getProviders();
    }

    public List<Provider> findAll() {
        return providers;
    }

    public Optional<Provider> findByName(String name) {
        return providers
                .stream()
                .filter(provider -> provider.getName().equals(name))
                .findFirst();
    }

}
