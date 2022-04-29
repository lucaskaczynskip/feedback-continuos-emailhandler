package br.com.vimserdev.feedback.emailconsumer.service;

import br.com.vimserdev.feedback.emailconsumer.dto.EmailHandlerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerService {

    private final ObjectMapper mapper;
    private final EmailService emailService;

    @KafkaListener(topics = "${cloudkarafka.topic}")
    public void processMessage(String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                               @Header(KafkaHeaders.OFFSET) List<Long> offsets) throws JsonProcessingException {
        log.info("offset -> '{}' -> Consumed message -> '{}'  ", offsets.get(0), message);

        EmailHandlerDTO emailHandlerDTO = mapper.readValue(message, EmailHandlerDTO.class);
        emailService.sendEmail(emailHandlerDTO);
    }
}
