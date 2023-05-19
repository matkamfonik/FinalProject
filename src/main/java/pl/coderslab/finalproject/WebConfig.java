package pl.coderslab.finalproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import pl.coderslab.finalproject.httpClients.ClientClient;

@Configuration
public class WebConfig {

  @Bean
  WebClient webClient(ObjectMapper objectMapper) {
    return WebClient.builder()
        .baseUrl("https://dane.biznes.gov.pl/api/ceidg/v2/")
            .defaultHeader("Authorization", Token.TOKEN)
        .build();
  }

  @SneakyThrows
  @Bean
  ClientClient postClient(WebClient webClient) {
    HttpServiceProxyFactory httpServiceProxyFactory =
        HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
            .build();
    return httpServiceProxyFactory.createClient(ClientClient.class);
  }
}