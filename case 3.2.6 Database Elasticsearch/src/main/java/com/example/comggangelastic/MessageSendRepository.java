package com.example.comggangelastic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname MessageSendRepository
 * @Description 消息发送
 * @Date 2022/10/25
 */
@Repository
public interface MessageSendRepository extends ElasticsearchRepository<MessageSendEntity, Long> {

    List<MessageSendEntity> findByRequestIdOrderByCreateTimeDesc(String requestId);

    List<MessageSendEntity> findByMsgChannel(String msgChannel);

    long countByMsgChannel(String msgChannel);

    Page<MessageSendEntity> findByBatchNo(Pageable pageable, String eventId);
}

