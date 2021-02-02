package dangqu.powertrade.security.conf.security;

import java.util.Collection;

import java.util.stream.Stream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class PowerTradeAuthenticationSecurityScope implements SecurityScope {

    protected final Authentication authentication;

    public PowerTradeAuthenticationSecurityScope(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public String getUserId() {
        return extractAuthoritiesStartingWith(SecurityUtils.CURRENT_USER_PREFIX).findFirst().orElse("");
    }



    @Override
    public String getTenantId() {
        return extractAuthoritiesStartingWith(SecurityUtils.TENANT_PREFIX).findFirst().orElse("");
    }

    @Override
    public boolean hasAuthority(String authority) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(authority)) {
                return true;
            }
        }
        return false;
    }

    protected Stream<String> extractAuthoritiesStartingWith(String prefix) {
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith(prefix))
                .map(authority -> authority.substring(prefix.length()));
    }
}