package dangqu.powertrade.security.conf.security;

import org.springframework.security.core.Authentication;

public class PowerTradeSecurityScopeProvider implements SecurityScopeProvider {

    @Override
    public SecurityScope getSecurityScope(Authentication authentication) {
        return new PowerTradeAuthenticationSecurityScope(authentication);
    }
}
