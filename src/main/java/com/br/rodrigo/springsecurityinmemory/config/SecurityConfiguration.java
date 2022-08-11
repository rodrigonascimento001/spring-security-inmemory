package com.br.rodrigo.springsecurityinmemory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // autorize todas requisições para o endpoint raiz
        http
                .authorizeHttpRequests().antMatchers("/").permitAll()
                // e quero que autentique todas as outras requisicoes
                .anyRequest().authenticated()
                // a autenticacao se dara pelo endpoint de login /login
                .and().formLogin().loginPage("/login").permitAll()
                // e para efetuar o logout chamar o endpoint /logout
                .and().logout().logoutUrl("/logout")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                // por default o spring vem habilitado par aproteger contra ataque csrf
                // desabilitando isso para    é um tipo de ataque de websites maliciosos.
                .and().csrf().disable();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
