/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.history.controller;

import com.thinkmicroservices.ri.spring.account.history.model.PagedEventHistoryRequest;
import com.thinkmicroservices.ri.spring.account.history.model.PagedEventHistoryResponse;
import com.thinkmicroservices.ri.spring.account.history.service.AccountHistoryService;
import com.thinkmicroservices.ri.spring.account.history.service.JWT;
import com.thinkmicroservices.ri.spring.account.history.service.JWTService;
import com.thinkmicroservices.ri.spring.account.history.service.UtilityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cwoodward
 */
@Api(value = "Account History Management System", description = "Operations pertaining to account history")
@RestController
//@CrossOrigin
@Slf4j
public class AccountHistoryController {

    @Autowired
    private AccountHistoryService accountHistoryService;
    @Autowired
    private UtilityService utilityService;

    @Autowired
    private JWTService jwtService;

    protected static final String DEFAULT_EVENT_HISTORY_SORT_BY = "timestamp";
    protected static final int DEFAULT_EVENT_HISTORY_PAGE_SIZE = 10;

    @GetMapping(path = "/all")
    @ResponseBody
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<?> getPagedHistory(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy, HttpServletRequest httpServletRequest) {
        log.info("get history request");
        JWT jwt = jwtService.getJWT(httpServletRequest);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if ((jwt.hasRole(JWT.ROLE_USER)) || (jwt.hasRole(JWT.ROLE_ADMIN))) {
            return ResponseEntity.ok(accountHistoryService.findAll(pageNo, pageSize, sortBy));

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
    
     @GetMapping(path = "/eventTypes")
    @ResponseBody
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<?> getEventTypes( HttpServletRequest httpServletRequest) {
        log.info("get history request");
        JWT jwt = jwtService.getJWT(httpServletRequest);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if ((jwt.hasRole(JWT.ROLE_USER)) || (jwt.hasRole(JWT.ROLE_ADMIN))) {
            return ResponseEntity.ok(utilityService.getEventTypes());

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
    

    @PostMapping(path = "/find")
    @ResponseBody
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<PagedEventHistoryResponse> find(@RequestBody PagedEventHistoryRequest request, HttpServletRequest httpServletRequest) {

        log.info("find {} ", request);
        request = setDefaults(request);
        log.info("find with defaults applied {} ", request);
        JWT jwt = jwtService.getJWT(httpServletRequest);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if ((jwt.hasRole(JWT.ROLE_USER)) || (jwt.hasRole(JWT.ROLE_ADMIN))) {
            return ResponseEntity.ok(accountHistoryService.findByTimestampBetweenAndEventTypeAndAccountIdAndEventType(request.getPage(),
                    request.getPageSize(),
                    request.getSortBy(), request.getStartDate(), request.getEndDate(),
                    request.getEventType(),
                    request.getAccountIds()
            ));

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    private PagedEventHistoryRequest setDefaults(PagedEventHistoryRequest request) {
        // set a default start date if not present
        if (request.getStartDate() == null) {
            log.debug("applying default start date");
            request.setStartDate(ZonedDateTime.ofInstant((new Date(0)).toInstant(), ZoneId.systemDefault())); // set the start to the epoch date
        }
        // set a default end date if not present
        if (request.getEndDate() == null) {
            log.debug("applying default end date");
            request.setEndDate(ZonedDateTime.now()); // set the end to the current date
        }
        // set a default page size if value is 0
        if (request.getPageSize() == 0) {
            log.debug("applying default page size");
            request.setPageSize(DEFAULT_EVENT_HISTORY_PAGE_SIZE);
        }
        // set a default sort value
        if ((request.getSortBy() == null) || (request.getSortBy().length() == 0)) {
            log.debug("applying default sortBy");
            request.setSortBy(DEFAULT_EVENT_HISTORY_SORT_BY);
        }

        // set default list of eventTypes
        if ((request.getEventType() == null) || (request.getEventType().size() == 0)) {
            log.debug("applying default event types");
            request.setEventType(utilityService.getEventTypes());
        }

        return request;
    }

}
