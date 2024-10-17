package com.progresssoft.clustereddata.warehouse.controllers;

import com.progresssoft.clustereddata.warehouse.controllers.requests.CreateDealRequest;
import com.progresssoft.clustereddata.warehouse.controllers.requests.UpdateDealRequest;
import com.progresssoft.clustereddata.warehouse.mappers.DealMapper;
import com.progresssoft.clustereddata.warehouse.models.Deal;
import com.progresssoft.clustereddata.warehouse.services.DealService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/deals")
public class DealController {

    private final DealService dealService;
    private final DealMapper dealMapper;

    @Autowired
    public DealController(DealService dealService, DealMapper dealMapper) {
        this.dealService = dealService;
        this.dealMapper = dealMapper;
    }

    @PostMapping(path = "/addNewDeal")
    public ResponseEntity<Deal> createNewDeal(@RequestBody @Valid CreateDealRequest request) {
        Deal createdDeal = dealService.createDeal(dealMapper.toDeal(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdDeal);
    }

    @PutMapping(path = "/updateDeal")
    public ResponseEntity<Deal> updateDeal(@RequestBody @Valid UpdateDealRequest request) {
        Deal updatedDeal = dealService.updateDeal(dealMapper.toDeal(request));
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedDeal);
    }

    @DeleteMapping(path = "{uuid}")
    public ResponseEntity<Deal> deleteDeal(@PathVariable("uuid") String uuId) {
        Deal deletedDeal = dealService.deleteDeal(uuId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(deletedDeal);
    }

    @GetMapping(path="/{uuid}")
    public ResponseEntity<Deal> getDealByUuId(@PathVariable String uuid) {
        Deal deal = dealService.getDealByUuid(uuid);
        return ResponseEntity.status(HttpStatus.OK)
                .body(deal);
    }

    @GetMapping(path = "/allDeals")
    public ResponseEntity<List<Deal>> getAllDeals() {
        List<Deal> deals = dealService.getAllDeals();
        return ResponseEntity.status(HttpStatus.OK)
                .body(deals);
    }

}
