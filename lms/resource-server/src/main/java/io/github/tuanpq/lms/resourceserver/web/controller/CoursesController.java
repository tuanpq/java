package io.github.tuanpq.lms.resourceserver.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursesController {

    private static final Logger logger = LoggerFactory.getLogger(CoursesController.class);

    @GetMapping("/courses")
    public String[] getCourses() {
        logger.trace("=====LMS===== getCourses: start");

        String[] courses = new String[]{"Core Java", "Java EE", "Spring Boot"};

        logger.trace("=====LMS===== getCourses: end");

        return courses;
    }

}
