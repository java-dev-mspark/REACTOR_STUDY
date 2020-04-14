package com.mspark.reactor.part05;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
public class MergeTest {

	@Test
	public void testMergeFlux() {
		Flux<Long> flux1 = Flux.interval(Duration.ofMillis(100)).take(10);
		Flux<Long> flux2 = Flux.just(100L, 102L, 103L);
		
		// flux1에 지연이 있는경우 지연이 없는 flux2가 앞서서 merge 된다.
		flux1.mergeWith(flux2)
		     .doOnNext(System.out::println)
		     .blockLast();
		
		// 순서를 지키고 싶은 경우
		flux1.concatWith(flux2)
			 .doOnNext(System.out::println)
			 .blockLast();
		
		// 여러개의 모노를 순서에 맞게 합쳐서 Flux를 만드는 경우 
		Mono<Long> mono1 = Mono.just(1l);
		Mono<Long> mono2 = Mono.just(2l);
		Mono<Long> mono3 = Mono.just(3l);
		
		Flux.concat(mono1, mono2, mono3)
			.doOnNext(System.out::println)
			.blockLast();
		
		
	}
}
