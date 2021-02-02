package dangqu.powertrade.security.conf.security;

import org.springframework.security.core.Authentication;

/**
 * Interface responsible for providing the security for a given authentication.
 *
 */
public interface SecurityScopeProvider {

    /**
     * Get the PowerTrade Security scope from the given authentication. It should never be null.
     *
     * @param authentication the authentication for the security scope
     * @return the non null security scope for the given authentication
     */
    SecurityScope getSecurityScope(Authentication authentication);

}
