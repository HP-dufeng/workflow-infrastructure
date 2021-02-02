package dangqu.powertrade.security.conf.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "powertrade.tenant")
public class DefaultTenantProperties {
    /**
     * The static tenant id used for the DefaultTenantProvider. The modeler app uses
     * this to determine under which tenant id to store and publish models. When not
     * provided, empty or only contains whitespace it defaults to the user's tenant
     * id if available otherwise it uses no tenant id.
     */
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
