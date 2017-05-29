package com.oracle.cloud.acc.cache.dcs;

import com.oracle.cloud.cache.basic.CacheLoader;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class PriceLoader implements CacheLoader<Ticker> {

    @Override
    public Ticker load(String ticker) {
        System.out.println("Cache loader triggered for key " + ticker);

        Ticker ticker_ = null;
        String price = null;
        String time = null;
        try {
            Response response = ClientBuilder.newClient().
                    target("https://www.google.com/finance/info?q=NASDAQ:" + ticker).
                    request().get();

            if (response.getStatus() != 200) {
                throw new RuntimeException(String.format("Could not find price for ticker %s", ticker));
            }
            String tick = response.readEntity(String.class);
            tick = tick.replace("// [", "");
            tick = tick.replace("]", "");

            JsonReader reader = Json.createReader(new StringReader(tick));
            JsonObject priceJsonObj = reader.readObject();
            price = priceJsonObj.getJsonString("l_cur").getString();
            time = priceJsonObj.getJsonString("lt_dts").getString();
            
            ticker_ = new Ticker(ticker, price, time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ticker_;
    }

}
