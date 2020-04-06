package com.mspark.reactor.part02;

import reactor.core.publisher.Mono;

public class Part02Mono {

	public Mono<String> emptyMono(){
		return Mono.empty();
	}
	
	public Mono<String> monoWithNoSignal(){
		return Mono.never();
	}
	
	public Mono<String> fooMono(){
		return Mono.just("Foo");
	}

	
	public Mono<String> errorMono(){
		return Mono.error(new IllegalStateException());
	}
}
