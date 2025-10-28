package com.game.microbook.order.jobs;

import com.game.microbook.order.domain.OrderEventService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "Order Event Publishing Job")
class OrderEventsPublishingJob {

    private final OrderEventService orderEventService;

    @Scheduled(cron = "${orders.publishOrderEventsCronJob}")
    public void publishOrderEvents() {
        log.info("Order Event Publishing Job at {}", Instant.now());
        orderEventService.publishedOrderEvents();
    }
}
