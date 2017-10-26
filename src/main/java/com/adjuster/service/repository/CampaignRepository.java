package com.adjuster.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.adjuster.service.entity.Campaign;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
	String FIND_ALL_CAMPAIGN_WITH_CREATIVE_SUM = "SELECT cp, sum(cr.views) as total_views, sum(cr.clicks) as total_clicks " + 
			"FROM Campaign cp " + 
			//left joining because a campaign might not have creatives
			"LEFT JOIN cp.creatives cr " + 
			"GROUP BY cp.id";
	
	List<Campaign> findById(Long id);
	
	@Query(FIND_ALL_CAMPAIGN_WITH_CREATIVE_SUM)
	List<Object[]> findAllWithCreatives();
}
