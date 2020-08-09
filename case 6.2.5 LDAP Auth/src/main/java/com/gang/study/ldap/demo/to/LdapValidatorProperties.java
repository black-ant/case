package com.gang.study.ldap.demo.to;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;

/**
 * This is {@link LdapValidatorProperties}.
 *
 * @author Misagh Moayyed
 * @since 5.3.0
 */
@Getter
@Setter
public class LdapValidatorProperties implements Serializable {

    private static final long serialVersionUID = 1150417354213235193L;

    /**
     * The following LDAP validators can be used to test connection health status:
     * <ul>
     * <li>{@code search}: Validates a connection is healthy by performing a search operation.
     * Validation is considered successful if the search result size is greater than zero.</li>
     * <li>{@code none}: No validation takes place.</li>
     * <li>{@code compare}: Validates a connection is healthy by performing a compare operation.</li>
     * </ul>
     */
    private String type = "search";

    /**
     * Base DN to use for the search request of the search validator.
     */
    private String baseDn = StringUtils.EMPTY;

    /**
     * Search filter to use for the search request of the search validator.
     */
    private String searchFilter = "(objectClass=*)";

    /**
     * Search scope to use for the search request of the search validator.
     */
    private String scope = "OBJECT";

    /**
     * Attribute name to use for the compare validator.
     */
    private String attributeName = "objectClass";

    /**
     * Attribute values to use for the compare validator.
     */
    private List<String> attributeValues = Stream.of("top").collect(Collectors.toList());

    /**
     * DN to compare to use for the compare validator.
     */
    private String dn = StringUtils.EMPTY;
}
