package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.time.Duration;
import java.time.Instant;

public class InventoryAggregator {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Instant start = Instant.now();
        CompletableFuture<String> futureLocal = CompletableFuture.supplyAsync(() -> getInventoryLocal());
        CompletableFuture<String> futureGlobal = CompletableFuture.supplyAsync(() -> getInventoryGlobal());

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureLocal, futureGlobal);

        combinedFuture.get(); // Wait for all to complete

        String result1 = futureLocal.get();
        String result2 = futureGlobal.get();

        System.out.println("Result from API 1: " + result1);
        System.out.println("Result from API 2: " + result2);
        Instant end = Instant.now();
        System.out.println("Time taken for Main Thread : " + Duration.between(start, end).toMillis() + " ms");
    }

    private static String getInventoryLocal() {
        Instant start = Instant.now();
        // Simulate API call
        try {
            Thread.sleep(2000); // Simulating delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Instant end = Instant.now();
        System.out.println("Time taken for API 1: " + Duration.between(start, end).toMillis() + " ms");
        return "Response from API 1";
    }

    private static String getInventoryGlobal() {
        Instant start = Instant.now();
        // Simulate API call
        try {
            Thread.sleep(3000); // Simulating delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Instant end = Instant.now();
        System.out.println("Time taken for API 2: " + Duration.between(start, end).toMillis() + " ms");
        return "Response from API 2";
    }
}
