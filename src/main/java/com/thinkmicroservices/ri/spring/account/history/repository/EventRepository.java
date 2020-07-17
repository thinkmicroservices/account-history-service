/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.history.repository;

import com.thinkmicroservices.ri.spring.account.history.messaging.event.AccountEvent;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
 
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author cwoodward
 */
public interface EventRepository extends CrudRepository<AccountEvent, String> {
    
    //Hypermedia support for Pageables
    // https://docs.spring.io/spring-data/data-mongo/docs/1.5.0.RELEASE/reference/html/repositories.html

    public Page<AccountEvent> findAll(Pageable paging);
    
    
    
    // public Page<AccountEvent> findByTimestampBetweenAndEventTypeInAndAccountIdIn(Pageable paging,  ZonedDateTime startDate, ZonedDateTime endDate, List<String> eventTypes,List<String> accountIds);
    
    public Page<AccountEvent> findByEventTypeInAndAccountIdInAndTimestampBetween(Pageable paging,   List<String> eventTypes,List<String> accountIds,ZonedDateTime startDate, ZonedDateTime endDate);
    
    // find using query: { "eventType" : { "$in" : ["AccountRegisteredEvent", "ProfileCreatedEvent", "CredentialsAuthenticationRequestedEvent"] }, "accountId" : { "$in" : ["77b53698-721c-4004-b6ab-d55dd7b4f837"] }, "timestamp" : { "$gt" : { "$date" : 0 }, "$lt" : { "$date" : 1577577570724 } } } fields: Document{{}}
    
    public Page<AccountEvent> findByEventTypeInAndTimestampBetween(Pageable paging, List<String> eventTypes,ZonedDateTime startDate, ZonedDateTime endDate);
    
    
}
