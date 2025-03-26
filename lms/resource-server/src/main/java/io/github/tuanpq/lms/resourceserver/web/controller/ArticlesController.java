package io.github.tuanpq.lms.resourceserver.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticlesController {

    private static final Logger logger = LoggerFactory.getLogger(ArticlesController.class);

    @GetMapping("/articles")
    public String[] getArticles() {
        logger.trace("XXX getArticles: start");
        logger.trace("XXX getArticles: end");
        return new String[]{"Article 1", "Article 2", "Article 3"};
    }

}
