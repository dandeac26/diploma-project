package dev.dandeac.data_api.repositories;

import dev.dandeac.data_api.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    boolean existsByFirmName(String firmName);

    Client findByFirmName(String firmName);
}
