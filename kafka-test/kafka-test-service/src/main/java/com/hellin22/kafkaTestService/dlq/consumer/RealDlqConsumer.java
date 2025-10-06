package com.hellin22.kafkaTestService.dlq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class RealDlqConsumer { // dlq topic을 소비하는 consumer

    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(
            topics = "dead-letter-topic",
            groupId = "dlq-consumer-group",
            containerFactory = "dlqListenerContainerFactory"
    )
    public void consumeFromDlq(
            ConsumerRecord<String, String> record,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            @Header(KafkaHeaders.ORIGINAL_TOPIC) Optional<String> originalTopicOpt
    ) {
        try {
            Headers headers = record.headers();

            for (org.apache.kafka.common.header.Header header : headers) {
                log.info("Kafka Header - key: {}, value: {}", header.key(), new String(header.value()));
            }
            org.apache.kafka.common.header.Header originTopicHeader = headers.lastHeader(KafkaHeaders.ORIGINAL_TOPIC);

            // ex) 실패한 메시지에 대해 별도 DB 저장, 슬랙 알림, 다른 토픽으로 재발행 등
            String originalTopic = originTopicHeader != null
                    ? new String(originTopicHeader.value())
                    : "unknown-origin-topic";

//            String originalTopic = originalTopicOpt.orElse("unknown-origin-topic");
            log.warn("[DLQ] 메시지 수신 - topic: {}, partition: {}, offset: {}, value: {}",
                    topic, partition, offset, record.value());

            // 1. 원래 실패한 토픽으로 재전송
            kafkaTemplate.send(originalTopic, record.key(), "dlq retry message value");
            log.info("[DLQ] 원래 토픽으로 재전송 완료 - {}", originalTopic);

            // 2. 커밋
            ack.acknowledge();

        } catch (Exception e) {
            log.error("[DLQ] 메시지 처리 실패 - value: {}, error: {}", record.value(), e.getMessage(), e);
            // 여기서 예외를 던지면 계속 재시도하거나 다시 DLQ로 들어갈 수도 있음.
            // 다시 dlq로 돌아오는건 무한루프 -> 다른 예외처리로 추가
        }
    }
}
