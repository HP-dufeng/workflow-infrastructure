package dangqu.powertrade.security.conf.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface ServiceWebSecurityConfigurer {
    void configure(HttpSecurity http) throws Exception;
}
