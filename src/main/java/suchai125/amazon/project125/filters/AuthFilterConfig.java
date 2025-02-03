package suchai125.amazon.project125.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthFilterConfig {
    @Bean
    public FilterRegistrationBean loggerFilter() {
        final FilterRegistrationBean reg = new FilterRegistrationBean(new AuthFilter());
        reg.addUrlPatterns("/*");
        reg.setOrder(1); //defines filter execution order
        return reg;
    }
}
