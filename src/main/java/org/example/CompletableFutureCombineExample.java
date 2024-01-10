package org.example;

import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureCombineExample {

    public static void main(String[] args) {
        // Convert CompletableFuture to Mono
        Mono<String> apiCall1Mono = Mono.fromFuture(apiCall1());
        Mono<String> apiCall2Mono = Mono.fromFuture(apiCall2());

        // Combine both API calls and wait for them to finish
        Mono.zip(apiCall1Mono, apiCall2Mono)
                .subscribe(
                        combinedResult -> {
                            String result1 = combinedResult.getT1();
                            String result2 = combinedResult.getT2();
                            System.out.println("API Call 1 Result: " + result1);
                            System.out.println("API Call 2 Result: " + result2);
                            // Proceed with combined results
                        },
                        error -> System.err.println("An error occurred: " + error)
                );
    }

    private static CompletableFuture<String> apiCall1() {
        // Simulate an API call returning CompletableFuture
        return CompletableFuture.supplyAsync(() -> {
            // Simulate delay
            sleep(1000);
            return "Result from API Call 1";
        });
    }

    private static CompletableFuture<String> apiCall2() {
        // Simulate an API call returning CompletableFuture
        return CompletableFuture.supplyAsync(() -> {
            // Simulate delay
            sleep(500);
            return "Result from API Call 2";
        });
    }

    private static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

