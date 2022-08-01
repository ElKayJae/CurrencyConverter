package com.vttp2022.CurrencyConverter.models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


public class CurrencyCode {
    static Map<String, String> currencyCodeList = new LinkedHashMap<>();

    public static Map<String, String> getCurrencyCodeLst() {
        return currencyCodeList;
    }

    public static void setCurrencyCodeList(Map<String, String> currencyCodeList) {
        CurrencyCode.currencyCodeList = currencyCodeList;
    }

    public static Map<String, String> lisOfCountryCode(String json){
        InputStream is = new ByteArrayInputStream(json.getBytes());

        try (JsonReader jr = Json.createReader(is)) {
            JsonObject o = jr.readObject();
            JsonObject symbolsList = o.getJsonObject("symbols");
            SortedSet<String> sortedKeys = new TreeSet<>(symbolsList.keySet());
            Iterator<String> key = sortedKeys.iterator();

            while (key.hasNext()){
                String symbol = key.next();
                currencyCodeList.put(symbol, symbolsList.getString(symbol).toString());
            }
        }
        return currencyCodeList;
    }


}
