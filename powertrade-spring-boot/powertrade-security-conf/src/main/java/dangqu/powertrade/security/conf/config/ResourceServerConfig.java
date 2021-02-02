package dangqu.powertrade.security.conf.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {


	@Autowired(required = false)
	ServiceWebSecurityConfigurer serviceSecConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		if (serviceSecConfig != null) serviceSecConfig.configure(http);



		http
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
					.antMatchers(getPermitUrls()).permitAll()
					.anyRequest().authenticated())
			.oauth2ResourceServer(oauth2ResourceServer ->
				oauth2ResourceServer
					.jwt())
			.oauth2Client();
	}

	private String[] getPermitUrls() {
		String permitUrls[] = {
            "/**/swagger-resources/**",
			"/**/swagger-ui/index.html",
			"/**/swagger/**",
			"/**/swagger**/**",
            "/**/v*/api-docs",
            "/**/*.js",
            "/**/*.css",
            "/**/*.png",
            "/**/*.ico",
            "/webjars/springfox-swagger-ui/**",
            "/actuator/**",
            "/health/**"
		};
		
		return permitUrls;
	}

}