package org.example;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ThenApplyExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Simulate a web service call
        long startTime = System.nanoTime();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // Fetch data (simulate with sleep)
            try {
                Thread.sleep(4000); // Simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Web Service Data";
        });

        // Process data asynchronously after fetching
        CompletableFuture<String> processedData = future.thenApply(data -> {
            // Data processing (CPU-intensive task)
            System.out.println("Processing data on thread: " + Thread.currentThread().getName());
            return processData(data);
        });

        // Output the processed data
        System.out.println("Processed Data: " + processedData.get());
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
        System.out.println("Execution Time: " + duration + " ms");
    }

    private static String processData(String data) {
        // Simulate data processing
        return "Processed " + data;

    }
}