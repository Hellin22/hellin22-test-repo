package com.hellin22.kafkaTestService.config;

import com.hellin22.kafkaTestService.chatting.dto.ChatMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, ChatMessage> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "websocket-group");
                // 카프카 컨슈머 그룹 id -> 같은 곳은 안받음.
                // 즉, 스프링 서버가 여러대로 scale out 한다고 해도 같은 group_id으로 컨슈머 그룹을 만들고 있기 때문에 중복해서 메시지를 읽지는 않음
                config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(ChatMessage.class));
    }

    //    @KafkaListener가 Kafka 메시지를 비동기로 수신할 수 있도록
    //    Kafka 컨슈머 쓰레드 풀(컨테이너)을 만들어주는 팩토리 클래스.
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatMessage> chatMessageListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    //  ---------------------------------------------------
    // 0. String 전용 consumerFactory 생성
    @Bean
    public ConsumerFactory<String, String> stringConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "dlq-group"); // DLQ 용으로 그룹명 따로 주는 게 좋다.
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    // 1. dlq 설정 (실패 메시지를 dead-letter-topic으로 전송)
    @Bean
    public DeadLetterPublishingRecoverer recoverer(KafkaTemplate<String, String> kafkaTemplate) {
        return new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, ex) -> new TopicPartition("dead-letter-topic", record.partition()));
    }

    // 2. 에러 헨들러: 재시도 + recoverer 사용
    @Bean
    public DefaultErrorHandler errorHandler(DeadLetterPublishingRecoverer recoverer) {
        return new DefaultErrorHandler(recoverer, new FixedBackOff(2000L, 2)); // 2초 간격 2회 재시도
    }

    // 3. Kafka Listener Container 설정 (수동 ack + 에러 핸들러 주입)
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> dlqListenerContainerFactory(
            DefaultErrorHandler errorHandler) {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory());
        factory.setCommonErrorHandler(errorHandler); // 에러 발생시 동작
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}