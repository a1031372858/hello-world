package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author xuyachang
 * @date 2025/12/2
 */
public class TransactionConfig {


    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionTemplate transactionTemplate() {
        TransactionTemplate template = new TransactionTemplate();
        template.setTransactionManager(transactionManager);
        // 设置默认的事务传播行为
        template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 设置默认的事务隔离级别
        template.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 设置默认超时时间
        template.setTimeout(30);
        return template;
    }
}
