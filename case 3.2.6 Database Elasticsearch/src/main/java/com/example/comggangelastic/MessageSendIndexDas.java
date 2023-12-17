package com.example.comggangelastic;

import cn.hutool.core.date.DateUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class MessageSendIndexDas {

    @Resource
    private MessageSendRepository messageSendRepository;

    public MessageSendEntity findById(Long id) {
        Optional<MessageSendEntity> messageSendEntity = messageSendRepository.findById(id);
        return messageSendEntity.orElse(null);
    }

    public List<MessageSendEntity> findByRequestId(String requestId) {
        return messageSendRepository.findByRequestIdOrderByCreateTimeDesc(requestId);
    }

    public List<MessageSendEntity> findByMsgChannel(String msgChannel) {
        return messageSendRepository.findByMsgChannel(msgChannel);
    }

    public Page<MessageSendEntity> findByBatchNo(Pageable pageable, String eventId) {
        return messageSendRepository.findByBatchNo(pageable, eventId);
    }

    public long countByMsgChannel(String msgChannel) {
        return messageSendRepository.countByMsgChannel(msgChannel);
    }

    public Page<MessageSendEntity> searchByHyMessageLogQuery(HyMessageLogQueryDto hyMessageLogQueryDto) {
        return messageSendRepository.search(buildQuery(hyMessageLogQueryDto));
    }

    private NativeSearchQuery buildQuery(HyMessageLogQueryDto hyMessageLogQueryDto) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(hyMessageLogQueryDto.getMsgChannel())) {
            booleanQueryBuilder.must(QueryBuilders.termQuery("msgChannel", hyMessageLogQueryDto.getMsgChannel()));
        }
        if (!StringUtils.isEmpty(hyMessageLogQueryDto.getBatchNo())) {
            booleanQueryBuilder.must(QueryBuilders.matchPhraseQuery("batchNo", hyMessageLogQueryDto.getBatchNo()));
        }
        if (!StringUtils.isEmpty(hyMessageLogQueryDto.getMsgTarget())) {
            booleanQueryBuilder.must(QueryBuilders.termQuery("msgTarget", hyMessageLogQueryDto.getMsgTarget()));
        }
        if (!StringUtils.isEmpty(hyMessageLogQueryDto.getRequestId())) {
            booleanQueryBuilder.must(QueryBuilders.termQuery("requestId", hyMessageLogQueryDto.getRequestId()));
        }
        if (!StringUtils.isEmpty(hyMessageLogQueryDto.getPushStatusCode())) {
            booleanQueryBuilder.must(QueryBuilders.termQuery("pushStatusCode", hyMessageLogQueryDto.getPushStatusCode()));
        }
        if (hyMessageLogQueryDto.getStartTime() != null) {
            String dataTime = DateUtil.formatDateTime(hyMessageLogQueryDto.getStartTime());
            booleanQueryBuilder.must(QueryBuilders.rangeQuery("createTime").gt(dataTime));
        }
        if (hyMessageLogQueryDto.getEndTime() != null) {
            String dataTime = DateUtil.formatDateTime(hyMessageLogQueryDto.getEndTime());
            booleanQueryBuilder.must(QueryBuilders.rangeQuery("createTime").lt(dataTime));
        }
        builder.withQuery(booleanQueryBuilder);

        return builder.build();
    }


}
