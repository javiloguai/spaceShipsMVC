package com.w2m.spaceShips.application.events;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.w2m.spaceShips.application.model.events.WormHoleJumpEvent;
import com.w2m.spaceShips.domain.model.SpaceShipDomain;

/**
 * @author javiloguai
 * <p>
 * Interface methods for Kafka Messaging Queues
 */
public interface WormHoleJumpQueueListener {

    /**
     * Produce a {@link WormHoleJumpEvent} for a Spaceship
     *
     * @param spaceship spaceship
     */
    void produce(SpaceShipDomain spaceship) throws JsonProcessingException;

    /**
     * Consume events in queue
     *
     * @param eventMessage then message event
     */
    void consume(String eventMessage) throws JsonProcessingException;
}
