package org.example;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class FluxDemo {

    public static void main(String[] args) {

        // Create a Flux from a list of items
        Flux<String> fluxFromItems = Flux.just("Apple", "Banana", "Cherry");
        // Create a Flux from an Iterable (e.g., a List)
        List<String> fruitList = Arrays.asList("Apple", "Banana", "Cherry");

        // Transform the items (e.g., uppercase the strings)
        Flux<String> transformedFlux = fluxFromItems.map(String::toUpperCase);

        // Transform and flatten (e.g., splitting each string)
        Flux<String> flatMappedFlux = fluxFromItems.flatMap(s -> Flux.fromArray(s.split("")));

        // Filter items (e.g., only fruits with length > 5)
        Flux<String> filteredFlux = fluxFromItems.filter(s -> s.length() > 5);

        // Take the first 2 fruits
        Flux<String> firstTwoFlux = fluxFromItems.take(2);

        // Zip two Flux sequences
        Flux<String> flux1 = Flux.just("Hello");
        Flux<String> flux2 = Flux.just("World");
        Flux<String> zippedFlux = Flux.zip(flux1, flux2, (s1, s2) -> s1 + " " + s2);
        //Merge two Flux sequences
        Flux<String> mergedFlux = flux1.mergeWith(flux2);

        // Recover from an error with another Flux
        Flux<String> errorHandlingFlux = fluxFromItems.concatWith(Flux.error(new RuntimeException("Error")))
                .onErrorResume(e -> Flux.just("Recovery item"));

        // Retry on error
        Flux<String> retryFlux = fluxFromItems.concatWith(Flux.error(new RuntimeException("Error")))
                .retry(1);
        // Side-effect operations
        fluxFromItems.doOnNext(System.out::println)
                .doOnComplete(() -> System.out.println("Completed"))
                .doOnError(e -> System.err.println("Error: " + e))
                .subscribe();





        Flux<String> fluxFromIterable = Flux.fromIterable(fruitList);
        // Create a list of integers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // Create a Flux from the list
        Flux<Integer> numberFlux = Flux.fromIterable(numbers)
                .map(number -> number * number)  // find the square of each number
                .filter(number -> number > 5); // Filter: Only allow numbers greater than 5

        // Subscribe to the Flux and print each element
        numberFlux.subscribe(System.out::println);

        // Example of Flux generating values every second
        Flux.interval(Duration.ofSeconds(1))
                .take(5) // Limit to the first 5 elements
                .subscribe(
                        item -> System.out.println("Interval item: " + item),
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Interval Flux Completed")
                );

        // Sleep to keep the application running to observe the interval Flux
        try {
            Thread.sleep(4000); // Sleep for 6 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
