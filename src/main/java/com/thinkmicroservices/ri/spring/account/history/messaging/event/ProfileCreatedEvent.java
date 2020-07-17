/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.history.messaging.event;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author cwoodward
 */
@Document(collection = "account_history_events")
@TypeAlias("ProfileCreated")
@Data
public class ProfileCreatedEvent extends AccountEvent {

   public ProfileCreatedEvent(){
       super();
   }

    public ProfileCreatedEvent(String accountId,String email) {
        super(accountId,email);

    }
}
