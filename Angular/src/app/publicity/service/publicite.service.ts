import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PubliciteService {
  constructor(private http:HttpClient) { }
  public ajouterAd(Publicite:any){
    return this.http.post("http://localhost:8083/voyageAffaires/acceuil/ajouterAd",Publicite,{responseType:'text' as 'json'});
  }

  public DeleteAd(number:number){
    return this.http.get("http://localhost:8083/voyageAffaires/acceuil/DeleteAd/"+number,{responseType:'text' as 'json'});
  }

  public afficherregion(jsonData:Object){
    return this.http.post<any>("http://localhost:8083/voyageAffaires/acceuil/AdRegion",jsonData);
  }

  public getstats(){
    return this.http.get("http://localhost:8083/voyageAffaires/acceuil/Stats");
  }
}
