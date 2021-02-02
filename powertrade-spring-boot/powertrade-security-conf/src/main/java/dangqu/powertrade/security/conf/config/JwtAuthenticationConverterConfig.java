package dangqu.powertrade.security.conf.config;

import org.apache.catalina.security.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import dangqu.powertrade.security.conf.constants.RequestHeaderConstants;
import dangqu.powertrade.security.conf.security.SecurityUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


import javax.servlet.http.HttpServletRequest;

@Configuration
public class JwtAuthenticationConverterConfig implements RequestHeaderConstants {
    

    @Autowired
    private HttpServletRequest httpRequest;

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new CustomJwtGrantedAuthoritiesConverter());
        return converter;
    }

    public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            String tenant = httpRequest.getHeader(TEANT_REQUEST_HEADER);
            String currentUserId = httpRequest.getHeader(CURRENT_USER_REQUEST_HEADER);


            Collection<GrantedAuthority> grantedAuthorities = jwtGrantedAuthoritiesConverter.convert(jwt);
            Collection<GrantedAuthority> updatedGrantedAuthorities = new ArrayList<>(grantedAuthorities);

            

            if (StringUtils.isNotEmpty(tenant)){
                updatedGrantedAuthorities.add(new SimpleGrantedAuthority(SecurityUtils.TENANT_PREFIX + tenant));
            }

            if (StringUtils.isNotEmpty(currentUserId)){
                updatedGrantedAuthorities.add(new SimpleGrantedAuthority(SecurityUtils.CURRENT_USER_PREFIX + currentUserId));
            }

            return updatedGrantedAuthorities;
        }
    }
}
