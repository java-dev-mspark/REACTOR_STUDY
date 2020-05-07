package com.mspark.reactor.part07;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
public class ErrorTest {

	@Test
	public void mono_on_error_resume() {
		Mono<Object> mono = Mono.error(new RuntimeException());

		//예외가 발생하면 새로운 Mono를 리턴한다.
		//리턴된 Mono는 시퀀스에 따라 doOnNext 메서드를 탄다.
		mono.log().onErrorResume(e -> Mono.just(2)).doOnNext(System.out::println).subscribe();
		
	}
	
	@Test
	public void flux_on_error_resume() {
		
		Flux<Object> flux = Flux.error(new RuntimeException());
		
		//예외가 발생하면 새로운 Flux를 리턴한다.
		//리턴된 Flux는 시퀀스에 따라 doOnNext 메서드를 탄다.
		flux.log().onErrorResume(e ->Flux.just(2, 3)).doOnNext(System.out::println).subscribe();
	}

	public void propagete_error() {
		
		try {
			
		}
		catch (Exception e) {
			// 특정 CheckedException을 특수한 RuntimeException으로 감싸 리턴하고 Subsciber나 Stepverifier가 구독할때는  자동으로 unwrapping 되어 기존의 exception에 직접 접근할 수 있다.
			throw Exceptions.propagate(e);
		}
		
	}
	
}
