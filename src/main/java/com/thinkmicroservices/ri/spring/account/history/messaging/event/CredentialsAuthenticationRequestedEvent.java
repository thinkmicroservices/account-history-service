/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.history.messaging.event;

import java.time.ZonedDateTime;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author cwoodward
 */
@Document(collection = "account_history_events")
@Data
@TypeAlias("CredentialsAuthenticationRequested")
public class CredentialsAuthenticationRequestedEvent extends AccountEvent {

    
    private boolean authenticated = false;
 

    public CredentialsAuthenticationRequestedEvent(String accountId,String email, ZonedDateTime timestamp, boolean authenticated) {
        super(accountId,email, timestamp);
       
        this.authenticated = authenticated;
    }

}
