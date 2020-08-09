package com.gang.study.ldap.demo.to;

import lombok.Getter;
import lombok.Setter;

/**
 * This is {@link AbstractLdapSearchProperties}.
 *
 * @author Misagh Moayyed
 * @since 5.3.0
 */

@Getter
@Setter
public abstract class AbstractLdapSearchProperties extends AbstractLdapProperties {
    private static final long serialVersionUID = 3009946735155362639L;

    /**
     * User filter to use for searching.
     * Syntax is {@code cn={user}} or {@code cn={0}}.
     */
    protected String searchFilter;

    /**
     * Whether subtree searching is allowed.
     */
    private boolean subtreeSearch = true;

    /**
     * Base DN to use.
     */
    private String baseDn;
}
