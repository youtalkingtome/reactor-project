package org.example;

import reactor.core.publisher.Mono;
import java.time.Duration;
import java.time.Instant;

public class StoresAggregator {

    public static void main(String[] args) {

        Instant start = Instant.now();
        Mono<String> indianStores = getStoresIndia();
        Mono<String> singaporeStores = getStoresSingapore();

        // Blocking on the first Mono
        String result1 = indianStores.blockOptional().orElse("Default Value for API 1");
        System.out.println("Result from API 1: " + result1);

        // Blocking on the second Mono
        String result2 = singaporeStores.blockOptional().orElse("Default Value for API 2");
        System.out.println("Result from API 2: " + result2);
        Instant end = Instant.now();
        System.out.println("Time taken for Main Thread: " + Duration.between(start, end).toMillis() + " ms");
    }

    private static Mono<String> getStoresIndia() {
        Instant start = Instant.now();
        Mono<String> result = Mono.just("Response from API 1")
                .delayElement(Duration.ofSeconds(3)); // Simulates a delay
        Instant end = Instant.now();
        System.out.println("Time taken for Indian Stores: " + Duration.between(start, end).toMillis() + " ms");
        return result;
    }

    private static Mono<String> getStoresSingapore() {
        Instant start = Instant.now();
        Mono<String> result= Mono.just("Response from API 2")
                .delayElement(Duration.ofSeconds(4)); // Simulates a delay
        Instant end = Instant.now();
        System.out.println("Time taken for Singapore Stores: " + Duration.between(start, end).toMillis() + " ms");
        return result;
    }
}
