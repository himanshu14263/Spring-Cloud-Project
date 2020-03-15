package com.springboot.cloud.project.currencyconversionservice.Controllers;

import com.springboot.cloud.project.currencyconversionservice.Beans.CurrencyConversionBean;
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
    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable BigDecimal quantity){

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("from",to);

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
}
