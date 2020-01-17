//package com.gang.study.cloud.stream.demo.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.autoconfigure.AutoConfigureOrder;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import para.cic.sync.common.api.SyncEntityConsumer;
//import para.cic.sync.common.api.SyncEntityProducer;
//import para.cic.sync.common.api.SyncEventConsumer;
//import para.cic.sync.common.api.SyncEventProducer;
//import para.cic.sync.common.config.SyncEventAutoConfiguration;
//import para.cic.sync.common.config.SyncProperties;
//
//@Configuration
//@EnableConfigurationProperties({
//        SyncProperties.class
//})
//// @ConditionalOnBean(ConfigurableApplicationContext.class)
//@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE - 1)
//@ConditionalOnProperty(name = SyncProperties.PREFIX + ".enable", havingValue = "true", matchIfMissing = true)
//public class SyncEventAutoConfigurationTest {
//
//    private static Logger LOG = LoggerFactory.getLogger(SyncEventAutoConfiguration.class);
//
//    @ConditionalOnProperty(name = SyncProperties.PREFIX + ".event-producer", havingValue = "true", matchIfMissing = true)
//    @EnableBinding(SyncEventProducer.class)
//    public class EventProducer implements ApplicationRunner {
//        @Override
//        public void run(ApplicationArguments args) throws Exception {
//            LOG.info("-----------> run EventProducer");
//        }
//    }
//
//    @ConditionalOnProperty(name = SyncProperties.PREFIX + ".entity-consumer", havingValue = "true", matchIfMissing = true)
//    @EnableBinding(SyncEntityConsumer.class)
//    public class EntityConsumer implements ApplicationRunner {
//        @Override
//        public void run(ApplicationArguments args) throws Exception {
//            LOG.info("-----------> run EntityConsumer");
//        }
//    }
//
//    @ConditionalOnProperty(name = SyncProperties.PREFIX + ".event-consumer", havingValue = "true", matchIfMissing = false)
//    @EnableBinding(SyncEventConsumer.class)
//    public class EventConsumer implements ApplicationRunner {
//        @Override
//        public void run(ApplicationArguments args) throws Exception {
//            LOG.info("-----------> run EventConsumer");
//        }
//    }
//
//    @ConditionalOnProperty(name = SyncProperties.PREFIX + ".entity-producer", havingValue = "true", matchIfMissing = false)
//    @EnableBinding(SyncEntityProducer.class)
//    public class EntityProducer implements ApplicationRunner {
//
//        @Override
//        public void run(ApplicationArguments args) throws Exception {
//            LOG.info("-----------> run EntityProducer");
//        }
//    }
//
//}
