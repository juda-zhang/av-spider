package juda.zhang.studio.taohuazuspider.core.dal;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by Juda.Zhang on 2017/6/9.
 */
@Configuration
@MapperScan(basePackages = "juda.zhang.studio.taohuazuspider.core.dal.mapper")
@EnableTransactionManagement
public class PersistenceConfig {
    @Value(value = "classpath:mybatis/sqlmap/*.xml")
    private Resource[] mapperLocations;

    @Value(value = "classpath:mybatis/mybatis-config.xml")
    private Resource configLocation;

    @Bean(autowire = Autowire.BY_NAME)
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setMapperLocations(mapperLocations);
        ssfb.setConfigLocation(configLocation);
        ssfb.setTypeAliasesPackage("juda.zhang.studio.taohuazuspider.core.model");
        return ssfb;
    }

    @Bean(autowire = Autowire.BY_NAME)
    public TransactionTemplate transactionTemplate() {
        return new TransactionTemplate();
    }

    @Bean(autowire = Autowire.BY_NAME)
    public TransactionTemplate transactionTemplateNew() {
        TransactionTemplate template = new TransactionTemplate();
        template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return template;
    }
}
