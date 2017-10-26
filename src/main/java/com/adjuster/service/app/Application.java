package com.adjuster.service.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import com.adjuster.service.json.Campaign;
import com.adjuster.service.json.Creative;
import com.adjuster.service.repository.CampaignRepository;

@SpringBootApplication
@EntityScan("com.adjuster.service.entity")
@EnableJpaRepositories("com.adjuster.service.repository")
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
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
		Set<Campaign> campaigns = getCampaigns();
		Set<Creative> creatives = getCreatives();
		
		Set<com.adjuster.service.entity.Campaign> campaignEntities = parseDataToEntities(campaigns, creatives);
		
		for(com.adjuster.service.entity.Campaign campaign : campaignEntities) {
			campaignRepo.save(campaign);
		}
	}
	
	private void problem3(CampaignRepository campaignRepo) throws FileNotFoundException {
		//I'm letting the database do most of the heavy lifting by grabbing exactly the data we need
		//so I don't have to programmatically parse and massage the results after
		List<Object[]> objects = campaignRepo.findAllWithCreatives();
		
		if(objects != null)
			createCSVFile(objects);
	}
	
	private Set<Campaign> getCampaigns() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		
		Campaign[] campaigns = restTemplate.getForObject("http://homework.ad-juster.com/api/campaign", Campaign[].class);

		if (campaigns != null) {
			return new HashSet<Campaign>(Arrays.asList(campaigns));
		}

		throw new Exception("There were no campaigns to return");
	}

	private Set<Creative> getCreatives() {
		RestTemplate restTemplate = new RestTemplate();

		Creative[] creatives = restTemplate.getForObject("http://homework.ad-juster.com/api/creative", Creative[].class);

		if (creatives != null) {
			return new HashSet<Creative>(Arrays.asList(creatives));
		}

		//no exception here campaigns doesn't necessarily have to have creatives unless it's a business requirement?
		return null;
	}

	private Set<com.adjuster.service.entity.Campaign> parseDataToEntities(Set<Campaign> campaigns, Set<Creative> creatives) {
		Map<Long, com.adjuster.service.entity.Campaign> idToCampaignEntity = new HashMap<>();
		Set<Creative> creativesToDelete = new HashSet<>();

		for (Campaign campaign : campaigns) {
			if (!idToCampaignEntity.containsKey(campaign.getId())) {
				idToCampaignEntity.put(campaign.getId(),
						new com.adjuster.service.entity.Campaign(campaign.getId(), campaign.getStartDate(), campaign.getCpm(), campaign.getName()));
			}
			if(creatives != null) {
				for (Creative creative : creatives) {
					if (creative.getParentId() == campaign.getId()) {
						idToCampaignEntity.get(campaign.getId()).getCreatives()
								.add(new com.adjuster.service.entity.Creative(creative.getId(), creative.getClicks(), creative.getViews(), idToCampaignEntity.get(campaign.getId())));
						creativesToDelete.add(creative);
					}
				}
			}

			// delete added creatives so we don't go through the whole list again
			for (Creative creative : creativesToDelete) {
				creatives.remove(creative);
			}

		}
		
		return new TreeSet<com.adjuster.service.entity.Campaign>(idToCampaignEntity.values());
	}

	private void createCSVFile(List<Object[]> objects) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File("campaign.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("id");
		sb.append(',');
		sb.append("name");
		sb.append(',');
		sb.append("cpm");
		sb.append(',');
		sb.append("startDate");
		sb.append(',');
		sb.append("views");
		sb.append(',');
		sb.append("clicks");
		sb.append(',');
		sb.append("profit");
		sb.append('\n');

		
		com.adjuster.service.entity.Campaign campaign = null;
		
		for (Object[] object : objects) {
			campaign = (com.adjuster.service.entity.Campaign) object[0];
			sb.append(campaign.getId());
			sb.append(',');
			sb.append(campaign.getName());
			sb.append(',');
			sb.append(campaign.getCpm());
			sb.append(',');
			sb.append(campaign.getStartDate());
			sb.append(',');
			sb.append(object[1] != null ? object[1] : 0);
			sb.append(',');
			sb.append(object[2] != null ? object[1] : 0);
			sb.append(',');
			sb.append(calculateProfit(campaign.getCpm(), object[1] != null ? (long) object[1] : 0));
			sb.append('\n');
		}

		pw.write(sb.toString());
		pw.close();
	}
	
	private String calculateProfit(String cpm, long views) {
		DecimalFormat df = new DecimalFormat();
		df.setRoundingMode(RoundingMode.CEILING);	//rounds up the cents
		df.setMaximumFractionDigits(2);				//only 2 decimal places
		
		Double cpmLong = Double.parseDouble(cpm.substring(1));
		
		StringBuilder sb = new StringBuilder();
		sb.append("$");
		sb.append("" + df.format(cpmLong * views / 1000));
		
		return sb.toString();
	}
}
