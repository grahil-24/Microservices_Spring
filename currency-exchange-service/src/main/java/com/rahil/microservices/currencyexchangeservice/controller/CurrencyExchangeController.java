package com.rahil.microservices.currencyexchangeservice.controller;

import com.rahil.microservices.currencyexchangeservice.model.CurrencyExchange;
import com.rahil.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    private Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to){
//        CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));

//        2023-12-28T23:52:49.501+05:30  INFO [currency-exchange,759e018f168add22965f00d39b5c0407,e603bb7fd0404e4a] 20366 --- [currency-exchange] [nio-8000-exec-1] [759e018f168add22965f00d39b5c0407-e603bb7fd0404e4a] c.r.m.c.c.CurrencyExchangeController     : retrieveExchangeValue called from USD to INR
//        759e018f168add22965f00d39b5c0407 is an id associated with a request. This id is provided by the micrometer
        logger.info("retrieveExchangeValue called from {} to {}", from, to);

        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
        if(currencyExchange == null){
            throw new RuntimeException("Unable to find data for "+from + " to "+ to);
        }
        String port= environment.getProperty("local.server.port");

        //CHANGE-KUBERNETES
        //this gives the info about which pod is being used
        String host = environment.getProperty("HOSTNAME");
        String version = "v12";

        currencyExchange.setEnvironment(port + " " + version + " " + host);
        return currencyExchange;
    }
}
