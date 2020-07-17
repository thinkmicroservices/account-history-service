
package com.thinkmicroservices.ri.spring.account.history.repository;

import com.thinkmicroservices.ri.spring.account.history.messaging.event.PasswordChangedEvent;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author cwoodward
 */
public interface PasswordChangedRepository extends CrudRepository<PasswordChangedEvent,String>{
    
}
