package example.reactor.withnetty.sequences;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OneAfterOther {

    static String listUri = "https://jsonplaceholder.typicode.com/posts";
    static String itemUri = "https://jsonplaceholder.typicode.com/posts/{id}";

    static JSONParser parser = new JSONParser();


    public static Mono<String> getItem(String id) {

        String uri2 = itemUri.replace("{id}", id);

        return HttpClient.create()
                .get()
                .uri(uri2)
                .responseSingle((httpResponse, byteBufMono) ->
                        byteBufMono.asString()
                );
    }

    public static Mono<JSONArray> getList() {

        return HttpClient.create()
                .get()
                .uri(listUri)
                .responseSingle((httpResponse, byteBufMono) ->
                        byteBufMono.asString()
                ).map(raw -> {
                    System.out.println("receive list");
                    try {
                        return (JSONArray) parser.parse(raw);
                    } catch (ParseException e) {
                        return new JSONArray();
                    }
        });
    }

    public static void main(String[] args) {


       getList()
                .map(jsonArray -> {
                    Stream itemsRequest = jsonArray
                            .stream()
                            .map(item -> (JSONObject) item)
                            .map(item -> {
                                String id = String.valueOf(((JSONObject) item).get("id"));
                                System.out.println(id);
                                return getItem(id);
                            });

                    return Flux.fromStream(itemsRequest).subscribe();

                }).block();

    }

}
