package com.gang.study.ldap.demo.to;

/**
 * This is {@link PrincipalTransformationProperties}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */

public class PrincipalTransformationProperties {

    private String prefix;
    private String suffix;
    private CaseConversion caseConversion = CaseConversion.NONE;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    public CaseConversion getCaseConversion() {
        return caseConversion;
    }

    public void setCaseConversion(final CaseConversion caseConversion) {
        this.caseConversion = caseConversion;
    }

    public enum CaseConversion {
        /**
         * No conversion.
         */
        NONE,
        /**
         * Lowercase conversion.
         */
        UPPERCASE,
        /**
         * Uppcase conversion.
         */
        LOWERCASE,
    }
}
