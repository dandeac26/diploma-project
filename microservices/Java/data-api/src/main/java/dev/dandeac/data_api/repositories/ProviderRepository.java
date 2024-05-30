package dev.dandeac.data_api.repositories;

import dev.dandeac.data_api.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {
    boolean existsByName(String name);

    Provider findByName(String name);
}
