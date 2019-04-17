package example.reactor.withnetty.benchmark;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

public class ReactorConcurrency {

    String uri;
    Integer volume = 1;

    ReactorConcurrency(String uri, Integer volume) {
        this.uri = uri;
        this.volume = volume;
    }

     public void run() {

        Long initial = System.currentTimeMillis();

        Scheduler currentScheduler = Schedulers.single();

        Flux<HttpClientResponse> pooling = Flux.range(0, this.volume)
                .publishOn(currentScheduler)
//                .log()
                .flatMap(integer -> {
                    System.out.println(Thread.currentThread().getName());

                    Mono<HttpClientResponse> response = HttpClient.create()
                            .get()
                            .uri(this.uri)
                            .response()
                            .publishOn(currentScheduler);

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
