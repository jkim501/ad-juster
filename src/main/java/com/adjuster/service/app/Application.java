package com.adjuster.service.app;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.adjuster.service.dto.CampaignDTO;
import com.adjuster.service.dto.CreativeDTO;
import com.adjuster.service.entity.Campaign;
import com.adjuster.service.repository.CampaignRepository;
import com.adjuster.service.rest.AdjusterRestService;
import com.adjuster.service.util.FileUtil;
import com.adjuster.service.util.ParseUtil;


@SpringBootApplication
@EntityScan("com.adjuster.service.entity")
@EnableJpaRepositories("com.adjuster.service.repository")
@ComponentScan("com.adjuster.service.rest")
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	AdjusterRestService adjusterRestService;
	
	public static void main(String args[]) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner run(CampaignRepository campaignRepo) {
		return (args) -> {
			try {
				problem1(campaignRepo);
				
				problem3(campaignRepo);
			} catch(Exception e) {
				log.error("Error in the system : " + e.getMessage());
			}
		};
	}

	private void problem1(CampaignRepository campaignRepo) throws Exception {
		Set<CampaignDTO> campaigns = adjusterRestService.getCampaigns();
		Set<CreativeDTO> creatives = adjusterRestService.getCreatives();
		
		Set<Campaign> campaignEntities = ParseUtil.parseDataToEntities(campaigns, creatives);
		
		for(Campaign campaign : campaignEntities) {
			campaignRepo.save(campaign);
		}
	}
	
	private void problem3(CampaignRepository campaignRepo) throws FileNotFoundException {
		List<Object[]> objects = campaignRepo.findAllWithCreatives();
		
		if(objects != null)
			FileUtil.createCSVFile(objects);
	}
}
