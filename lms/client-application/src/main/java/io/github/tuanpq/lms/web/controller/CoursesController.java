package io.github.tuanpq.lms.web.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class CoursesController {

    private static final Logger logger = LoggerFactory.getLogger(CoursesController.class);

    private WebClient webClient;

    public CoursesController(WebClient webClient) {
        logger.trace("=====LMS===== CoursesController: start");

        this.webClient = webClient;

        logger.trace("=====LMS===== CoursesController: end");
    }

    @GetMapping(value = "/courses")
    public String[] getCourses(@RegisteredOAuth2AuthorizedClient("lms-client-authorization-code") OAuth2AuthorizedClient authorizedClient) {
        logger.trace("=====LMS===== getCourses: start");

        String[] courses = this.webClient
            .get()
            .uri("http://127.0.0.1:8090/courses")
            .attributes(oauth2AuthorizedClient(authorizedClient))
            .retrieve()
            .bodyToMono(String[].class)
            .block();

        logger.trace("=====LMS===== getCourses: end");

        return courses;
    }

}
