package study.withgradle;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicReference;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {

//        pipeOnlyExecuteOnSubscribe();
//
//        fluxWhenError();
//
//        requestOnly2();
//
//        withCustomSubscriberThatCancelIntarnaly();
//
//        fluxGenerated();

//        AtomicReference<Integer> count = new AtomicReference<>(0);
//
//        Flux.just(1, 2, 3, 0, 4 ).map(n -> {
//            return n;
//        }).subscribe(n -> {
//            count.set(count.get() + 1);
//            System.out.println(count);
//
//            System.out.println(n);
//        });
    }


    private static void fluxGenerated() {

        System.out.println("\nfluxGenerated Flux.generate ---------------\n");

        Flux.generate(
            () -> 0,
            (state, sink) -> {
                sink.next("state is: " + state);

                if (state > 9)
                    sink.complete();
                return state + 1;
            }
        ).map(n -> {
            System.out.println(n);
            return n;
        }).subscribe();
    }

    private static void withCustomSubscriberThatCancelIntarnaly() {

        System.out.println("\nwithCustomSubscriber with SubscriberExample ---------------\n");

        SubscriberExample<Integer> ss = new SubscriberExample();
        Flux<Integer> ints = Flux.range(1, 40000000);

        ints.subscribe(ss);
    }

    private static void requestOnly2(){
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"),
                sub -> sub.request(2));
    }

    private static void fluxWhenError() {
        System.out.println("\nfluxWhenError pipe.subscribe when error ---------------\n");

        // put one element as "zero" to see what happen
        Flux.just(1, 2, 3, 0, 4 ).map(n -> {
            //  causes a zero division when n is zero
            Integer divided = n/n;
            return divided;
        }).subscribe(

            n -> System.out.println(n),

            error -> System.out.println("error is: " + error.getMessage()),

            //  Only print "DONE" when no error interrupt the flux
            () ->  System.out.println("DONE")
        );



        System.out.println("\nfluxWhenError Flux stop when one fail ---------------\n");

    }

    private static void pipeOnlyExecuteOnSubscribe() {
        Flux pipe1 = Flux.just(1, 2, 3, 4 );

        Flux pipe2 = pipe1.map(n -> {
            return (Integer)n * (Integer) n;
        }).map(n -> {
            System.out.println(n);
            return n;
        });

        System.out.println(pipe1);


        System.out.println("\npipeOnlyExecuteOnSubscribe pipe1.subscribe() ---------------\n");

        pipe1.subscribe();

        System.out.println("\npipeOnlyExecuteOnSubscribe pipe2.subscribe() ---------------\n");

        pipe2.subscribe();

        System.out.println("\npipeOnlyExecuteOnSubscribe pipe2.subscribe() again ---------------\n");

        pipe2.subscribe();
    }
}
