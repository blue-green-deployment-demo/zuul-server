package com.ewolff.microservice.zuulserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.netflix.appinfo.AmazonInfo;

@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {

	@Value("${server.port}") 
	private int port;
	
	@Value("${spring.application.name}")
	private String name;
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ZuulApplication.class).web(true).run(args);
	}

	@Bean
	@Profile(value="aws")
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils) {
	    EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
	    AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild(name);
	    config.setDataCenterInfo(info);
	    info.getMetadata()
	            .put(AmazonInfo.MetaDataKey.publicHostname.getName(), info.get(AmazonInfo.MetaDataKey.publicHostname));
	    config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
	    config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
	    config.setNonSecurePort(port);
	    return config;
	}
		
}
