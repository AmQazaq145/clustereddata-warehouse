package com.progresssoft.clustereddata.warehouse.mappers;


import com.progresssoft.clustereddata.warehouse.controllers.requests.CreateDealRequest;
import com.progresssoft.clustereddata.warehouse.controllers.requests.UpdateDealRequest;
import com.progresssoft.clustereddata.warehouse.entites.DealEntity;
import com.progresssoft.clustereddata.warehouse.models.Deal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealMapper {
    Deal toDeal(DealEntity entity);

    Deal toDeal(CreateDealRequest createDealRequest);

    Deal toDeal(UpdateDealRequest updateDealRequest);

    DealEntity toDealEntity(Deal deal);
}
