package dev.dandeac.data_api.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class ClientDTO {

    private UUID clientId;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String firmName;

    private String contactPerson;

    @Pattern(regexp="(^$|[0-9]{10})", message="Phone number must be exactly 10 digits")
    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;

    private String location;

    @DecimalMin(value = "-90.0", inclusive = false, message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", inclusive = false, message = "Latitude must be between -90 and 90")
    private Double latitude;

    @DecimalMin(value = "-180.0", inclusive = false, message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", inclusive = false, message = "Longitude must be between -180 and 180")
    private Double longitude;

    private String address;

    // In Client.java
    @NotNull(message = "Type cannot be null")
    private ClientType type;


    public ClientDTO(){}

    public ClientDTO(UUID clientId, String firmName, String contactPerson, String phoneNumber, String location, Double latitude, Double longitude, String address, ClientType type) {
        this.clientId = clientId;
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
        ClientDTO client = (ClientDTO) o;
        return firmName.equals(client.firmName) && phoneNumber.equals(client.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmName, phoneNumber);
    }

    public enum ClientType {
        SPECIAL,
        REGULAR,
        KINDERGARTEN
    }
}
