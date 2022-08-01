package com.vttp2022.CurrencyConverter.models;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;

public class Query {
    Logger logger = LoggerFactory.getLogger(Query.class);

    private String from;
    private String to;
    private BigDecimal amount;


    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public static Query createJson(JsonObject o){
        
        Query q = new Query();
        // get the key mapping of "query"
        JsonObject queryObj = o.getJsonObject("query");
        // get the key mapping of "to" inside of "query"
        String toStr = queryObj.getString("to");
        // set "to" attribute of q
        q.to = toStr;
        String fromStr = queryObj.getString("from");
        q.from = fromStr;
        JsonNumber amountNum = queryObj.getJsonNumber("amount");
        q.amount = amountNum.bigDecimalValue();
        return q;
    }
}
