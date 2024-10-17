package com.progresssoft.clustereddata.warehouse.entites;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Currency;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity(name = "deals")
public class DealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deal_SEQ")
    @SequenceGenerator(name = "deal_SEQ", sequenceName = "deal_SEQ", allocationSize = 1)
    private Long id;

    private String uuid;

    private Currency toCurrency;

    private Currency fromCurrency;

    private BigDecimal amount;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    private void prePersist() {
        if (Objects.isNull(uuid)) {
            uuid = UUID.randomUUID().toString();
        }
    }

}
