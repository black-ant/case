package com.example.customerservice.service;

import com.example.customerservice.config.VectorStorePersistence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 知识库服务 - RAG 功能 + 文件持久化
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeBaseService {
    
    private final VectorStore vectorStore;
    private final VectorStorePersistence vectorStorePersistence;
    
    @PostConstruct
    public void initKnowledgeBase() {
        log.info("初始化知识库...");
        
        // 检查向量存储是否已有数据
        try {
            List<Document> existingDocs = vectorStore.similaritySearch(
                SearchRequest.query("测试").withTopK(1)
            );
            
            if (!existingDocs.isEmpty()) {
                log.info("向量存储已有数据，跳过初始化");
                return;
            }
        } catch (Exception e) {
            log.debug("检查现有数据时出错，继续初始化", e);
        }
        
        // 初始化产品知识库
        List<Document> documents = List.of(
            new Document(
                "我们的退货政策：商品自收到之日起30天内可无理由退货。退货商品需保持原包装完好，未使用。退款将在收到退货后3-5个工作日内处理。",
                Map.of("category", "policy", "type", "return")
            ),
            new Document(
                "配送时效：标准配送3-5个工作日，加急配送1-2个工作日。偏远地区可能需要额外1-3天。",
                Map.of("category", "shipping", "type", "delivery")
            ),
            new Document(
                "支付方式：我们支持支付宝、微信支付、银行卡支付。所有支付均采用SSL加密，确保您的支付安全。",
                Map.of("category", "payment", "type", "methods")
            ),
            new Document(
                "会员权益：注册会员享受积分返现、生日优惠、专属客服等特权。消费满100元即可成为会员。",
                Map.of("category", "membership", "type", "benefits")
            ),
            new Document(
                "产品保修：所有电子产品享受1年质保，非人为损坏免费维修或更换。保修期内提供免费上门服务。",
                Map.of("category", "warranty", "type", "electronics")
            ),
            new Document(
                "发票开具：我们提供电子发票和纸质发票。电子发票在订单完成后自动发送到您的邮箱，纸质发票随货寄出。",
                Map.of("category", "invoice", "type", "billing")
            ),
            new Document(
                "客服工作时间：在线客服服务时间为每天9:00-21:00。非工作时间可以留言，我们会在工作时间内第一时间回复。",
                Map.of("category", "service", "type", "hours")
            ),
            new Document(
                "优惠券使用规则：优惠券不可叠加使用，每个订单只能使用一张。优惠券有效期请查看券面说明，过期自动失效。",
                Map.of("category", "promotion", "type", "coupon")
            )
        );
        
        vectorStore.add(documents);
        log.info("知识库初始化完成，共加载 {} 条知识", documents.size());
        
        // 立即保存到文件
        vectorStorePersistence.manualSave();
    }
    
    /**
     * 添加新知识到知识库
     */
    public void addKnowledge(String content, Map<String, Object> metadata) {
        Document document = new Document(content, metadata);
        vectorStore.add(List.of(document));
        log.info("添加新知识: {}", content.substring(0, Math.min(50, content.length())));
        
        // 保存到文件
        vectorStorePersistence.manualSave();
    }
    
    /**
     * 批量添加知识
     */
    public void addKnowledgeBatch(List<Document> documents) {
        vectorStore.add(documents);
        log.info("批量添加 {} 条知识", documents.size());
        
        // 保存到文件
        vectorStorePersistence.manualSave();
    }
    
    /**
     * 搜索相关知识
     */
    public List<Document> searchKnowledge(String query, int topK) {
        return vectorStore.similaritySearch(
            SearchRequest.query(query).withTopK(topK)
        );
    }
    
    /**
     * 获取知识库上下文
     */
    public String getKnowledgeContext(String query) {
        List<Document> docs = searchKnowledge(query, 3);
        if (docs.isEmpty()) {
            return "";
        }
        
        StringBuilder context = new StringBuilder("相关知识库信息：\n");
        for (int i = 0; i < docs.size(); i++) {
            context.append(i + 1).append(". ").append(docs.get(i).getContent()).append("\n");
        }
        return context.toString();
    }
}
