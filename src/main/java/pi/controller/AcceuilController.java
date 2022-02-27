package pi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pi.entity.Ad;
import pi.entity.Newsletter;
import pi.entity.Partner;
import pi.entity.Recommandation;
import pi.enums.ArticleRegion;
import pi.enums.Countries;
import pi.enums.PubRegion;
import pi.enums.PubType;
import pi.enums.RecommandationAvis;
import pi.enums.RecommandationCategory;
import pi.service.AdService;
import pi.service.NewsletterService;
import pi.service.PartenaireService;
import pi.service.RecommandationService;

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
	public void AjouterNews(@RequestBody Newsletter news) {
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
	
	@GetMapping("AdRegion/{region}/{typepub}")
	public List<Ad> pubRegion(@PathVariable("region") PubRegion pubregion,@PathVariable("typepub") PubType pubtype) {
		return ServiceAd.PubRegion(pubregion, pubtype);
	}
	
	@GetMapping("DeleteAd/{idpub}")
	public void DeleteAd(@PathVariable("idpub") int idpub) {
		ServiceAd.DeleteAd(idpub);
	}
	
	@PostMapping("NewsAll")
	public List<Newsletter> NewsAll() {
		return ServiceNews.NewsAll();
	}
	
	@GetMapping("newsAllRegion/{region}/{country}")
	public List<Newsletter> NewsAllRegion(@PathVariable("region") ArticleRegion articleRegion,@PathVariable("country") Countries country) {
		return ServiceNews.NewsAllRegion(articleRegion, country);
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
	//Recommandations
	@GetMapping("listRecom/{reecat}/{reccavis}")
	public List<Recommandation> listRecommandation(@PathVariable("reecat") RecommandationCategory reecat,@PathVariable("reccavis") RecommandationAvis reccavis) {
		return ServiceRecommandation.listRecommandation(reecat, reccavis);
	}
	
	@GetMapping("listRecomAvis/{reecat}/{reccavis}/{country}")
	public List<Recommandation> listRecommandation(@PathVariable("reecat") RecommandationCategory reecat,@PathVariable("reccavis") RecommandationAvis reccavis,@PathVariable("country") Countries country) {
		return ServiceRecommandation.listRecommandationAvis(reecat, country,reccavis);
	}
	
}
