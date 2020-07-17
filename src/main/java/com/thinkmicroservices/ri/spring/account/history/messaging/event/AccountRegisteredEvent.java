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
@TypeAlias("AccountRegistered")
@Data
public class AccountRegisteredEvent extends AccountEvent {

    private String firstName;
    private String middleName;
    private String lastName;
    
    public AccountRegisteredEvent(){
        
    }
    
    public AccountRegisteredEvent(String accountId,String email) {
        super(accountId,email);
    }
    public AccountRegisteredEvent(String accountId,String email,String firstName, String middleName, String lastName) {
        super(accountId,email);
        this.firstName=firstName;
        this.middleName=middleName;
        this.lastName=lastName;
    }
}
