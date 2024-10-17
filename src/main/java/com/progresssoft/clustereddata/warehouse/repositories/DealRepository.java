package com.progresssoft.clustereddata.warehouse.repositories;

import com.progresssoft.clustereddata.warehouse.entites.DealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@Repository
public interface DealRepository extends JpaRepository<DealEntity, Long> {

    Optional<DealEntity> findByUuid(String uuid);

    boolean existsByFromCurrencyAndToCurrencyAndAmount(Currency fromCurrency, Currency toCurrency, BigDecimal amount);
}
