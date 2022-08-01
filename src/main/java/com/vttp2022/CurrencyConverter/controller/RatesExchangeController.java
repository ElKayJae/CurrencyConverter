package com.vttp2022.CurrencyConverter.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vttp2022.CurrencyConverter.models.Currency;
import com.vttp2022.CurrencyConverter.models.Query;
import com.vttp2022.CurrencyConverter.services.CurrencyAPIService;

@Controller
public class RatesExchangeController {
    private static final Logger logger = LoggerFactory.getLogger(RatesExchangeController.class);

    @Autowired
    private CurrencyAPIService currySvc;

    @RequestMapping("/")
    public String showIndexPage(Model model){
        Currency c = new Currency();
        Query q = new Query();
        c.setQuery(q);
        Map<String, String> symbolsList = currySvc.getSymbols();
        List<String> symbols = Arrays.asList(symbolsList.keySet().toArray(new String[0]));
        List<String> countryName = Arrays.asList(symbolsList.values().toArray(new String[0]));
        model.addAttribute("countrySymbols", symbols);
        model.addAttribute("countryName", countryName);
        return "index";
    }

    @GetMapping("/exchange")
    public String exchange(@RequestParam(required=true) String to, 
    @RequestParam(required=true) String from, @RequestParam(required=true) String amount, Model model){
        logger.info("From string>>>>>" +from);
        Query q = new Query();
        q.setTo(to);
        q.setFrom(from);
        q.setAmount(new BigDecimal(amount));
        Optional<Currency> optcurry = currySvc.convertExchangeRates(q);
        if (optcurry.isEmpty()){
            model.addAttribute("currency", new Currency());
            return "exchange";
        }
        logger.info("<<<<<" + q.getFrom() + "****" + q.getTo());
        model.addAttribute("currency", optcurry.get());
        model.addAttribute("fromCurry", q.getFrom());
        model.addAttribute("toCurry", q.getTo());
        return "exchange";
    }
    
}
