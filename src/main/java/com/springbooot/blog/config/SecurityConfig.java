package com.springbooot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// once we annotate this class with @Configuration annotation, then this class becomes a java based configuration
//and within this Java based configuration, we can define all the spring bean definitions.
@EnableMethodSecurity // Method Level Security

public class SecurityConfig {

    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return  configuration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        //authorize.anyRequest().authenticated() // comment ed bcoz we are not going to authenticate All the request , infact we are going to provide access to some of the Apis
                        authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                                .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());
        return http.build();

    }
    /*As we are doing Data base Authentication so, this in memory Objects are  not required. */
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails ramesh = User.builder()
//                .username("rahul")
//                .password(passwordEncoder().encode("Under*100"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return  new InMemoryUserDetailsManager(
//                ramesh,admin
//        );
//    }

}
