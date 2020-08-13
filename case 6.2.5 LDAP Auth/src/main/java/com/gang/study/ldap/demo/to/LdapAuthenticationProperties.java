package com.gang.study.ldap.demo.to;


import java.util.ArrayList;
import java.util.List;

/**
 * This is {@link LdapAuthenticationProperties}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
public class LdapAuthenticationProperties extends AbstractLdapAuthenticationProperties {

    private PasswordPolicyProperties passwordPolicy = new PasswordPolicyProperties();

    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();

    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();

    private String credentialCriteria;

    private String principalAttributeId;

    private List principalAttributeList = new ArrayList();
    private boolean allowMultiplePrincipalAttributeValues;
    private List additionalAttributes = new ArrayList();

    private boolean allowMissingPrincipalAttributeValue = true;

    private Integer order;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(final Integer order) {
        this.order = order;
    }

    public PasswordPolicyProperties getPasswordPolicy() {
        return passwordPolicy;
    }

    public void setPasswordPolicy(final PasswordPolicyProperties passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
    }

    public PrincipalTransformationProperties getPrincipalTransformation() {
        return principalTransformation;
    }

    public void setPrincipalTransformation(final PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
    }

    public PasswordEncoderProperties getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(final PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String getPrincipalAttributeId() {
        return principalAttributeId;
    }

    public void setPrincipalAttributeId(final String principalAttributeId) {
        this.principalAttributeId = principalAttributeId;
    }

    public List getPrincipalAttributeList() {
        return principalAttributeList;
    }

    public void setPrincipalAttributeList(final List principalAttributeList) {
        this.principalAttributeList = principalAttributeList;
    }

    public boolean isAllowMultiplePrincipalAttributeValues() {
        return allowMultiplePrincipalAttributeValues;
    }

    public void setAllowMultiplePrincipalAttributeValues(final boolean allowMultiplePrincipalAttributeValues) {
        this.allowMultiplePrincipalAttributeValues = allowMultiplePrincipalAttributeValues;
    }

    public List getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(final List additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public String getCredentialCriteria() {
        return credentialCriteria;
    }

    public void setCredentialCriteria(final String credentialCriteria) {
        this.credentialCriteria = credentialCriteria;
    }

    public boolean isAllowMissingPrincipalAttributeValue() {
        return allowMissingPrincipalAttributeValue;
    }

    public void setAllowMissingPrincipalAttributeValue(final boolean allowMissingPrincipalAttributeValue) {
        this.allowMissingPrincipalAttributeValue = allowMissingPrincipalAttributeValue;
    }
}
