package com.alg.brewer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
@PropertySource(value = { "file:\\${USERPROFILE}\\.brewer-s3.properties" }, ignoreResourceNotFound = true)
public class S3Config {

	@Autowired
	private Environment env;
	
	@Bean
	public AmazonS3 amazonS3() {
		String secretKey = env.getProperty("AWS_ACCESS_KEY_ID");
		String accessKey = env.getProperty("AWS_SECRET_ACCESS_KEY");
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		
		AmazonS3 amazonS3 = new AmazonS3Client(credentials, new ClientConfiguration());
		Region region = Region.getRegion(Regions.US_EAST_1);
		amazonS3.setRegion(region);
		
		return amazonS3;
		
	}
	
}
