package ar.edu.is2.ejercicio7.config;

import ar.edu.is2.ejercicio7.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService userDetailsService(UsuarioRepository usuarios) {
    return mail ->
        usuarios
            .findByMail(mail.trim().toLowerCase())
            .map(u -> User.withUsername(u.getMail()).password(u.getClave()).roles("RECEPCION").build())
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            auth -> auth.requestMatchers("/login", "/assets/**", "/error").permitAll().anyRequest().authenticated())
        .formLogin(
            login ->
                login.loginPage("/login")
                    .usernameParameter("mail")
                    .passwordParameter("clave")
                    .defaultSuccessUrl("/", true)
                    .permitAll())
        .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());
    return http.build();
  }
}
