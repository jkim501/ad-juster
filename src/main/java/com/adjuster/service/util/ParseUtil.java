package com.adjuster.service.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.adjuster.service.dto.CampaignDTO;
import com.adjuster.service.dto.CreativeDTO;
import com.adjuster.service.entity.Campaign;
import com.adjuster.service.entity.Creative;

public class ParseUtil {
	public static Set<Campaign> parseDataToEntities(Set<CampaignDTO> campaigns, Set<CreativeDTO> creatives) throws Exception {
		Map<Long, Campaign> idToCampaignEntity = new HashMap<>();
		Set<CreativeDTO> creativesToDelete = new HashSet<>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		for (CampaignDTO campaign : campaigns) {
			if (!idToCampaignEntity.containsKey(campaign.getId())) {
				
				idToCampaignEntity.put(campaign.getId(),
						new Campaign(campaign.getId(), new java.sql.Date(df.parse(campaign.getStartDate()).getTime()), campaign.getCpm(), campaign.getName()));
			}
			if(creatives != null) {
				for (CreativeDTO creative : creatives) {
					if (creative.getParentId() == campaign.getId()) {
						idToCampaignEntity.get(campaign.getId()).getCreatives()
								.add(new Creative(creative.getId(), creative.getClicks(), creative.getViews(), idToCampaignEntity.get(campaign.getId())));
						creativesToDelete.add(creative);
					}
				}
			}

			// delete added creatives so we don't go through the whole list again
			for (CreativeDTO creative : creativesToDelete) {
				creatives.remove(creative);
			}
		}
		
		return new TreeSet<Campaign>(idToCampaignEntity.values());
	}
}
