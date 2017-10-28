package com.adjuster.service.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.adjuster.service.dto.CampaignDTO;
import com.adjuster.service.dto.CreativeDTO;

@Component
public class AdjusterRestService {
	private static final String GET_CAMPAIGN = "http://homework.ad-juster.com/api/campaign";
	private static final String GET_CREATIVE = "http://homework.ad-juster.com/api/creative";
	
	public Set<CampaignDTO> getCampaigns() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		
		CampaignDTO[] campaigns = restTemplate.getForObject(GET_CAMPAIGN, CampaignDTO[].class);

		if (campaigns != null) {
			return new HashSet<CampaignDTO>(Arrays.asList(campaigns));
		}

		throw new Exception("There were no campaigns to return");
	}

	public Set<CreativeDTO> getCreatives() {
		RestTemplate restTemplate = new RestTemplate();

		CreativeDTO[] creatives = restTemplate.getForObject(GET_CREATIVE, CreativeDTO[].class);

		if (creatives != null) {
			return new HashSet<CreativeDTO>(Arrays.asList(creatives));
		}

		//no exception here campaigns doesn't necessarily have to have creatives unless it's a business requirement?
		return null;
	}
}
