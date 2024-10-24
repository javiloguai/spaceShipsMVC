package com.w2m.spaceShips.application.events.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2m.spaceShips.application.events.WormHoleJumpQueueListener;
import com.w2m.spaceShips.application.model.events.WormHoleJumpEvent;
import com.w2m.spaceShips.domain.model.SpaceShipDomain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author javiloguai
 */
@Service
public class WormHoleJumpEventQueueListenerImpl implements WormHoleJumpQueueListener {

    private static final Logger LOGGER = LogManager.getLogger(WormHoleJumpEventQueueListenerImpl.class);

    private static final String TOPIC = "wormhole-topic";

    private static final String GROUP = "spaceships-group";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WormHoleJumpEventQueueListenerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void produce(SpaceShipDomain spaceShip) throws JsonProcessingException {
        final WormHoleJumpEvent event = new WormHoleJumpEvent(spaceShip.getId(),
                spaceShip.getName());

        final String eventPayload = new ObjectMapper().writeValueAsString(event);
        kafkaTemplate.send(TOPIC, eventPayload);


    }

    @Override
    @KafkaListener(topics = TOPIC, groupId = GROUP)
    public void consume(String eventMessage) throws JsonProcessingException {

        WormHoleJumpEvent e = new ObjectMapper().readValue(eventMessage, WormHoleJumpEvent.class);
        LOGGER.info("Passing Black Hole Event Horizon. There's no turning back");
        LOGGER.info("Entering in wormhole");
        LOGGER.info(String.format("Space Ship Name: %s. and Id: %s. Is travelling throughout a wormhole. It will appear in another time/space anywhere in the Universe",
                e.name()), e.id());
        LOGGER.info("Expulsed out from WhiteHole");

    }

}
