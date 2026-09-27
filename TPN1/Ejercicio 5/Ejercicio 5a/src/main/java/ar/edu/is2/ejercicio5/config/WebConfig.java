package ar.edu.is2.ejercicio5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  private final AutenticacionInterceptor autenticacionInterceptor;

  public WebConfig(AutenticacionInterceptor autenticacionInterceptor) {
    this.autenticacionInterceptor = autenticacionInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(autenticacionInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/login", "/registro", "/assets/**", "/error", "/favicon.ico");
  }
}
