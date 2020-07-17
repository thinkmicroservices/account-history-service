/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.history.repository;

import com.thinkmicroservices.ri.spring.account.history.messaging.event.ProfileCreatedEvent;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author cwoodward
 */
public interface ProfileCreatedRepository extends CrudRepository<ProfileCreatedEvent, String>{
    
}
