package dev.dandeac.data_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProviderDTO {

    private UUID providerId;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Pattern(regexp="(^$|[0-9]{10})", message="Phone number must be exactly 10 digits")
    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;

    public ProviderDTO() {
    }

    public ProviderDTO(UUID providerId, String name, String phoneNumber) {
        this.providerId = providerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public ProviderDTO(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
