package se.kth.mauritzz.thesis.tinkapi.provider.rpc;

import lombok.Getter;
import se.kth.mauritzz.thesis.annotations.JsonObject;
import se.kth.mauritzz.thesis.tinkapi.provider.rpc.entity.Provider;

import java.util.List;

@JsonObject
public class ProviderList {

    @Getter
    private List<Provider> providers;

}
