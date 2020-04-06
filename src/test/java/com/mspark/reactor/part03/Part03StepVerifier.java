package com.mspark.reactor.part03;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.function.Supplier;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class Part03StepVerifier {

	@Test
	void expectFooBarComplete() {
		Flux<String> flux = Flux.just("foo", "bar");
		
		StepVerifier.create(flux).expectNext("foo", "bar").verifyComplete();
	}
	
	@Test
	@Disabled
	void excpectFooBarError() {
		Flux<String> flux = Flux.just("foo", "bar");
		
		StepVerifier.create(flux).expectNext("foo","bar").verifyError(RuntimeException.class);
	}
	
	public static class User{
		private String username;

		protected User(String username) {
			this.username = username;
		}

		public String getUsername() {
			return username;
		}
		
	}
	
	@Test
	void expectSkylerJesseComplete() {
		Flux<User> flux = Flux.just(new User("swhite"), new User("jpinkman"));
		StepVerifier.create(flux)
					.assertNext(u -> assertThat(u.getUsername()).isEqualTo("swhite"))
					.assertNext(u -> assertThat(u.getUsername()).isEqualTo("jpinkman"))
					.verifyComplete();
	}
	
	
	@Test
	void expect10Elements() {
		Flux<Long> flux = Flux.interval(Duration.ofMillis(100)).take(10);
		StepVerifier.create(flux).expectNextCount(10).verifyComplete();
	}
	
	@Test
	void expect3600Elements(Supplier<Flux<Long>> supplier) {
		StepVerifier.withVirtualTime(supplier).thenAwait(Duration.ofHours(1)).expectNextCount(3600).verifyComplete();
	}
}
