package com.mspark.reactor.part06;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class RequestTest {

	@Test
	public void test() {
		/**
		 * 값이 출력되지 않는 로직
		 * Flux의 실행은 새로운 데몬스레드에서 실행되는데  생성된 플럭스는 각 100 milli seconds의 지연시간을 가지며 생성된다.
		 * flux가 생성되기 이전에 유저 스레드가 끝나면서 데몬스레드는 실행되지 않았다.
		 * 해당 로직을 출력하기 위해서는 데몬스레드가 끝나기까지 유저 스레드를 잠시 sleep 시키는 방법과 blockLast() 등과 같은 메서드를 통해 출력할 수 있다.
		 */
		Flux<Long> flux = Flux.interval(Duration.ofMillis(100)).take(10).log();
		flux.doOnNext(System.out::println)
//			.subscribe();
			.blockLast();

		
		// 수 많은 엘리먼트를 가지는 Flux에 대해서  부분만 테스트를 하는 경우  thenCancel() 호출을 통해 Flux의 흐름을 중단하고 verify()를 호출할 수 있다.
		Flux<Integer> flux2 = Flux.just(0, 1, 2).log();
		
		
		StepVerifier.create(flux2, 1)
					.expectNext(0)
					.thenRequest(1)
					.expectNext(1)
					.thenCancel()
					.verify();

		// 다른 방법으로 take 메서드를 통해 새로운 Flux를 만들고  upstream에 cancel을 날려 원하는 Flux에 대해서 테스트 할 수 잇다.
//		Flux<Integer> flux2 = Flux.just(0, 1, 2).take(2)log();
//		StepVerifier.create(flux2, 1)
//					.expectNext(0)
//					.thenRequest(1)
//					.expectNext(1)
//					.verifyComplete();
	
		// Backpressure 구현 , Subscriber 구현, Spring Webflux 사용시 기본적으로 31로 고정
		Flux.range(1,  100)
		 	.log()
		 	.doOnNext(System.out::println)
		 	.subscribe(new Subscriber<Integer>() {

		 		private Subscription subscription;
		 		private int count;
		 		
				@Override
				public void onSubscribe(Subscription s) {
					this.subscription = s;
					this.subscription.request(10);
				}

				@Override
				public void onNext(Integer t) {
					count++;
					if(count % 10 == 0) {
						this.subscription.request(10);
					}
				}

				@Override
				public void onError(Throwable t) {
				}

				@Override
				public void onComplete() {
				}

			});
	}
}
