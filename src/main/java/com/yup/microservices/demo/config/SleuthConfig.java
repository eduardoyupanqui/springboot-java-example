package com.yup.microservices.demo.config;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebFilter;

@Configuration(proxyBeanMethods = false)
public class SleuthConfig {
    // Example of a servlet Filter for non-reactive applications
    @Bean
    Filter traceIdInResponseFilter(Tracer tracer) {
        return (request, response, chain) -> {
            Span currentSpan = tracer.currentSpan();
            if (currentSpan != null) {
                HttpServletResponse resp = (HttpServletResponse) response;
                // putting trace id value in [mytraceid] response header
                resp.addHeader("request-id", currentSpan.context().traceId());
            }
            chain.doFilter(request, response);
        };
    }

    // Example of a reactive WebFilter for reactive applications
//    @Bean
//    WebFilter traceIdInResponseFilter(Tracer tracer) {
//        return (exchange, chain) -> {
//            Span currentSpan = tracer.currentSpan();
//            if (currentSpan != null) {
//                // putting trace id value in [mytraceid] response header
//                exchange.getResponse().getHeaders().add("mytraceid", currentSpan.context().traceId());
//            }
//            return chain.filter(exchange);
//        };
//    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
