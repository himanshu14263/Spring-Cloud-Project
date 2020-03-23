package com.springboot.cloud.project.currencyconversionservice.Controllers;

import com.springboot.cloud.project.currencyconversionservice.Beans.CurrencyConversionBean;
import com.springboot.cloud.project.currencyconversionservice.Proxies.CurrencyExchangeServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.URIParameter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable BigDecimal quantity){

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        //calling currency-exchange service
        ResponseEntity<CurrencyConversionBean> responseEntity =  new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",CurrencyConversionBean.class, uriVariables);

        //reading extracting data response Entity
        CurrencyConversionBean response = responseEntity.getBody();

        //reading data which will be used for calculation
        BigDecimal conversionMultiple = response.getConversionMultiple();
        int port = response.getPort();

        CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean(1L,from,to, conversionMultiple,
                quantity ,conversionMultiple.multiply(quantity),port);

        return currencyConversionBean;
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable BigDecimal quantity){

        CurrencyConversionBean response = currencyExchangeServiceProxy.retrieveExchangeValue(from,to);

        //reading data which will be used for calculation
        BigDecimal conversionMultiple = response.getConversionMultiple();
        int port = response.getPort();

        CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean(1L,from,to, conversionMultiple,
                quantity ,conversionMultiple.multiply(quantity),port);

        logger.info("{}",currencyConversionBean);

        return currencyConversionBean;
    }

}
