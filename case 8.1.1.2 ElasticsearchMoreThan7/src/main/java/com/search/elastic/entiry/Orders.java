package com.search.elastic.entiry;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/13 16:40
 * @Version 1.0
 **/
@Document(indexName = "scsystemorder", type = "orders")
public class Orders {

    @Id
    @Field(type = FieldType.Integer, store = true)
    private Integer id;

    @Field(type = FieldType.Text, store = true)
    private String orderdesc;

//    @Field(type = FieldType.Date, store = true)
    private Date createDate;

    @Field(type = FieldType.Double, store = true)
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderdesc() {
        return orderdesc;
    }

    public void setOrderdesc(String orderdesc) {
        this.orderdesc = orderdesc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
