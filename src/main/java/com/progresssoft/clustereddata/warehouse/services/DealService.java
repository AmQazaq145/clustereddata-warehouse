package com.progresssoft.clustereddata.warehouse.services;

import com.progresssoft.clustereddata.warehouse.entites.DealEntity;
import com.progresssoft.clustereddata.warehouse.exceptions.DealAlreadyExistException;
import com.progresssoft.clustereddata.warehouse.exceptions.DealNotFoundException;
import com.progresssoft.clustereddata.warehouse.mappers.DealMapper;
import com.progresssoft.clustereddata.warehouse.models.Deal;
import com.progresssoft.clustereddata.warehouse.repositories.DealRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DealService {

    public static final String DEAL_NOT_FOUND_MESSAGE = "Deal [%s] not found";
    public static final String DEAL_ALREADY_EXIST_MESSAGE = "Deal with fromCurrency[%s], toCurrency[%s] and amount [%s] already exist]";

    private final DealMapper dealMapper;

    private final DealRepository dealRepository;

    @Autowired
    public DealService(DealMapper dealMapper, DealRepository dealRepository) {
        this.dealMapper = dealMapper;
        this.dealRepository = dealRepository;
    }

    public Deal createDeal(Deal deal) {
        if (dealRepository.existsByFromCurrencyAndToCurrencyAndAmount(deal.getFromCurrency(), deal.getToCurrency(), deal.getAmount())) {
            log.error(" cannot create Deal with fromCurrency[{}], toCurrency[{}] and amount [{}]]", deal.getFromCurrency(), deal.getToCurrency(), deal.getAmount());
            throw new DealAlreadyExistException(String.format(DEAL_ALREADY_EXIST_MESSAGE, deal.getFromCurrency(), deal.getToCurrency(), deal.getAmount()));
        }
        log.info("Creating Deal [{}]", deal);
        DealEntity dealEntity = dealRepository.save(dealMapper.toDealEntity(deal));
        return dealMapper.toDeal(dealEntity);
    }

    public Deal updateDeal(Deal deal) {
        return dealRepository.findByUuid(deal.getUuid())
                .map(originalDealEntity -> {
                    Optional.ofNullable(deal.getToCurrency()).ifPresent(originalDealEntity::setToCurrency);
                    Optional.ofNullable(deal.getFromCurrency()).ifPresent(originalDealEntity::setFromCurrency);
                    Optional.ofNullable(deal.getAmount()).ifPresent(originalDealEntity::setAmount);
                    log.info("Updating Deal [{}]", deal);
                    return dealRepository.save(originalDealEntity);
                }).map(dealMapper::toDeal)
                .orElseThrow(() -> {
                    log.error(String.format(DEAL_NOT_FOUND_MESSAGE, deal.getUuid()));
                    return new DealNotFoundException(String.format(DEAL_NOT_FOUND_MESSAGE, deal.getUuid()));
                });
    }

    @Transactional
    public Deal deleteDeal(String uuid) {
        return dealRepository.findByUuid(uuid)
                .map(entity -> {
                    dealRepository.delete(entity);
                    log.info("Successfully deleted Deal with UUID [{}]", uuid);
                    return dealMapper.toDeal(entity);
                })
                .orElseThrow(() -> {
                    log.error(String.format(DEAL_NOT_FOUND_MESSAGE, uuid));
                    return new DealNotFoundException(String.format(DEAL_NOT_FOUND_MESSAGE, uuid));
                });
    }

    public Deal getDealByUuid(String uuid) {
        return dealRepository.findByUuid(uuid)
                .map(dealMapper::toDeal)
                .orElseThrow(() -> {
                    log.error(String.format(DEAL_NOT_FOUND_MESSAGE, uuid));
                    return new DealNotFoundException(String.format(DEAL_NOT_FOUND_MESSAGE, uuid));
                });
    }

    public List<Deal> getAllDeals() {
        return dealRepository.findAll()
                .stream()
                .map(dealMapper::toDeal)
                .toList();
    }
}
