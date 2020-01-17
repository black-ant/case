package net.gang.bundles.demo.service;

public interface ConnInstance{

    void setConnectorName(String connectorName);

    String getConnectorName();

    void setDisplayName(String displayName);

    String getDisplayName();

    void setLocation(String location);

    String getLocation();


    void setVersion(String version);

    String getVersion();

    void setBundleName(String bundleName);

    String getBundleName();

    void setConnRequestTimeout(Integer timeout);

    Integer getConnRequestTimeout();

}
