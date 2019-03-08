package example.reactor;


import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

import java.util.List;
import java.util.stream.IntStream;

public class WebClients {

    static String uri = "http://api.github.com/users/luizamboni/repos";

    static Integer volume = 10;

    public static void main(String[] args) {

        syncHttpClient();

        asyncHttpClient();

        while(true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void syncHttpClient(){
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

    public static void asyncHttpClient() {


        Long initial = System.currentTimeMillis();


        Flux<HttpClientResponse> pooling = Flux.range(0, volume)
                .publishOn(Schedulers.single())
//                .log()
                .flatMap(integer -> {
                    System.out.println(Thread.currentThread().getName());

                    Mono<HttpClientResponse> response = HttpClient.create()
                            .get()
                            .uri(uri)
                            .response();

                    return response;

                });




        pooling.doOnComplete(() -> {

            System.out.println(
                "enlapsed time: " + (System.currentTimeMillis() - initial) + " milliseconds"
            );

            System.exit(0);

        }).subscribe(response -> {

            System.out.println(Thread.currentThread().getName());

            System.out.println("http status: " + response.status());
        });
    }
}
