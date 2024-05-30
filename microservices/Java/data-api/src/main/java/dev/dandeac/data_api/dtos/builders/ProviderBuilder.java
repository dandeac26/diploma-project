package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.ProviderDTO;
import dev.dandeac.data_api.entity.Provider;

public class ProviderBuilder {
    private ProviderBuilder() {
    }

    public static ProviderDTO toProviderDTO(Provider provider) {
        return new ProviderDTO(provider.getProviderId(), provider.getName(), provider.getPhoneNumber());
    }

    public static Provider toProvider(ProviderDTO providerDTO) {
        return new Provider(providerDTO.getName(), providerDTO.getPhoneNumber());
    }
}
