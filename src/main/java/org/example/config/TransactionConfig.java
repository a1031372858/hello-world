package org.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author xuyachang
 * @date 2026/3/3
 */
@Configuration
public class TransactionConfig {

    @Bean
    public TransactionTemplate transactionTemplate(DataSource dataSource){
        //使用指定数据源的方式来控制事务
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        TransactionTemplate template = new TransactionTemplate(dataSourceTransactionManager);
        // 设置默认的事务传播行为
        template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 设置默认的事务隔离级别
        template.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 设置默认超时时间
        template.setTimeout(30);
        return template;
    }
}
