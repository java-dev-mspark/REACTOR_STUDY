package com.mspark.reactor.part04;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Part04Transform {

	public static class User{
		private String username;
		private String firstname;
		private String lastname;
		
		protected User(String username, String firstname, String lastname) {
			this.username = username;
			this.firstname = firstname;
			this.lastname = lastname;
		}

		public String getUsername() {
			return username;
		}

		public String getFirstname() {
			return firstname;
		}

		public String getLastname() {
			return lastname;
		}
		
	}
	
	Mono<User> capitalizeOne(Mono<User> mono){
		return mono.map(u -> new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase()));
	}
	
	Flux<User> capitalizeMany(Flux<User> flux){
		return flux.map(u -> new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase()));
	}
	
	Flux<User> asyncCapitalizeMany(Flux<User> flux) {
		return flux.flatMap(this::asyncCapitalizeUser);
	}
	
	Mono<User> asyncCapitalizeUser(User u) {
		return Mono.just(new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase()));
	}
}
