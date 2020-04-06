package com.mspark.reactor.part01;

import java.time.Duration;
import java.util.Arrays;

import reactor.core.publisher.Flux;

public class Part01Flux {

	public Flux<String> emptyFlux(){
		return Flux.empty();
	}
	
	public Flux<String> fooBarFluxFromValue(){
		return Flux.just("Foo", "Bar").log();
	}
	
	public Flux<String> fooBarFluxFromList(){
		return Flux.fromIterable(Arrays.asList("Foo", "Bar")).log();
	}
	
	public Flux<String> errorFlux(){
		return Flux.error(new IllegalStateException());
	}
	
	public Flux<Long> counter(){
		return Flux.interval(Duration.ofMillis(100)).take(10);
	}
}
