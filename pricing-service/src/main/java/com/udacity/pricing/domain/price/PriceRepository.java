package com.udacity.pricing.domain.price;

import org.springframework.data.repository.CrudRepository;

// Interface that will perform CRUD operations from the Data Rest API specs
public interface PriceRepository extends CrudRepository<Price, Long> {}