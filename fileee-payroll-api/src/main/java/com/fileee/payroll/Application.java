package com.fileee.payroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Controller
    static class SwaggerWelcome {
        @GetMapping("/")
        public String home() {
            //noinspection SpringMVCViewInspection
            return "redirect:/swagger-ui.html";
        }
    }

}
