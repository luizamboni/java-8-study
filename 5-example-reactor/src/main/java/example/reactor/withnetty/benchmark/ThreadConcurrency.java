package example.reactor.withnetty.benchmark;

import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ThreadConcurrency {

    String uri;
    Integer volume = 1;

    ThreadConcurrency(String uri, Integer volume) {
        this.uri = uri;
        this.volume = volume;
    }


    public void run() {

        Long initial = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        Callable<Boolean> task = () -> {
            HttpClientResponse response = HttpClient.create()
                    .get()
                    .uri(uri)
                    .response()
                    .block();

            System.out.println("http status: " + response.status());
            System.out.println(Thread.currentThread().getName());

            return true;
        };

        Collection<Callable<Boolean>> tasks = new ArrayList<>();

        IntStream.range(0, volume).forEach(i -> {
            tasks.add(task);
        });


        try {
            List<Future<Boolean>> isDone = executor.invokeAll(tasks);


            // if you want get a value from Future

            Integer countDone = 0;

            for(Future<Boolean> donefuture : isDone) {
                try {
                    Boolean done = donefuture.get();

                    if(done)
                        System.out.println(++countDone);

                    System.out.println(done);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            };



        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        executor.shutdown();

        System.out.println(
                "enlapsed time: " + (System.currentTimeMillis() - initial) + " milliseconds"
        );
    }
}
