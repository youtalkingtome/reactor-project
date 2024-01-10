package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class ApiAggregatorCustomerThreadPool {


    public static void main(String[] ar) {

        ExecutorService customThreadPool = Executors.newFixedThreadPool(10);

        List<String> taskIds = Arrays.asList(new Task("Admin", RandomGenerator.getDefault().toString()).getId(),
                new Task("Comercial", RandomGenerator.getDefault().toString()).getId());

        List<CompletableFuture<String>> futureList = taskIds.stream()
                .map(taskId -> CompletableFuture.supplyAsync(() -> fetchResult(taskId),customThreadPool))
                .collect(Collectors.toList());

        // Create a combined Future using allOf()
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futureList.toArray(new CompletableFuture[0])
        );

        // When all the Futures are completed, call `future.join()` to get their results and collect the results in a list
        CompletableFuture<List<String>> allPageContentsFuture = allFutures.thenApply(v ->
                futureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );


    // Now you can use `allPageContentsFuture` to get the combined results
        try {
            List<String> results = allPageContentsFuture.get(); // This gets the aggregated results
            // Process the combined results
            results.forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        customThreadPool.shutdown();

    }

    // Dummy method to simulate fetching result
    private static String fetchResult(String taskId) {
        // Implement fetching logic
        return "Result for task " + taskId;
    }
}
