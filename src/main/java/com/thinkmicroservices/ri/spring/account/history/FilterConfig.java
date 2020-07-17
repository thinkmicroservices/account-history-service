package com.thinkmicroservices.ri.spring.account.history;

import com.thinkmicroservices.ri.spring.account.history.filter.JWTFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author cwoodward
 */
@Configuration
@Slf4j
public class FilterConfig {

    protected static final String URL_PATTERN_ACCOUNT_HISTORY = "/account/history/*";

    @Bean
    public FilterRegistrationBean<JWTFilter> jwtFilterRegistration() {
        //FilterRegistrationBean<JWTFilter> filterRegistrationBean
        //        = new FilterRegistrationBean<>(new JWTFilter());
        FilterRegistrationBean<JWTFilter> filterRegistrationBean
                = new FilterRegistrationBean<>();
        
        filterRegistrationBean.setFilter(new JWTFilter());
 
        filterRegistrationBean.addUrlPatterns(URL_PATTERN_ACCOUNT_HISTORY);
        log.debug("JWTFilter patterns {}", filterRegistrationBean.getUrlPatterns());
        return filterRegistrationBean;
    }

}
