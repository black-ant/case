package io.seata.samples.integration.storage.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_storage")
public class TStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Column(name = "commodity_code")
    private String commodityCode;

    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

//    protected Serializable pkVal() {
//        return this.id;
//    }

    @Override
    public String toString() {
        return "TStorage{" +
                ", id=" + id +
                ", commodityCode=" + commodityCode +
                ", name=" + name +
                ", count=" + count +
                "}";
    }
}
