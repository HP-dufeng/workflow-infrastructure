package dangqu.powertrade.security.conf.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import dangqu.powertrade.security.conf.constants.RequestHeaderConstants;
import dangqu.powertrade.security.conf.security.SecurityScope;
import dangqu.powertrade.security.conf.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class WebClientConfig implements RequestHeaderConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationConverterConfig.class);

	@Bean
	WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
				new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);

		 Builder builder = WebClient.builder()
				.apply(oauth2Client.oauth2Configuration());
		
		// SecurityScope currentSecurityScope = SecurityUtils.getCurrentSecurityScope();
		
		// if(currentSecurityScope !=null) {
		// 	String tenantId = currentSecurityScope.getTenantId();
		// 	String currentUserId = currentSecurityScope.getUserId();

		// 	LOGGER.info("Tenant is: " + tenantId);
        //     LOGGER.info("Current user is: " + currentUserId);

		// 	if(StringUtils.isNotEmpty(tenantId))
		// 		builder.defaultHeader(TEANT_REQUEST_HEADER, tenantId);
		// 	if(StringUtils.isNotEmpty(currentUserId))
		// 		builder.defaultHeader(CURRENT_USER_REQUEST_HEADER, currentUserId);
		// }


		return builder.build();
	}

	@Bean
	OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository,
															OAuth2AuthorizedClientRepository authorizedClientRepository) {
		OAuth2AuthorizedClientProvider authorizedClientProvider =
				OAuth2AuthorizedClientProviderBuilder.builder()
						.clientCredentials()
						.build();

		DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
				clientRegistrationRepository, authorizedClientRepository);
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);


		return authorizedClientManager;
	}


}