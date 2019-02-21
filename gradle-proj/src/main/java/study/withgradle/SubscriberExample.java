package study.withgradle;


import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

public class SubscriberExample<T> extends BaseSubscriber<T> {



    public void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscribed");
        request(1);
    }

    public void hookOnNext(T value) {


        if((Integer) value > 3)
          this.cancel();


        System.out.println(value);
        request(1);
    }
}