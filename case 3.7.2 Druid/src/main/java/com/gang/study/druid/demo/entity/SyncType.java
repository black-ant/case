package com.gang.study.druid.demo.entity;

import java.io.Serializable;

/**
 * sync_type
 *
 * @author
 */
public class SyncType implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String typeCode;

    private String typeClass;

    private String typePolicy;

    private String typeName;

    private String supplierId;

    private String supplierName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public String getTypePolicy() {
        return typePolicy;
    }

    public void setTypePolicy(String typePolicy) {
        this.typePolicy = typePolicy;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SyncType other = (SyncType) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getTypeCode() == null ? other.getTypeCode() == null :
                this.getTypeCode().equals(other.getTypeCode()))
                && (this.getTypeClass() == null ? other.getTypeClass() == null :
                this.getTypeClass().equals(other.getTypeClass()))
                && (this.getTypePolicy() == null ? other.getTypePolicy() == null :
                this.getTypePolicy().equals(other.getTypePolicy()))
                && (this.getTypeName() == null ? other.getTypeName() == null :
                this.getTypeName().equals(other.getTypeName()))
                && (this.getSupplierId() == null ? other.getSupplierId() == null :
                this.getSupplierId().equals(other.getSupplierId()))
                && (this.getSupplierName() == null ? other.getSupplierName() == null :
                this.getSupplierName().equals(other.getSupplierName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTypeCode() == null) ? 0 : getTypeCode().hashCode());
        result = prime * result + ((getTypeClass() == null) ? 0 : getTypeClass().hashCode());
        result = prime * result + ((getTypePolicy() == null) ? 0 : getTypePolicy().hashCode());
        result = prime * result + ((getTypeName() == null) ? 0 : getTypeName().hashCode());
        result = prime * result + ((getSupplierId() == null) ? 0 : getSupplierId().hashCode());
        result = prime * result + ((getSupplierName() == null) ? 0 : getSupplierName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", typeCode=").append(typeCode);
        sb.append(", typeClass=").append(typeClass);
        sb.append(", typePolicy=").append(typePolicy);
        sb.append(", typeName=").append(typeName);
        sb.append(", supplierId=").append(supplierId);
        sb.append(", supplierName=").append(supplierName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
