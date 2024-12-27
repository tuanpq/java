package io.github.tuanpq.javafileio.common.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.UrlHandlerFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Bean
	FilterRegistrationBean<OncePerRequestFilter> urlHandlerFilterRegistrationBean() {
		FilterRegistrationBean<OncePerRequestFilter> registrationBean = new FilterRegistrationBean<>();
		UrlHandlerFilter urlHandlerFilter = UrlHandlerFilter
				.trailingSlashHandler("/files/**").wrapRequest()
				//.trailingSlashHandler("/files/**").redirect(HttpStatus.PERMANENT_REDIRECT)
				.build();
		registrationBean.setFilter(urlHandlerFilter);

		return registrationBean;
	}

}
