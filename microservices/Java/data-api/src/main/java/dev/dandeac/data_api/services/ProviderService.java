package dev.dandeac.data_api.services;

import dev.dandeac.data_api.dtos.ProviderDTO;
import dev.dandeac.data_api.dtos.builders.ProviderBuilder;
import dev.dandeac.data_api.entity.Provider;
import dev.dandeac.data_api.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProviderService {
    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository){
        this.providerRepository = providerRepository;
    }

    public List<ProviderDTO> findProviders() {
        List<Provider> providerList = providerRepository.findAll();
        return providerList.stream()
                .map(ProviderBuilder::toProviderDTO)
                .collect(Collectors.toList());
    }

    public ProviderDTO addProvider(ProviderDTO providerDTO) {

        if (providerRepository.existsByName(providerDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provider with name " + providerDTO.getName() + " already exists");
        }
        Provider provider = ProviderBuilder.toProvider(providerDTO);
        Provider savedProvider = providerRepository.save(provider);
        return ProviderBuilder.toProviderDTO(savedProvider);
    }

    public void deleteProvider(String providerId) {
        try{
            if (!providerRepository.existsById(UUID.fromString(providerId))) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider with id " + providerId + " does not exist");
            }
            providerRepository.deleteById(UUID.fromString(providerId));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot delete provider. It is used in a stock."
            );
        }
    }

    public ProviderDTO updateProvider(String providerId, ProviderDTO providerDTO) {
        if (!providerRepository.existsById(UUID.fromString(providerId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider with id " + providerId + " does not exist");
        }

        if (providerRepository.existsByName(providerDTO.getName()) && !providerRepository.findByName(providerDTO.getName()).getProviderId().equals(UUID.fromString(providerId) )){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provider with name " + providerDTO.getName() + " already exists");
        }
        Provider provider = ProviderBuilder.toProvider(providerDTO);
        provider.setProviderId(UUID.fromString(providerId));
        Provider updatedProvider = providerRepository.save(provider);
        return ProviderBuilder.toProviderDTO(updatedProvider);
    }

    public ProviderDTO findProviderById(String providerId) {
        Provider provider = providerRepository.findById(UUID.fromString(providerId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider with id " + providerId + " does not exist"));
        return ProviderBuilder.toProviderDTO(provider);
    }

    public void deleteAllProviders() {
        try{
            providerRepository.deleteAll();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot delete providers. They are used in a stock."
            );
        }
    }

    public boolean existsById(UUID providerId) {
        return providerRepository.existsById(providerId);
    }

    public Provider findById(UUID providerId) {
        return providerRepository.findById(providerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider with id " + providerId + " does not exist"));
    }
}
