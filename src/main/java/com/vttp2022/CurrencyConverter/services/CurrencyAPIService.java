package com.vttp2022.CurrencyConverter.services;

import java.util.Map;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vttp2022.CurrencyConverter.models.Currency;
import com.vttp2022.CurrencyConverter.models.CurrencyCode;
import com.vttp2022.CurrencyConverter.models.Query;

@Service
public class CurrencyAPIService {

    Logger logger = LoggerFactory.getLogger(CurrencyAPIService.class);

    private static String URL = "https://api.apilayer.com/fixer/convert";
    private static String SYMBOLS_URL = "https://api.apilayer.com/fixer/symbols";

    public Optional<Currency> convertExchangeRates(Query q){
        String apikey = System.getenv("FIXER_CURRENCY_API_KEY");
        String currencyUrl = UriComponentsBuilder.fromUriString(URL)
                            .queryParam("to", q.getTo())
                            .queryParam("from", q.getFrom())
                            .queryParam("amount", q.getAmount())
                            .toUriString();
        logger.info("URI > " +currencyUrl);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        
        try{
            logger.info(apikey);
            HttpHeaders headers = new HttpHeaders();
            headers.set("apikey", apikey);
            HttpEntity request = new HttpEntity<>(headers);
            resp = template.exchange(currencyUrl, HttpMethod.GET, request, String.class,1);
            logger.info(resp.getBody());
            Currency c = Currency.createJson(resp.getBody());
            return Optional.of(c);
            
        } catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();

    }

    public Map<String, String> getSymbols(){
        String apikey = System.getenv("FIXER_CURRENCY_API_KEY");

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        try{
            logger.info(apikey);
            HttpHeaders headers = new HttpHeaders();
            headers.set("apikey", apikey);
            HttpEntity request = new HttpEntity<>(headers);
            resp = template.exchange(SYMBOLS_URL, HttpMethod.GET, request, String.class,1);

        } catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        Map<String,String> symbolsList = CurrencyCode.lisOfCountryCode(resp.getBody());
        logger.info("Service get list>>>> " + symbolsList.toString());
        return symbolsList;

        
        
    }
    
}
