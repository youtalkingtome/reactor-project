package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SendEmailToSubscriber {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> finalFuture = getSubscribersEmail("vikas taank")
                .thenCompose(email -> sendNewsLetter(email));

        // Wait for the final future to complete
        System.out.println(finalFuture.get() + " to user123's email");
    }

    // Simulates fetching user email asynchronously
    private static CompletableFuture<String> getSubscribersEmail(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            // Assume this is some IO operation to get user email
            System.out.println("Fetching email for user " + userId);
            return "vikas.taank@retor.com";
        });
    }

    // Simulates sending an email asynchronously
    private static CompletableFuture<String> sendNewsLetter(String email) {
        return CompletableFuture.supplyAsync(() -> {
            // Assume this is some IO operation to send email
            System.out.println("Sending News Letter to " + email);
            return "News Letter sent";
        });
    }
}

