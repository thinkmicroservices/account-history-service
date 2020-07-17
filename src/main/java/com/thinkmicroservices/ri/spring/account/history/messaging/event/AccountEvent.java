/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.history.messaging.event;

import java.time.ZonedDateTime;
 
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author cwoodward
 */
@Document(collection = "account_history_events")
@Data
public abstract class AccountEvent {

    @Id
    protected String id;
    protected String eventType;
   
    protected String accountId;
    protected String email;
    
    protected ZonedDateTime            timestamp;

    public AccountEvent() {
        this(null,null);
    }

    public AccountEvent(String accountId,String email) {
        this(accountId,email, ZonedDateTime.now());
    }

    public AccountEvent(String accountId,String email, ZonedDateTime timestamp) {
        this.accountId=accountId;
        this.email = email;
        this.timestamp = timestamp;
        this.eventType=this.getClass().getSimpleName();
    }

}
