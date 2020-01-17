package com.gamg.easyrest.demo.entity;

import javax.ws.rs.core.MediaType;

public final class RESTHeaders {

    public static final String DOMAIN = "X-Idm-Domain";

    public static final String TOKEN = "X-Idm-Token";

    public static final String TOKEN_EXPIRE = "X-Idm-Token-Expire";

    public static final String OWNED_ENTITLEMENTS = "X-Idm-Entitlements";

    public static final String RESOURCE_KEY = "X-Idm-Key";

    /**
     * Asks for asynchronous propagation towards external resources with null priority.
     */
    public static final String NULL_PRIORITY_ASYNC = "X-Idm-Null-Priority-Async";

    /**
     * Mediatype for PNG images, not defined in {@link javax.ws.rs.core.MediaType}.
     */
    public static final String MEDIATYPE_IMAGE_PNG = "image/png";

    /**
     * Mediatype for YAML, not defined in {@link javax.ws.rs.core.MediaType}.
     */
    public static final String APPLICATION_YAML = "application/yaml";

    /**
     * Mediatype for YAML, not defined in {@link javax.ws.rs.core.MediaType}.
     */
    public static final MediaType APPLICATION_YAML_TYPE = new MediaType("application", "yaml");

    /**
     * Mediatype for multipart/mixed, not defined in {@link javax.ws.rs.core.MediaType}.
     */
    public static final String MULTIPART_MIXED = "multipart/mixed";

    /**
     * The boundary parameter name for multipart, not defined in {@link javax.ws.rs.core.MediaType}.
     */
    public static final String BOUNDARY_PARAMETER = "boundary";

    /**
     * Builds Content-Type string for multipart/mixed and the given boundary.
     *
     * @param boundary multipart boundary value
     * @return multipart/mixed Content-Type string, with given boundary
     */
    public static String multipartMixedWith(final String boundary) {
        return MULTIPART_MIXED + ";" + BOUNDARY_PARAMETER + "=" + boundary;
    }

    /**
     * Allows the client to specify a preference for the result to be returned from the server.
     * <a href="http://msdn.microsoft.com/en-us/library/hh537533.aspx">More information</a>.
     */
    public static final String PREFER = "Prefer";

    /**
     * Allows the server to inform the client about the fact that a specified preference was applied.
     * <a href="http://msdn.microsoft.com/en-us/library/hh554623.aspx">More information</a>.
     */
    public static final String PREFERENCE_APPLIED = "Preference-Applied";

    private RESTHeaders() {
        // Empty constructor for static utility class.
    }
}
