package dangqu.powertrade.security.conf.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    
    public static final String TENANT_PREFIX = "TENANT_";
    public static final String CURRENT_USER_PREFIX = "CURRENT_USER_";

    private static SecurityScopeProvider securityScopeProvider = new PowerTradeSecurityScopeProvider();

    private SecurityUtils() {
    }

    public static GrantedAuthority createTenantAuthority(String tenantId) {
        return new SimpleGrantedAuthority(TENANT_PREFIX + tenantId);
    }


    public static void setSecurityScopeProvider(SecurityScopeProvider securityScopeProvider) {
        SecurityUtils.securityScopeProvider = securityScopeProvider;
    }

    /**
     * Get the login of the current user.
     */
    public static String getCurrentUserId() {
        SecurityScope user = getCurrentSecurityScope();
        if (user != null) {
            return user.getUserId();
        }
        return null;
    }

    public static SecurityScope getCurrentSecurityScope() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null && securityContext.getAuthentication() != null) {
            return getSecurityScope(securityContext.getAuthentication());
        }
        return null;
    }

    public static SecurityScope getSecurityScope(Authentication authentication) {
        return securityScopeProvider.getSecurityScope(authentication);
    }

    public static SecurityScope getAuthenticatedSecurityScope() {
        SecurityScope currentSecurityScope = getCurrentSecurityScope();
        if (currentSecurityScope != null) {
            return currentSecurityScope;
        }
        throw new IllegalStateException("User is not authenticated");
    }
}
