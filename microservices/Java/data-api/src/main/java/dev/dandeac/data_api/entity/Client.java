package dev.dandeac.data_api.entity;


import dev.dandeac.data_api.dtos.ClientDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "client_tb")
public class Client {

    @Id
    @UuidGenerator(style=UuidGenerator.Style.RANDOM)
    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "firm_name")
    private String firmName;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "location")
    private String location;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ClientDTO.ClientType type;

    public Client(){}

    public Client(UUID clientId, String firmName, String contactPerson, String phoneNumber, String location, Double latitude, Double longitude, String address) {
        this.clientId = clientId;
        this.firmName = firmName;
        this.contactPerson = contactPerson;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public Client(String firmName, String contactPerson, String phoneNumber, String location, Double latitude, Double longitude, String address, ClientDTO.ClientType type) {
        this.firmName = firmName;
        this.contactPerson = contactPerson;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(clientId, client.clientId) && Objects.equals(firmName, client.firmName) && Objects.equals(contactPerson, client.contactPerson) && Objects.equals(phoneNumber, client.phoneNumber) && Objects.equals(location, client.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, firmName, contactPerson, phoneNumber, location);
    }
}
