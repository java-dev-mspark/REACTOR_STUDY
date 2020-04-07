package com.mspark.reactor.part04;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@SpringBootTest
public class FlatMapTest {

	@Test
	public void flatMapTest() {
		Flux.just("a","b","c","d","e","f","g","h") // Flux<String>
		    .window(3)// Flux<Flux<String>>
		    .flatMapSequential(l -> l.map(this::toUpperCase).subscribeOn(Schedulers.parallel())) // Flux<List<String>>
		    .doOnNext(System.out::println)
		    .blockLast();
	}
	
	private List<String> toUpperCase(String s){
		try {
			Thread.sleep(1000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return Arrays.asList(s.toUpperCase(), Thread.currentThread().getName());
	}
}
