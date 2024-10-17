package com.progresssoft.clustereddata.warehouse;

import com.progresssoft.clustereddata.warehouse.entites.DealEntity;
import com.progresssoft.clustereddata.warehouse.exceptions.DealAlreadyExistException;
import com.progresssoft.clustereddata.warehouse.exceptions.DealNotFoundException;
import com.progresssoft.clustereddata.warehouse.mappers.DealMapper;
import com.progresssoft.clustereddata.warehouse.models.Deal;
import com.progresssoft.clustereddata.warehouse.repositories.DealRepository;
import com.progresssoft.clustereddata.warehouse.services.DealService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static com.progresssoft.clustereddata.warehouse.services.DealService.DEAL_ALREADY_EXIST_MESSAGE;
import static com.progresssoft.clustereddata.warehouse.services.DealService.DEAL_NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DealServiceTests {

    private DealRepository dealRepository;
    private DealMapper dealMapper;
    private DealService dealService;


    @Before
    public void setUp() {
        dealRepository = mock(DealRepository.class);
        dealMapper = mock(DealMapper.class);
        dealService = new DealService(dealMapper, dealRepository);
    }

    @Test
    public void testCreateDeal() {
        Deal deal = new Deal();
        deal.setToCurrency(Currency.getInstance("JOD"));
        deal.setToCurrency(Currency.getInstance("USD"));
        deal.setAmount(BigDecimal.valueOf(200.0));

        DealEntity entity = new DealEntity();

        when(dealMapper.toDealEntity(deal)).thenReturn(entity);
        when(dealMapper.toDeal(entity)).thenReturn(deal);
        when(dealRepository.existsByFromCurrencyAndToCurrencyAndAmount(deal.getFromCurrency(), deal.getToCurrency(), deal.getAmount())).thenReturn(false);
        when(dealRepository.save(entity)).thenReturn(entity);

        Deal createdDeal = dealService.createDeal(deal);
        assertNotNull(createdDeal);
        assertEquals(deal, createdDeal);

        verify(dealMapper).toDealEntity(deal);
        verify(dealMapper).toDeal(entity);
        verify(dealRepository).existsByFromCurrencyAndToCurrencyAndAmount(deal.getFromCurrency(), deal.getToCurrency(), deal.getAmount());
        verify(dealRepository).save(entity);
    }

    @Test
    public void testCreateDealFailed() {
        Deal deal = new Deal();
        deal.setToCurrency(Currency.getInstance("JOD"));
        deal.setToCurrency(Currency.getInstance("USD"));
        deal.setAmount(BigDecimal.valueOf(200.0));

        when(dealRepository.existsByFromCurrencyAndToCurrencyAndAmount(deal.getFromCurrency(), deal.getToCurrency(), deal.getAmount()))
                .thenReturn(true);

        DealAlreadyExistException exception = assertThrows(DealAlreadyExistException.class, () -> dealService.createDeal(deal));
        assertEquals(String.format(DEAL_ALREADY_EXIST_MESSAGE, deal.getFromCurrency(), deal.getToCurrency(), deal.getAmount()), exception.getMessage());

        verify(dealRepository).existsByFromCurrencyAndToCurrencyAndAmount(deal.getFromCurrency(), deal.getToCurrency(), deal.getAmount());
    }

    @Test
    public void testUpdateDeal() {
        Deal updatedDeal = new Deal();
        updatedDeal.setUuid("uuid");
        updatedDeal.setToCurrency(Currency.getInstance("JOD"));
        updatedDeal.setToCurrency(Currency.getInstance("USD"));
        updatedDeal.setAmount(BigDecimal.valueOf(500.9));

        DealEntity originalEntity = new DealEntity();
        DealEntity updatedEntity = new DealEntity();

        when(dealMapper.toDeal(updatedEntity)).thenReturn(updatedDeal);
        when(dealRepository.findByUuid(updatedDeal.getUuid())).thenReturn(Optional.of(originalEntity));
        when(dealRepository.save(originalEntity)).thenReturn(updatedEntity);

        Deal deal = dealService.updateDeal(updatedDeal);
        assertNotNull(deal);
        assertEquals(updatedDeal, deal);

        verify(dealMapper).toDeal(updatedEntity);
        verify(dealRepository).findByUuid(updatedDeal.getUuid());
        verify(dealRepository).save(originalEntity);
    }

    @Test
    public void testUpdateDealFailed() {
        Deal updatedDeal = new Deal();
        updatedDeal.setUuid("uuid");
        updatedDeal.setToCurrency(Currency.getInstance("JOD"));
        updatedDeal.setToCurrency(Currency.getInstance("USD"));
        updatedDeal.setAmount(BigDecimal.valueOf(500.9));

        when(dealRepository.findByUuid(updatedDeal.getUuid())).thenReturn(Optional.empty());

        DealNotFoundException exception = assertThrows(DealNotFoundException.class, () -> dealService.updateDeal(updatedDeal));
        assertNotNull(exception);
        assertEquals(String.format(DEAL_NOT_FOUND_MESSAGE, updatedDeal.getUuid()), exception.getMessage());

        verify(dealRepository).findByUuid(updatedDeal.getUuid());
    }

    @Test
    public void testDeleteDeal() {
        Deal originalDeal = new Deal();
        originalDeal.setUuid("uuid");
        originalDeal.setToCurrency(Currency.getInstance("JOD"));
        originalDeal.setToCurrency(Currency.getInstance("USD"));
        originalDeal.setAmount(BigDecimal.valueOf(500.9));

        DealEntity originalEntity = new DealEntity();

        when(dealMapper.toDeal(originalEntity)).thenReturn(originalDeal);
        when(dealRepository.findByUuid(originalDeal.getUuid())).thenReturn(Optional.of(originalEntity));
        doNothing().when(dealRepository).delete(originalEntity);

        Deal deletedDeal = dealService.deleteDeal(originalDeal.getUuid());
        assertNotNull(deletedDeal);
        assertEquals(originalDeal, deletedDeal);

        verify(dealMapper).toDeal(originalEntity);
        verify(dealRepository).findByUuid(originalDeal.getUuid());
        verify(dealRepository).delete(originalEntity);
    }

    @Test
    public void testDeleteDealFailed() {
        Deal originalDeal = new Deal();
        originalDeal.setUuid("uuid");
        originalDeal.setToCurrency(Currency.getInstance("JOD"));
        originalDeal.setToCurrency(Currency.getInstance("USD"));
        originalDeal.setAmount(BigDecimal.valueOf(500.9));


        when(dealRepository.findByUuid(originalDeal.getUuid())).thenReturn(Optional.empty());

        DealNotFoundException exception = assertThrows(DealNotFoundException.class, () -> dealService.deleteDeal(originalDeal.getUuid()));
        assertNotNull(exception);
        assertEquals(String.format(DEAL_NOT_FOUND_MESSAGE, originalDeal.getUuid()), exception.getMessage());

        verify(dealRepository).findByUuid(originalDeal.getUuid());
    }

    @Test
    public void testGetDealByUuid() {
        Deal originalDeal = new Deal();
        originalDeal.setUuid("uuid");
        originalDeal.setToCurrency(Currency.getInstance("JOD"));
        originalDeal.setToCurrency(Currency.getInstance("USD"));
        originalDeal.setAmount(BigDecimal.valueOf(500.9));

        DealEntity originalEntity = new DealEntity();

        when(dealMapper.toDeal(originalEntity)).thenReturn(originalDeal);
        when(dealRepository.findByUuid(originalDeal.getUuid())).thenReturn(Optional.of(originalEntity));

        Deal deal = dealService.getDealByUuid(originalDeal.getUuid());
        assertNotNull(deal);
        assertEquals(originalDeal, deal);

        verify(dealMapper).toDeal(originalEntity);
        verify(dealRepository).findByUuid(originalDeal.getUuid());
    }

    @Test
    public void testGetDealByUuidFailed() {
        Deal originalDeal = new Deal();
        originalDeal.setUuid("uuid");
        originalDeal.setToCurrency(Currency.getInstance("JOD"));
        originalDeal.setToCurrency(Currency.getInstance("USD"));
        originalDeal.setAmount(BigDecimal.valueOf(500.9));

        when(dealRepository.findByUuid(originalDeal.getUuid())).thenReturn(Optional.empty());

        DealNotFoundException exception = assertThrows(DealNotFoundException.class, () -> dealService.getDealByUuid(originalDeal.getUuid()));
        assertNotNull(exception);
        assertEquals(String.format(DEAL_NOT_FOUND_MESSAGE, originalDeal.getUuid()), exception.getMessage());

        verify(dealRepository).findByUuid(originalDeal.getUuid());
    }


}
