 
package com.thinkmicroservices.ri.spring.account.history.repository;

import com.thinkmicroservices.ri.spring.account.history.messaging.event.PasswordRecoveryCompletedEvent;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author cwoodward
 */
public interface PasswordRecoveryCompletedRepository extends
        CrudRepository<PasswordRecoveryCompletedEvent,String> {
    
}
