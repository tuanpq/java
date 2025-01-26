package io.github.tuanpq.javafileio.common.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.UrlHandlerFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(WebConfiguration.class);

	@Bean
	FilterRegistrationBean<OncePerRequestFilter> urlHandlerFilterRegistrationBean() {
		logger.debug("urlHandlerFilterRegistrationBean: start");

		FilterRegistrationBean<OncePerRequestFilter> registrationBean = new FilterRegistrationBean<>();
		UrlHandlerFilter urlHandlerFilter = UrlHandlerFilter
				.trailingSlashHandler("/files/**").wrapRequest()
				// .trailingSlashHandler("/files/**").redirect(HttpStatus.PERMANENT_REDIRECT)
				.build();
		registrationBean.setFilter(urlHandlerFilter);

		logger.debug("urlHandlerFilterRegistrationBean: end");
		return registrationBean;
	}

}
