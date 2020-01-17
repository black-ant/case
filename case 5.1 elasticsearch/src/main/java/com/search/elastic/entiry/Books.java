package com.search.elastic.entiry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/13 16:39
 * @Version 1.0
 **/

@Data
@Document(indexName = "scsystem", type = "books")
public class Books {

    @Id
    @Field(type = FieldType.Integer, store = true)
    private Integer id;
    @Field(type = FieldType.Text, store = true)
    private String title;
    @Field(type = FieldType.Text, store = true)
    private String desc;
//    @Field(type = FieldType.Double, store = true)
    private BigDecimal price;
    @Field(type = FieldType.Text, store = true)
    private String author;
    @Field(store = true)
    private Date pubdate;

    public Books() {
    }

    public Books(Integer id, String title, String desc, BigDecimal price, String author, Date pubdate) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.author = author;
        this.pubdate = pubdate;
    }
}
