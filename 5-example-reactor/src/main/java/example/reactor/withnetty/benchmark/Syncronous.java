package example.reactor.withnetty.benchmark;

import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Syncronous {

    String uri;
    Integer volume = 1;

    Syncronous(String uri, Integer volume) {
        this.uri = uri;
        this.volume = volume;
    }


    public void run() {

        Long initial = System.currentTimeMillis();

        IntStream range = IntStream.range(0, volume);

        range.forEach(n -> {
            HttpClientResponse response = HttpClient.create()
                    .get()
                    .uri(uri)
                    .response()
                    .block();

            System.out.println("http status: " + response.status());
        });

        System.out.println(
                "enlapsed time: " + (System.currentTimeMillis() - initial) + " milliseconds"
        );

    }
}
