package com.gang.mongodb.demo.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.UpdateDescription;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @Classname ChangeStreamServiceImpl
 * @Description TODO
 * @Date 2023/8/26
 * @Created by zengzg
 */
@Service
public class ChangeStreamServiceImpl {


    @Autowired
    private MongoTemplate mongoTemplate;

    public void doChange() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("user");

        // 获取 Change Streams 游标
        MongoCursor<ChangeStreamDocument<Document>> cursor = collection.watch().iterator();

        // 处理变更事件
        while (cursor.hasNext()) {
            ChangeStreamDocument<Document> change = cursor.next();
            Document fullDocument = change.getFullDocument();
            UpdateDescription updateDescription = change.getUpdateDescription();
            System.out.println("Change event: " + fullDocument);
            System.out.println("Update event: " + updateDescription);
        }

        // 关闭游标和连接
        cursor.close();
    }
}
