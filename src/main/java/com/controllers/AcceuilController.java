package com.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Ad;
import com.entities.Newsletter;
import com.entities.Partner;
import com.entities.Recommandation;
import com.entities.enums.ArticleRegion;
import com.entities.enums.PubRegion;
import com.entities.enums.PubType;
import com.entities.enums.RecommandationAvis;
import com.entities.enums.RecommandationCategory;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.services.Interfaces.AdService;
import com.services.Interfaces.NewsletterService;
import com.services.Interfaces.PartenaireService;
import com.services.Interfaces.RecommandationService;

@RestController
@RequestMapping("/acceuil/")
public class AcceuilController {
	
	@Autowired
	AdService ServiceAd;
	@Autowired
	NewsletterService ServiceNews;
	@Autowired
	PartenaireService ServicePartenaire;
	@Autowired
	RecommandationService ServiceRecommandation;
	
	@PostMapping("ajouterAd")
	public void AjouterPub(@RequestBody Ad pub) {
		ServiceAd.AddPubLetter(pub);
	}
	
	@PostMapping("ajouterNews")
	public void AjouterNews(@RequestBody Newsletter news){
		ServiceNews.AddNewsletter(news);
	}
	
	@PostMapping("ajouterPartner")
	public void AjouterPartenaire(@RequestBody Partner p) {
		System.out.println("PARTNER:"+p.getLogoPart());
		ServicePartenaire.AddPartenaire(p);
	}
	
	@PostMapping("ajouterRecom")
	public void AjouterRecom(@RequestBody Recommandation recom) {
		System.out.println("PARTNER:"+recom);
		ServiceRecommandation.AddRecom(recom);
	}
	
	@PostMapping("AdRegion")
	public List<Ad> pubRegion(@RequestBody Map<String,String> json) {
		String pubreg = json.get("pubregion");
		String pubtyp = json.get("pubtype");
		String country = json.get("country");
		PubRegion pubregion = PubRegion.valueOf(pubreg);
		PubType pubtype = PubType.valueOf(pubtyp);
		return ServiceAd.PubRegion(pubregion, pubtype,country);
	}
	
	@GetMapping("DeleteAd/{idpub}")
	public void DeleteAd(@PathVariable("idpub") int idpub) {
		ServiceAd.DeleteAd(idpub);
	}
	
	@PostMapping("Stats")
	public List<Object[]> StatsAd(){
		return ServiceAd.StatsAd();
	}
//	@PostMapping("NewsAll")
//	public List<Newsletter> NewsAll() {
//		return ServiceNews.NewsAll();
//	}
	
	@GetMapping("newsAllRegion/{type}")
	public List<Newsletter> NewsAllRegion(@PathVariable("type") ArticleRegion art) throws IOException, GeoIp2Exception{
		return ServiceNews.NewsAllRegion(art);
	}
	
	@PostMapping("viewnumbernews")
	public List<Newsletter> ViewNumberNews() {
		return ServiceNews.ViewNumberNews();
	}
	
	
	//PARTNERS
	
	@GetMapping("DeletePartner/{nompart}")
	public void DeletePart(@PathVariable("nompart") String nompart) {
		ServicePartenaire.DeletePartenaire(nompart);
	}
	
	@PostMapping("listPartenaires")
	public List<Partner> listPartenaires() {
		return ServicePartenaire.listPartenaires();
	}
	
	
	@GetMapping("DeleteRecom/{idrecom}")
	public void DeletePart(@PathVariable("idrecom") int idrecom) {
		ServiceRecommandation.DeleteRecom(idrecom);
	}

	
	@GetMapping("listRecom/{categorie}")
	public List<Recommandation> listRecommandation(@PathVariable("categorie") RecommandationCategory cat) {
		return ServiceRecommandation.listRecommandation(cat);
	}
	
	@PostMapping("listRecomAvis")
	public List<Recommandation> listRecommandationAvis(@RequestBody Map<String,String> json) {
		String reecat = json.get("categorie");
		String reccavis = json.get("avis");
		String pays = json.get("country");
		RecommandationCategory categorie = RecommandationCategory.valueOf(reecat);
		RecommandationAvis avis = RecommandationAvis.valueOf(reccavis);
		return ServiceRecommandation.listRecommandationAvis(categorie,avis,pays);
	}
	
}
