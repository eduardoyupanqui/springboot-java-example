package com.yup.microservices.demo.Web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.reactive.function.client.WebClient;

@RestController()
@RequestMapping("sleuth/v1")
public class SleuthController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/")
    public String getHome() {
        logger.info("START  [GET] /");
        return "blank";
    }

    @GetMapping("/status")
    public String getStatus() {
        logger.info("START  [POST] /v1/inicio/status");
        return "info";
    }

    @GetMapping("/callhome")
    public String callhome() {
        logger.info("START  [GET] /callhome");

        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8011/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("sleuth/v1/").build())
                //.uri("http://localhost:8011/sleuth/v1/")
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }
}
