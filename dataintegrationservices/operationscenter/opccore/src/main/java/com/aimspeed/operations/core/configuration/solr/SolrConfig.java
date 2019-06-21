package com.aimspeed.operations.core.configuration.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.solr.SolrProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Solr配置类
 * @author AimSpeed
 */
@Configuration
@ConditionalOnClass({ HttpSolrClient.class, CloudSolrClient.class })
@ComponentScan("com.aimspeed.operation.repository.solr.*")
@EnableConfigurationProperties(SolrProperties.class)
public class SolrConfig {
	
	private final SolrProperties properties;

    private SolrClient solrClient;

    public SolrConfig(SolrProperties properties) {
        this.properties = properties;
    }

    @Bean	
    @ConditionalOnMissingBean
    public SolrClient solrClient() {
        this.solrClient = createSolrClient();
        return this.solrClient;
    }


    /**
     * 判断是否是集群，如果是集群则使用集群的地址
     * @author AimSpeed
     * @return SolrClient  
     */
    private SolrClient createSolrClient() {
        if (null != this.properties.getZkHost() && !"".equals(this.properties.getZkHost())) {
            return new CloudSolrClient(this.properties.getZkHost());
        }
        return new HttpSolrClient(this.properties.getHost());
    }

	
	
}
