package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class ApiAggregatorParallel {


    public static void main(String[] ar) {


        List<String> taskIds = Arrays.asList(new Task("Admin", RandomGenerator.getDefault().toString()).getId(),
                new Task("Comercial", RandomGenerator.getDefault().toString()).getId());

        List<CompletableFuture<String>> futures = taskIds.parallelStream()
                .map(taskId -> CompletableFuture.supplyAsync(() -> fetchResult(taskId)))
                .collect(Collectors.toList());

        // Convert List<CompletableFuture<String>> to CompletableFuture<List<String>>
        CompletableFuture<List<String>> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        ).thenApply(v ->
                futures.stream() // Use a regular stream here to join futures
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
        // When all the Futures are completed, call `future.join()` to get their results and collect the results in a list
        // Use the combined future
        try {
            List<String> results = allFutures.get(); // Aggregated results
            results.forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }

    // Dummy method to simulate fetching result
    private static String fetchResult(String taskId) {
        // Implement fetching logic
        return "Result for task " + taskId;
    }
}
