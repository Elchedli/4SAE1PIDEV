package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.Newsletter;
import com.enums.ArticleRegion;

@Repository
public interface NewsletterRepository extends CrudRepository<Newsletter,Integer>{
	@Query("Select news From Newsletter news where news.articleRegion = :type")
	List<Newsletter> listerNewsInt(@Param("type") ArticleRegion type);
	@Query("Select news From Newsletter news where news.articleRegion = :type AND news.country = :countries")
	List<Newsletter> listerNewsCountry(@Param("type") ArticleRegion type,@Param("countries") String country);
	@Query("Select news From Newsletter news where news.articleRegion = :type AND news.country = :countries AND news.city = :city")
	List<Newsletter> listerNewsCity(@Param("type") ArticleRegion type,@Param("countries") String country,@Param("city") String city);
	@Query("Select news From Newsletter news ORDER BY news.vues DESC")
	List<Newsletter> listerNewsViews();
}
