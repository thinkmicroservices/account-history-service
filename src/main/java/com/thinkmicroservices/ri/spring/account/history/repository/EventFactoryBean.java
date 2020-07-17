/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.history.repository;

import java.io.Serializable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 *
 * @author cwoodward
 */
public class EventFactoryBean <T extends Repository<S, ID>, S, ID extends Serializable> extends
        MongoRepositoryFactoryBean<T, S, ID> {
    
    
    
    public EventFactoryBean(Class<? extends T> repositoryInterface){
        super(repositoryInterface);
    }
    
     @Override
    protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
        return new EventRepositoryFactory(operations);
    }
}
