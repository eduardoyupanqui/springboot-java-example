package com.yup.microservices.demo.Web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("v1/inicio")
public class InicioController {
    private final Logger    logger = LoggerFactory.getLogger(getClass());
    @Value("${prueba.config}")
    private String PruebaConfig;

    @GetMapping("/")
    public String getHome() {
        logger.info("START  [GET] /");
        logger.info("Hola Spring Boot, valor de prueba.config es "+PruebaConfig);
        return "blank";
    }

    @GetMapping("/status")
    public String getStatus() {
        logger.info("START  [POST] /v1/inicio/status");
        return "info";
    }
    @GetMapping("/warning")
    public String getWarning() {
        logger.warn("START  [POST] /v1/inicio/warning");
        return "warning";
    }
    @GetMapping("/error")
    public String getError() {
        try {
            throw new Exception("START  [POST] /v1/inicio/error");
        } catch (Exception e) {
            logger.error("Some error occured", e);
        }
        return "error";
    }
}
