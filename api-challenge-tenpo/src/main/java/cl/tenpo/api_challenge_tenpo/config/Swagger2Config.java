package cl.tenpo.api_challenge_tenpo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class Swagger2Config {
  private static final String ARTIFACT_ID = "api-tenpo-challenge";

  private static final String NOMBRE_SISTEMA = "api-tenpo-challenge";

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("cl.tenpo.api_challenge_tenpo.controller"))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/");
  }
}