package examin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import examin.service.DiscussionService;
import examin.service.MessaService;

@RestController
@RequestMapping("/api/")
public class AppController {
	@Autowired
	DiscussionService SDiscussion;
	MessaService SMessage;
	
//	@PostMapping("ajouterEtAffecterClientBoutiques/{lidb}")
//	public void ajouterEtAffecterClientBoutiques(@RequestBody Client client,@PathVariable("lidb") List<Long> idBoutiques) {
//		ICl.ajouterEtAffecterClientBoutique(client, idBoutiques);
//	}
//
//	@GetMapping("listeClients/{idb}")
//	public List<Client> listeClients(@PathVariable("idb") Long idBoutique) {
//		return ICl.listeClients(idBoutique);
//	}
	
//	@PostMapping("ajoutetudiant")
//	public void ajouterEtudiant(@RequestBody Map<String,String> json) {
//		String idDoc = json.get("idDoc");
//		Long matricule = Long.parseLong(json.get("matricule"));
//		System.out.println("idDoc est : "+ idDoc +"matricule : "+matricule);
//		ITest.testfunction(idDoc, matricule);
//	}
	
}