package com.gang.azure.bundles.gangazurebundles.entity;

/**
 * @Classname AzureConfigTO
 * @Description TODO
 * @Date 2020/3/31 21:20
 * @Created by zengzg
 */
public class AzureConfigTO {

    private AzureClientTokenTO azureClientTokenTO;

    private String tenant = "303370752qq.onmicrosoft.com";

    public AzureClientTokenTO getAzureClientTokenTO() {
        return azureClientTokenTO;
    }

    public void setAzureClientTokenTO(AzureClientTokenTO azureClientTokenTO) {
        this.azureClientTokenTO = azureClientTokenTO;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
