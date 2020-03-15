package com.springboot.cloud.project.currencyconversionservice.Controllers;

import com.springboot.cloud.project.currencyconversionservice.Beans.CurrencyConversionBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {
    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable BigDecimal quantity){

        CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean(1L,from,to,new BigDecimal(65),
                quantity ,new BigDecimal(6500),1010);

        return currencyConversionBean;
    }
}
