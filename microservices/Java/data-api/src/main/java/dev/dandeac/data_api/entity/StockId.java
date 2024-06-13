package dev.dandeac.data_api.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class StockId implements Serializable {

        @Column(name = "ingredient_id")
        private UUID ingredientId;

        @Column(name = "provider_id")
        private UUID providerId;


        public StockId() {}

        public StockId(UUID ingredientId, UUID providerId) {
            this.ingredientId = ingredientId;
            this.providerId = providerId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StockId stockId)) return false;
            return getIngredientId().equals(stockId.getIngredientId()) && getProviderId().equals(stockId.getProviderId());
        }
}
