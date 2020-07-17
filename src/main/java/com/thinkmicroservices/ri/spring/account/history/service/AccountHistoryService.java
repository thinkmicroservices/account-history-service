 
package com.thinkmicroservices.ri.spring.account.history.service;

import com.thinkmicroservices.ri.spring.account.history.messaging.event.AccountEvent;
import com.thinkmicroservices.ri.spring.account.history.model.PagedEventHistoryResponse;
import com.thinkmicroservices.ri.spring.account.history.repository.EventRepository;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author cwoodward
 */
@Service
@Slf4j
public class AccountHistoryService {

    @Autowired
    private EventRepository eventRepository;

 
    
   // @Autowired
   // private UtilityService utilityService;

   
    public PagedEventHistoryResponse<AccountEvent> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AccountEvent> pagedResult = eventRepository.findAll(paging);
        log.debug("results {}", pagedResult);
        PagedEventHistoryResponse<AccountEvent> pagedResultResponse = createPagedResultResponse(pagedResult);

        return pagedResultResponse;
    }

    public PagedEventHistoryResponse<AccountEvent> findByTimestampBetweenAndEventTypeAndAccountIdAndEventType(Integer pageNo,
            Integer pageSize,
            String sortBy,
            ZonedDateTime start,
            ZonedDateTime end,
            List<String> eventTypes,
            List<String> accountIds) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        // Page<AccountEvent> pagedResult = eventRepository.findByTimestampBetweenAndEventTypeInAndAccountIdIn(paging, start, end, eventTypes, accountIds);
        Page<AccountEvent> pagedResult = null;
        if ((accountIds == null) || (accountIds.size() == 0)) {
            log.debug("No account ids---> using findByEventTYpeInAndTimestampBetween");
            pagedResult = eventRepository.findByEventTypeInAndTimestampBetween(paging, eventTypes,  start, end);
        } else {
            log.debug("account ids present---> using findByEventTYpeInAndTimestampBetween");
            pagedResult = eventRepository.findByEventTypeInAndAccountIdInAndTimestampBetween(paging, eventTypes, accountIds, start, end);
        }
        log.debug("results {}", pagedResult);
        PagedEventHistoryResponse<AccountEvent> pagedResultResponse = createPagedResultResponse(pagedResult);

        return pagedResultResponse;
    }

    public PagedEventHistoryResponse<AccountEvent> createPagedResultResponse(Page<AccountEvent> pagedResult) {

        PagedEventHistoryResponse<AccountEvent> pagedResultResponse = new PagedEventHistoryResponse<AccountEvent>();
        if (pagedResult.hasContent()) {
            pagedResultResponse.setResultsAvailable(true);
            pagedResultResponse.setResultList(pagedResult.getContent());
            pagedResultResponse.setTotalPages(pagedResult.getTotalPages());
            pagedResultResponse.setTotalElements(pagedResult.getTotalElements());
            pagedResultResponse.setCurrentPage(pagedResult.getNumber());
            pagedResultResponse.setCurrentPageElementNumber(pagedResult.getNumberOfElements());

        } else {
            pagedResultResponse.setResultList(new ArrayList<AccountEvent>());

        }

        return pagedResultResponse;
    }

}
