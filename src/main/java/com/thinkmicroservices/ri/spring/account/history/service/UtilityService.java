/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.history.service;

import com.mongodb.client.DistinctIterable;
import com.thinkmicroservices.ri.spring.account.history.messaging.event.AccountEvent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author cwoodward
 */
@Service
@Slf4j
public class UtilityService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.database}")
    private String databaseCollection;

    public List<String> getEventTypes() {
        log.debug("get distinct event types from collection{}", databaseCollection);
        log.debug("mongo template:{}", mongoTemplate);
        Criteria criteria = new Criteria();
        criteria.where("eventType").ne(null);
        Query query = new Query();

        query.addCriteria(criteria);

        // DistinctIterable<String> eventTypeIterable = mongoTemplate.getCollection("account-history-events").distinct("eventType", String.class);
        DistinctIterable<String> eventTypeIterable = mongoTemplate.getCollection("account_history_events").distinct("eventType", String.class);
        List<String> eventTypeList = StreamSupport.stream(eventTypeIterable.spliterator(), false)
                //.map( event->event.getEventType())
                .collect(Collectors.toList());

        log.debug("event types={}", eventTypeList);
        return eventTypeList;

    }
}
