package com.yup.microservices.demo.Web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("v1/inicio")
public class InicioController {
    private final Logger    logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/status")
    public String getStatus() {
        logger.info("START  [POST] /v1/inicio/status");
        return "info";
    }
    @GetMapping("/warning")
    public String getWarning() {
        logger.warn("START  [POST] /v1/inicio/status");
        return "warning";
    }
    @GetMapping("/error")
    public String getError() {
        logger.error("START  [POST] /v1/inicio/status");
        return "error";
    }
}
