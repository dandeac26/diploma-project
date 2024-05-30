package dev.dandeac.data_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "providers_tb")
public class Provider {

    @Id
    @UuidGenerator(style=UuidGenerator.Style.RANDOM)
    @Column(name = "provider_id")
    private UUID providerId;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    public Provider(){}

    public Provider(UUID providerId, String name, String phoneNumber) {
        this.providerId = providerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Provider(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
