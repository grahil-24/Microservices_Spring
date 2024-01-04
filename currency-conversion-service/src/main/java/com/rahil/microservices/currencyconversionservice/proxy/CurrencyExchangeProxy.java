package com.rahil.microservices.currencyconversionservice.proxy;
import com.rahil.microservices.currencyconversionservice.model.CurrencyConversion;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange", url = "localhost:8000")
//@FeignClient(name = "currency-exchange")

//CHANGE-KUBERNETES
//if currency-exchange was down, the CURRENCY_EXCHANGE_SERVICE_HOST env variable wont be
//availiable to us. So we need to use a custom one
//@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000")

@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_URI:http://localhost}:8000")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
