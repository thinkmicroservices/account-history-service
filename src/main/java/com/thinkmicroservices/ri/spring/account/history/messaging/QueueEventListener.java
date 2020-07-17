/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.history.messaging;

import com.thinkmicroservices.ri.spring.account.history.messaging.event.CredentialsAuthenticationRequestedEvent;
import com.thinkmicroservices.ri.spring.account.history.messaging.event.PasswordChangedEvent;
import com.thinkmicroservices.ri.spring.account.history.messaging.event.PasswordRecoveryRequestedEvent;
import com.thinkmicroservices.ri.spring.account.history.messaging.event.AccountRegisteredEvent;
import com.thinkmicroservices.ri.spring.account.history.messaging.event.PasswordRecoveryCompletedEvent;
import com.thinkmicroservices.ri.spring.account.history.messaging.event.ProfileCreatedEvent;
import com.thinkmicroservices.ri.spring.account.history.repository.AccountRegisteredRepository;
import com.thinkmicroservices.ri.spring.account.history.repository.CredentialAuthenticationRequestRepository;
import com.thinkmicroservices.ri.spring.account.history.repository.PasswordChangedRepository;
import com.thinkmicroservices.ri.spring.account.history.repository.ProfileCreatedRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import com.thinkmicroservices.ri.spring.account.history.repository.PasswordRecoveryRequestedRepository;
import com.thinkmicroservices.ri.spring.account.history.repository.PasswordRecoveryCompletedRepository;

/**
 *
 * @author cwoodward
 */
@EnableBinding(Sink.class)
@Slf4j
public class QueueEventListener {

    //private static final String CONDITION_UNMAPPED_EVENTS="(headers['type'] != 'ACCOUNT_REGISTERED_EVENT') && (headers['type'] != 'CREDENTIALS_AUTHENTICATION_REQUEST_EVENT') && (headers['type'] != 'PASSWORD_CHANGED_EVENT' && (headers['type'] != 'PASSWORD_RECOVERY_REQUESTED_EVENT' && (headers['type'] != 'PROFILE_CREATED_EVENT')";
    @Autowired
    private AccountRegisteredRepository accountRegisteredRepository;

    @Autowired
    private CredentialAuthenticationRequestRepository authenticationRepository;

    @Autowired
    private ProfileCreatedRepository profileCreatedRepository;

    @Autowired
    private PasswordChangedRepository passwordChangedRepository;
    
    @Autowired
    private PasswordRecoveryRequestedRepository passwordRecoveryRequestedRepository;
    
    @Autowired
    private PasswordRecoveryCompletedRepository passwordRecoveryCompletedRepository;
    
    @Autowired
    private MeterRegistry meterRegistry;

    private Counter authenticationEventCounter;
    private Counter registrationEventCounter;
    private Counter passwordChangeEventCounter;
    private Counter passwordRecoveryRequestedEventCounter;
    private Counter passwordRecoveryCompletedEventCounter;
    private Counter profileCreatedEventCounter;

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='ACCOUNT_REGISTERED_EVENT'")
    public void processAccountRegisteredEvent(AccountRegisteredEvent event) {
        log.info("account registration event=>: " + event);
        accountRegisteredRepository.save(event);
        registrationEventCounter.increment();

    }

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='CREDENTIALS_AUTHENTICATION_REQUEST_EVENT'")
    public void processCredentialsAuthenticatedEvent(CredentialsAuthenticationRequestedEvent event) {
        log.info("account authentication event=>: " + event);
        this.authenticationRepository.save(event);
        authenticationEventCounter.increment();
    }

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='PASSWORD_CHANGED_EVENT'")
    public void processPasswordChangedEvent(PasswordChangedEvent event) {
        log.info("event=>: " + event);
        this.passwordChangedRepository.save(event);
        passwordChangeEventCounter.increment();
    }

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='PASSWORD_RECOVERY_REQUESTED_EVENT'")
    public void processPasswordRecoveryRequestedEvent(PasswordRecoveryRequestedEvent event) {
        log.info("event=>: " + event);
        this.passwordRecoveryRequestedRepository.save(event);
        passwordRecoveryRequestedEventCounter.increment();

    }

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='PASSWORD_RECOVERY_COMPLETED_EVENT'")
    public void processPasswordRecoveryCompletedEvent(PasswordRecoveryCompletedEvent event) {
        log.info("event=>: " + event);
        this.passwordRecoveryCompletedRepository.save(event);
        passwordRecoveryCompletedEventCounter.increment();

    }

    @StreamListener(target = Sink.INPUT, condition = "headers['type']=='PROFILE_CREATED_EVENT'")
    public void processProfileCreatedEvent(ProfileCreatedEvent event) {
        log.info("event=>: " + event);
        this.profileCreatedRepository.save(event);
        profileCreatedEventCounter.increment();
    }

//    @StreamListener(target = Sink.INPUT, condition = CONDITION_UNMAPPED_EVENTS)
//    public void trapUnmappedEvent(Object event) {
//        logger.debug("Trapped unmapped account event=>: " + event);
//
//    }
    @PostConstruct
    private void initializeMetrics() {
        authenticationEventCounter = Counter.builder("account.history.authentication.event")
                .description("The number of account history authentication events.")
                .register(meterRegistry);
        registrationEventCounter = Counter.builder("account.history.registration.event")
                .description("The number of account history registration events.")
                .register(meterRegistry);
        passwordChangeEventCounter = Counter.builder("account.history.password.change.event")
                .description("The number of account history password change events.")
                .register(meterRegistry);
        passwordRecoveryRequestedEventCounter = Counter.builder("account.history.password.recovery.request.event")
                .description("The number of account history password recovery request events.")
                .register(meterRegistry);
        passwordRecoveryCompletedEventCounter = Counter.builder("account.history.password.recovery.completed.event")
                .description("The number of account history password recovery request completed events.")
                .register(meterRegistry);

        profileCreatedEventCounter = Counter.builder("account.history.profile.created.event")
                .description("The number of account history profile created events.")
                .register(meterRegistry);

    }
}
