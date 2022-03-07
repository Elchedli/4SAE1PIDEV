package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.Ad;
import com.enums.PubRegion;
import com.enums.PubType;

@Repository
public interface AdRepository extends CrudRepository<Ad,Integer>{
	@Query("Select ad From Ad ad where ad.pubType = :regtype  and ad.pubRegion = :regpub and ad.country = :country")
	List<Ad> listerPubRegion(@Param("regtype") PubType regtype,@Param("regpub") PubRegion regpub,@Param("country") String country);
	@Query("Select ad From Ad ad where ad.pubType = :regtype")
	List<Ad> listerPubGlobal(@Param("regtype") PubType regtype);
	@Query(
			value = "select count(*) as 'data',country as 'pays',((SELECT COUNT(*) FROM ad WHERE country=pays)/(select count(*) from ad))*100 as 'percentage' from ad group by (country)",
			nativeQuery = true
	)
	List<Object[]> listerStats();
	@Query(
			value = "Select count(*) from ad",
			nativeQuery = true
	)
	int countAds();
}
