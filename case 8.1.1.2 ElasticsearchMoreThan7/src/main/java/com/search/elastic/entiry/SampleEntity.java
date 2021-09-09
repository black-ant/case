package com.search.elastic.entiry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Classname Sample
 * @Description TODO
 * @Date 2021/7/15
 * @Created by zengzg
 */
@Document(indexName = "info", type = "sample")
public class SampleEntity {

    @Id
    @Field(type = FieldType.Integer, store = true)
    private String id;
    @Field(type = FieldType.Text, store = true)
    private String infoTitle;
    @Field(type = FieldType.Text, store = true)
    private String infoDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getInfoDetails() {
        return infoDetails;
    }

    public void setInfoDetails(String infoDetails) {
        this.infoDetails = infoDetails;
    }
}
