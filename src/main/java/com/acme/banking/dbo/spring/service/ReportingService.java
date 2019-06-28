package com.acme.banking.dbo.spring.service;

import com.acme.banking.dbo.spring.dao.AccountRepository;
import com.acme.banking.dbo.spring.domain.Account;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component //this will create a bean with default name reportingService , or you can specify it as @Component('name')
@Service //Pseudonym over Component, for more clarity. There is also @repository and @controller
@Scope("singleton") //Same as in Spring, max clarity. Not really required for Singleton, as it's default
@Lazy(false) //Again, same as in Spring. Again, defaulted
@PropertySource("classpath:application.properties") //Basically makes properties object and makes @Value annotation
// below to essentially be properties.getProperty(marker)
public class ReportingService {
    /** Basically "getBean(Logger.class) and assign it to this field". If there are 2+ such classes,
     * the one with @Primary is chosen. Or you can use @Qualifier field annotation. But those both are anti-patterns.
     * The only way it can be justified is when beans are oly data-different */
    @Autowired private Logger logger;

    /** TODO Field VS constructor VS setter injection*/
    @Value("${marker}") private String layoutMarker; //TODO SpEL
    /** Absolutely the same as @Autowired, but supported by Java instead of Spring. There is also @Inject. Note, that you will STILL need to use Spring for context init */
    @Resource private AccountRepository accounts;

    public void setLayoutMarker(String layoutMarker) {
        this.layoutMarker = layoutMarker;
    }
/** this allows to create RepService instance without Spring. Constructor is used OVER field annotations,
 *  so basically we don't need @Resource on accounts*/
    @Autowired
    public ReportingService(AccountRepository accounts) {
        this.accounts = accounts;
    }

    @PostConstruct //This is not Spring one, but here java won and there's one commonly used annotation only
    public void onCreate() {
        logger.info("ReportingService created");
    }

    @PreDestroy
    public void onShutDown() {
        logger.info("ReportingService shut down");
    }

    public String accountReport(long id) {
        Account account = accounts.findById(id).orElseThrow(() -> new IllegalStateException("Account not found"));
        return layoutMarker + layoutMarker + " " + account.toString();
    }
}
