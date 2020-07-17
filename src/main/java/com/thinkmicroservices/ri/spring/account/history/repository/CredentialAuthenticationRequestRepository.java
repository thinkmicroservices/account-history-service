
package com.thinkmicroservices.ri.spring.account.history.repository;

import com.thinkmicroservices.ri.spring.account.history.messaging.event.CredentialsAuthenticationRequestedEvent;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author cwoodward
 */
public interface CredentialAuthenticationRequestRepository extends CrudRepository<CredentialsAuthenticationRequestedEvent,String>{
    
}
