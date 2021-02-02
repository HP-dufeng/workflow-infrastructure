package dangqu.powertrade.security.conf.tenant;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import dangqu.powertrade.security.conf.properties.DefaultTenantProperties;
import dangqu.powertrade.security.conf.security.SecurityScope;
import dangqu.powertrade.security.conf.security.SecurityUtils;

@Component
public class DefaultTenantProvider implements TenantProvider {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTenantProvider.class);

    private String tenantId;
    
    public DefaultTenantProvider(DefaultTenantProperties tenantProperties) {
        super();
        String configuredTenantId = tenantProperties.getTenantId();
        if (!StringUtils.isBlank(configuredTenantId)) {
            // trim whitespace as trailing whitespace are possible in properties files and easy to do
            configuredTenantId = configuredTenantId.trim();

            // quotes can help solve whitespace issues
            LOGGER.debug("Found configured tenantId: '{}'", configuredTenantId);

            this.tenantId = configuredTenantId;
        }
    }

    @Override
    public String getTenantId() {
        if (tenantId != null) {
            LOGGER.debug("Using configured tenantId: '{}'", tenantId);
            return tenantId;
        }

        SecurityScope currentSecurityScope = SecurityUtils.getCurrentSecurityScope();
        if (currentSecurityScope != null) {
            String tenantId = currentSecurityScope.getTenantId();
            // quotes can help solve whitespace issues, trimming here would not 
            // help solve the problem at source which is in user database
            LOGGER.debug("Using user tenantId: '{}'", tenantId);

            return tenantId;
        }

        LOGGER.debug("No tenantId");

        return null;
    }
    
}
