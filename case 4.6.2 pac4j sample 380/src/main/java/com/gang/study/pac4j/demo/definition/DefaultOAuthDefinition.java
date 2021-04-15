package com.gang.study.pac4j.demo.definition;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gang.study.pac4j.demo.api.DefaultOAuthAPI;
import com.gang.study.pac4j.demo.converter.DefaultDateConverter;
import com.gang.study.pac4j.demo.profile.DefaultOAuhtProfile;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.casoauthwrapper.CasAuthenticationDateFormatter;
import org.pac4j.oauth.profile.definition.OAuth20ProfileDefinition;

import java.util.Iterator;

import static org.pac4j.core.profile.AttributeLocation.PROFILE_ATTRIBUTE;

/**
 * @Classname OAuthDefinition
 * @Description TODO
 * @Date 2021/4/14
 * @Created by zengzg
 */
public class DefaultOAuthDefinition extends OAuth20ProfileDefinition<DefaultOAuhtProfile, OAuth20Configuration> {

    public static final String IS_FROM_NEW_LOGIN = "isFromNewLogin";
    public static final String AUTHENTICATION_DATE = "authenticationDate";
    public static final String AUTHENTICATION_METHOD = "authenticationMethod";
    public static final String SUCCESSFUL_AUTHENTICATION_HANDLERS = "successfulAuthenticationHandlers";
    public static final String LONG_TERM_AUTHENTICATION_REQUEST_TOKEN_USED = "longTermAuthenticationRequestTokenUsed";

    public DefaultOAuthDefinition() {
        super(x -> new DefaultOAuhtProfile());
        primary(IS_FROM_NEW_LOGIN, Converters.BOOLEAN);
        primary(AUTHENTICATION_DATE, new DefaultDateConverter());
        primary(AUTHENTICATION_METHOD, Converters.STRING);
        primary(SUCCESSFUL_AUTHENTICATION_HANDLERS, Converters.STRING);
        primary(LONG_TERM_AUTHENTICATION_REQUEST_TOKEN_USED, Converters.BOOLEAN);
    }

    @Override
    public String getProfileUrl(final OAuth2AccessToken accessToken, final OAuth20Configuration configuration) {
        return ((DefaultOAuthAPI) configuration.getApi()).getRootEndpoint() + "/profile";
    }

    @Override
    public DefaultOAuhtProfile extractUserProfile(final String body) {
        final DefaultOAuhtProfile profile = newProfile();
        final String attributesNode = "attributes";
        JsonNode json = JsonHelper.getFirstNode(body);
        if (json != null) {
            profile.setId(ProfileHelper.sanitizeIdentifier(profile, JsonHelper.getElement(json, "id")));
            json = json.get(attributesNode);
            if (json != null) {
                // CAS <= v4.2
                if (json instanceof ArrayNode) {
                    final Iterator<JsonNode> nodes = json.iterator();
                    while (nodes.hasNext()) {
                        json = nodes.next();
                        final String attribute = json.fieldNames().next();
                        convertAndAdd(profile, PROFILE_ATTRIBUTE, attribute, JsonHelper.getElement(json, attribute));
                    }
                    // CAS v5
                } else if (json instanceof ObjectNode) {
                    final Iterator<String> keys = json.fieldNames();
                    while (keys.hasNext()) {
                        final String key = keys.next();
                        convertAndAdd(profile, PROFILE_ATTRIBUTE, key, JsonHelper.getElement(json, key));
                    }
                }
            } else {
                raiseProfileExtractionJsonError(body, attributesNode);
            }
        } else {
            raiseProfileExtractionJsonError(body);
        }
        return profile;
    }
}

