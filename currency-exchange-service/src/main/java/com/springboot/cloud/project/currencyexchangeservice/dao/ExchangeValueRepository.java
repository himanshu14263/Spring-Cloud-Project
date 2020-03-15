package com.springboot.cloud.project.currencyexchangeservice.dao;

import com.springboot.cloud.project.currencyexchangeservice.Beans.ExchangeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue,Long> {

    //writing our own query method
    ExchangeValue findByFromAndTo(String from, String to);
}
