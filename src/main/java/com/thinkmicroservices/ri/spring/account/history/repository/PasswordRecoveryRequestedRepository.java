 
package com.thinkmicroservices.ri.spring.account.history.repository;

import com.thinkmicroservices.ri.spring.account.history.messaging.event.PasswordRecoveryRequestedEvent;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author cwoodward
 */
public interface PasswordRecoveryRequestedRepository extends CrudRepository<PasswordRecoveryRequestedEvent,String>{
    
}
