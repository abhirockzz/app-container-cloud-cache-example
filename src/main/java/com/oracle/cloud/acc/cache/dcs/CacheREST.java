package com.oracle.cloud.acc.cache.dcs;

import com.oracle.cloud.cache.basic.Cache;
import com.oracle.cloud.cache.basic.RemoteSessionProvider;
import com.oracle.cloud.cache.basic.SessionProvider;
import com.oracle.cloud.cache.basic.options.Expiry;
import com.oracle.cloud.cache.basic.options.Transport;
import com.oracle.cloud.cache.metrics.CacheMetrics;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("")
public class CacheREST {

    private static final String CACHE_NAME = "test-cache";

    private static Cache<Ticker> CACHE;

    static {
        //public CacheREST() {
        String protocolName = Optional.ofNullable(System.getenv("CACHING_PROTOCOL")).orElse("REST");
        System.out.println("Protocol - " + protocolName);

        String port = protocolName.equals("REST") ? "8080" : "1444";
        String cacheUrlSuffix = protocolName.equals("REST") ? "ccs" : "";

        Transport transport = protocolName.equals("REST") ? Transport.rest() : Transport.grpc();
        System.out.println("Transport - " + transport.getType().name());

        String cacheHost = System.getenv("CACHING_INTERNAL_CACHE_URL");
        String cacheUrl = "http://" + cacheHost + ":" + port + "/" + cacheUrlSuffix;
        System.out.println("Cache URL - " + cacheUrl);

        String expiry = Optional.ofNullable(System.getenv("EXPIRY")).orElse("5"); //defaults to 5 seconds
        System.out.println("Expiry - " + expiry);

        SessionProvider sessionProvider = new RemoteSessionProvider(cacheUrl);
        CACHE = sessionProvider.createSession(transport)
                .getCache(CACHE_NAME,
                        new PriceLoader(),
                        Expiry.of(Integer.valueOf(expiry), TimeUnit.SECONDS),
                        new TickerSerializer()
                );

    }

    @GET
    @Path("price/{key}")
    public Response get(@PathParam("key") String key) {
        Ticker info = null;
        Response resp = null;
        try {
            System.out.println("Searching cache for stock " + key);
            info = CACHE.get(key);
            System.out.println("Value for key " + info.getPrice());

            resp = Response.ok(info.toString()).build();
        } catch (Throwable e) {
            resp = Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        return resp;

    }

    @GET
    @Path("metrics")
    public String metrics() {
        String metrics_ = null;
        try {
            CacheMetrics metrics = CACHE.getMetrics();
            metrics_ = metrics.toString();
            System.out.println(metrics_);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return metrics_;
    }

}
