package com.adjuster.service.app;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.adjuster.service.dto.CampaignDTO;
import com.adjuster.service.dto.CreativeDTO;
import com.adjuster.service.entity.Campaign;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@Autowired
	private Application application;
	
	@Test
	public void testCalculateProfit() {
		assertTrue(application.calculateProfit("$1.00", 1).equals("$0.01"));
	}
	
	@Test
	public void testCalculateProfitCpmNull() {
		assertTrue(application.calculateProfit(null, 1).equals("$0.00"));
	}
	
	@Test
	public void testCalculateProfitViewsNegative() {
		assertTrue(application.calculateProfit("$1.00", -100).equals("$0.00"));
	}

	@Test
	public void testParseWithNullCreatives() {
		Set<CampaignDTO> campaigns = new HashSet<>();
		CampaignDTO campaingDTO1 = new CampaignDTO();
		campaingDTO1.setCpm("$10.00");
		campaingDTO1.setId(1L);
		campaingDTO1.setName("adjuster1");
		campaingDTO1.setStartDate(new Date(System.currentTimeMillis()));
		
		CampaignDTO campaingDTO2 = new CampaignDTO();
		campaingDTO2.setCpm("$200.00");
		campaingDTO2.setId(2L);
		campaingDTO2.setName("adjuster2");
		campaingDTO2.setStartDate(new Date(System.currentTimeMillis() + 1000));
		
		campaigns.add(campaingDTO1);
		campaigns.add(campaingDTO2);
		
		Set<Campaign> parseDataToEntities = application.parseDataToEntities(campaigns, null);
		
		assertTrue(parseDataToEntities.iterator().next().getCreatives().size() == 0);
	}
	
	@Test
	public void testParseWithCreatives() {
		Set<CampaignDTO> campaigns = new HashSet<>();
		CampaignDTO campaingDTO1 = new CampaignDTO();
		campaingDTO1.setCpm("$10.00");
		campaingDTO1.setId(1L);
		campaingDTO1.setName("adjuster1");
		campaingDTO1.setStartDate(new Date(System.currentTimeMillis()));
		
		CampaignDTO campaingDTO2 = new CampaignDTO();
		campaingDTO2.setCpm("$200.00");
		campaingDTO2.setId(2L);
		campaingDTO2.setName("adjuster2");
		campaingDTO2.setStartDate(new Date(System.currentTimeMillis() + 1000));
		
		campaigns.add(campaingDTO1);
		campaigns.add(campaingDTO2);
		
		Set<CreativeDTO> creatives = new HashSet<>();
		CreativeDTO creativeDTO1 = new CreativeDTO();
		creativeDTO1.setClicks(10);
		creativeDTO1.setId(10L);
		creativeDTO1.setParentId(1L);
		creativeDTO1.setViews(20);
		
		CreativeDTO creativeDTO2 = new CreativeDTO();
		creativeDTO2.setClicks(15);
		creativeDTO2.setId(11L);
		creativeDTO2.setParentId(1L);
		creativeDTO2.setViews(30);

		creatives.add(creativeDTO1);
		creatives.add(creativeDTO2);
		
		Set<Campaign> cms = application.parseDataToEntities(campaigns, creatives);
		
		for(Campaign campaign : cms) {
			if(campaign.getId() == 1L) {
				assertTrue(campaign.getCreatives().size() == 2);
			}
			else {
				assertTrue(campaign.getCreatives().size() == 0);
			}
		}
	}
}
