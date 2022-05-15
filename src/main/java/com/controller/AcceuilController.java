package com.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Ad;
import com.entity.Newsletter;
import com.entity.Partner;
import com.entity.Recommandation;
import com.enums.ArticleRegion;
import com.enums.PubRegion;
import com.enums.PubType;
import com.enums.RecommandationAvis;
import com.enums.RecommandationCategory;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.service.AdService;
import com.service.NewsletterService;
import com.service.PartenaireService;
import com.service.RecommandationService;
import com.utils.AcceuilUtils;
@CrossOrigin(origins = "*")
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
	public boolean AjouterNews(@RequestBody Newsletter news){
		ServiceNews.AddNewsletter(news);
		return true;
	}
	
	@PostMapping("ajouterPartner")
	public void AjouterPartenaire(@RequestBody Partner p) {
		System.out.println("PARTNER:"+p.getLogoPart());
		ServicePartenaire.AddPartenaire(p);
	}
	
	@PostMapping("ajouterRecom")
	public void AjouterRecom(@RequestBody Recommandation recom) {
		System.out.println("PARTNER:"+recom);
		recom.setRecomAvis(RecommandationAvis.bas);
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
	
	@PostMapping("adLocation")
	public List<Ad> adLocation(@RequestBody Map<String,String> json) throws IOException, GeoIp2Exception {
		String pubreg = json.get("pubregion");
		String pubtyp = json.get("pubtype");
		String[] countr = AcceuilUtils.geoData();
		PubRegion pubregion = PubRegion.valueOf(pubreg);
		PubType pubtype = PubType.valueOf(pubtyp);
		return ServiceAd.PubRegion(pubregion, pubtype,countr[0]);
	}
	
	@GetMapping("DeleteAd/{idpub}")
	public void DeleteAd(@PathVariable("idpub") int idpub) {
		ServiceAd.DeleteAd(idpub);
	}
	
	@GetMapping("Stats")
	public List<Object> StatsAd(){
		List<Object> datas = ServiceAd.StatsAd();
//		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//		try {
//			String json = ow.writeValueAsString(datas);
//			return json;
////			System.out.println("datas : "+json);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return datas;
	}
//	@PostMapping("NewsAll")
//	public List<Newsletter> NewsAll() {
//		return ServiceNews.NewsAll();
//	}
	
	@GetMapping("newsAllRegion/{type}")
	public List<Newsletter> NewsAllRegion(@PathVariable("type") ArticleRegion art) throws IOException, GeoIp2Exception{
		return ServiceNews.NewsAllRegion(art);
	}
	@GetMapping("deleteNews/{id}")
	public boolean NewsAllRegion(@PathVariable("id") int id) {
		return ServiceNews.deleteNews(id);
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
	
	@GetMapping("getRecom/{idrecom}")
	public Recommandation getRecom(@PathVariable("idrecom") int idrecom) {
		return ServiceRecommandation.getRecom(idrecom);
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
//		String reccavis = json.get("avis");
		String pays = json.get("country");
		RecommandationCategory categorie = RecommandationCategory.valueOf(reecat);
//		RecommandationAvis avis = RecommandationAvis.valueOf(reccavis);
		return ServiceRecommandation.listRecommandationAvis(categorie,pays);
	}
	
	
	@GetMapping("searchRecom/{idrecom}")
	public Recommandation searchRecom(@PathVariable("idrecom") int idrecom) {
		return ServiceRecommandation.SearchRecommandation(idrecom);
	}
	
	@GetMapping("searchNews/{idNews}")
	public Newsletter searchNews(@PathVariable("idNews") int idNews) {
		return ServiceNews.SearchNewsletter(idNews);
	}
	
	@GetMapping("searchAd/{idAd}")
	public Ad searchAd(@PathVariable("idAd") int idAd) {
		return ServiceAd.searchAd(idAd);
	}
	
}
