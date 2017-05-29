package com.oracle.cloud.acc.cache.dcs;

import java.io.IOException;

import com.oracle.cloud.cache.basic.io.Serializer;

public class TickerSerializer implements Serializer {

    public TickerSerializer() {
    }

    @Override
    public byte[] serialize(Object o) throws IOException {
        byte[] serializedForm = null;
        try {
            System.out.println("Serializing " + o.toString() + " to Ticker");
            Ticker info = (Ticker) o;
            serializedForm = (info.getName() + "," + info.getPrice() + "," + info.getTime()).getBytes();
            System.out.println("Serialized form " + new String(serializedForm));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return serializedForm;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clzType) throws IOException {
        Ticker info = null;
        if (data == null || data.length == 0) {
            System.out.println("data null/empty");
            return null;
        }
        try {
            String serializedData = new String(data);
            System.out.println("de-serializing data " + serializedData + " to info");
            info = new Ticker(serializedData.split(",")[0], serializedData.split(",")[1], serializedData.split(",")[2]);
            System.out.println("deserialized form " + info);
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

        return (T) info;
    }
}
