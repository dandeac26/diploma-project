package dev.dandeac.data_api.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

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

    public Client(){}

    public Client(UUID clientId, String firmName, String contactPerson, String phoneNumber, String location) {
        this.clientId = clientId;
        this.firmName = firmName;
        this.contactPerson = contactPerson;
        this.phoneNumber = phoneNumber;
        this.location = location;
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
