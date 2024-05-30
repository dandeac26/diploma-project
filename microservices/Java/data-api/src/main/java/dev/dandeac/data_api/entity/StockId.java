package dev.dandeac.data_api.entity;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class StockId implements Serializable {

        private UUID ingredientId;
        private UUID providerId;

        public StockId() {}

        public StockId(UUID ingredientId, UUID providerId) {
            this.ingredientId = ingredientId;
            this.providerId = providerId;
        }
}
