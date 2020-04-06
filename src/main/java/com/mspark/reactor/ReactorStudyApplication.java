package com.mspark.reactor;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactorStudyApplication {

	public static void main(String[] args) throws InterruptedException {
		
		SpringApplication.run(ReactorStudyApplication.class, args);
		
		Flux.fromIterable(Arrays.asList("Foo", "Bar"))
			.doOnNext(System.out::println)
			.map(String::toUpperCase)
			.subscribe(System.out::println);
		
		
		Flux.error(new IllegalStateException())
		    .doOnError(System.out::println)
		    .subscribe();
		
		Flux.interval(Duration.ofMillis(100)).take(10)
			.subscribe(System.out::println);

		System.out.println("is It First?");
		
		Thread.sleep(3000);
		
		Mono.just(1L)
	    	.map(integer ->  integer*2)
	    	.or(Mono.just(3l))
	    	.subscribe(System.out::println);
	}
}
