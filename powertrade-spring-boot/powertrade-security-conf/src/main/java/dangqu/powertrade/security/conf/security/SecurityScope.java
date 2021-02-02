package dangqu.powertrade.security.conf.security;

/**
 * Security scope that can be used for passing the needed security scope accessibility to the Java API.
 *
 */
public interface SecurityScope {

    /**
     * The id of the user for which the security scope is meant for
     *
     * @return the user id
     */
    String getUserId();


    /**
     * The tenant id for which the security scope is meant for
     *
     * @return the tenant id
     */
    String getTenantId();

    /**
     * Check if the security scope has the given authority.
     *
     * @param authority the authority to be checked
     * @return {@code true} if the security scope has the given authority, {@code false} otherwise
     */
    boolean hasAuthority(String authority);

}
