package com.adjuster.service.util;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.adjuster.service.dto.CampaignDTO;
import com.adjuster.service.dto.CreativeDTO;
import com.adjuster.service.entity.Campaign;

@RunWith(SpringRunner.class)
public class ParseUtilTest {
	@Test
	public void testParseWithNullCreatives() throws Exception {
		Set<CampaignDTO> campaigns = new HashSet<>();
		CampaignDTO campaingDTO1 = new CampaignDTO();
		campaingDTO1.setCpm("$10.00");
		campaingDTO1.setId(1L);
		campaingDTO1.setName("adjuster1");
		campaingDTO1.setStartDate("2016-03-01");
		
		CampaignDTO campaingDTO2 = new CampaignDTO();
		campaingDTO2.setCpm("$200.00");
		campaingDTO2.setId(2L);
		campaingDTO2.setName("adjuster2");
		campaingDTO2.setStartDate("2016-03-01");
		
		campaigns.add(campaingDTO1);
		campaigns.add(campaingDTO2);
		
		Set<Campaign> parseDataToEntities = ParseUtil.parseDataToEntities(campaigns, null);
		
		assertTrue(parseDataToEntities.iterator().next().getCreatives().size() == 0);
	}
	
	@Test
	public void testParseWithCreatives() throws Exception {
		Set<CampaignDTO> campaigns = new HashSet<>();
		CampaignDTO campaingDTO1 = new CampaignDTO();
		campaingDTO1.setCpm("$10.00");
		campaingDTO1.setId(1L);
		campaingDTO1.setName("adjuster1");
		campaingDTO1.setStartDate("2016-03-01");
		
		CampaignDTO campaingDTO2 = new CampaignDTO();
		campaingDTO2.setCpm("$200.00");
		campaingDTO2.setId(2L);
		campaingDTO2.setName("adjuster2");
		campaingDTO2.setStartDate("2016-03-01");
		
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
		
		Set<Campaign> cms = ParseUtil.parseDataToEntities(campaigns, creatives);
		
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
