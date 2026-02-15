package org.example.config;


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author xuyachang
 * @date 2026/2/15
 */
@Configuration
@MapperScan(basePackages = "org.example.mapper.test", sqlSessionFactoryRef = "sqlSessionFactory2")
public class Mybatis2Config {

    //首先配置数据库连接信息
    @Bean(name = "ds2")
    @ConfigurationProperties(prefix = "spring.datasource.ds2")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }


    //然后配置数据库连接工厂，每个数据库对应一个工厂
    @Bean(name = "sqlSessionFactory2")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("ds2") DataSource dataSource, MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        //因为是mybatisPlus，所以用MybatisSqlSessionFactoryBean
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //配置插件，不然分页插件无效
        bean.setPlugins(mybatisPlusInterceptor);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:*/mapper/test/*.xml"));
        return bean.getObject();
    }
}
