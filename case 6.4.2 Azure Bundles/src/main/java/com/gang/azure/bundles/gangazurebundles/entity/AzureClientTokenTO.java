package com.gang.azure.bundles.gangazurebundles.entity;

/**
 * @Classname ConfigEntity
 * @Description TODO
 * @Date 2020/3/31 12:03
 * @Created by zengzg
 */
public class AzureClientTokenTO {

    public String client_id = "bdfcdc00-0177-4499-bd8d-98984d2506f7";
    public String scope = "https://graph.windows.net/.default";
    public String client_secret = "Cd_=2u9V2zV5/iQ?[bepTN57ze=hd[]?";
    public String grant_type = "client_credentials";

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
