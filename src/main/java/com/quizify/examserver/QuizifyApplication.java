package com.quizify.examserver;

import com.quizify.examserver.model.user.Role;
import com.quizify.examserver.model.user.User;
import com.quizify.examserver.model.user.UserRole;
import com.quizify.examserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class QuizifyApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(QuizifyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		System.out.println("********************************");
//		User user = new User();
//		user.setFirstName("admin");
//		user.setLastName("admin");
//		user.setEmail("admin@quizify.com");
//		user.setUsername("admin");
//		user.setPassword(this.bCryptPasswordEncoder.encode("Admin#123"));
//		user.setPhone("986651656");
//		user.setProfile("default.png");
//
//		Role role = new Role();
//		role.setRoleName("ADMIN");
//
//		UserRole userRole = new UserRole();
//		userRole.setRole(role);
//		userRole.setUser(user);
//
//		Set<UserRole> userRoles = new HashSet<>();
//		userRoles.add(userRole);
//
//		User user1 = this.userService.createUser(user, userRoles);
//		System.out.println(user1);
	}

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://quizifyapp.herokuapp.com")); // Provide list of origins if you want multiple origins
		config.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
