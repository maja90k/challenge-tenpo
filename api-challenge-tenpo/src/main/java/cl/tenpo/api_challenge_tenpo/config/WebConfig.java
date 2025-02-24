package cl.tenpo.api_challenge_tenpo.config;

import cl.tenpo.api_challenge_tenpo.utils.RateLimitingInterceptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@SpringBootApplication

public class WebConfig implements WebMvcConfigurer {

  @Autowired
  private RateLimitingInterceptorUtil rateLimitingInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(rateLimitingInterceptor);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/")
        .setViewName("redirect:/swagger-ui.html");

    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    WebMvcConfigurer.super.addViewControllers(registry);
  }
}