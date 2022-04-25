import { Component, OnInit } from '@angular/core';
import { Publicite } from '../../model/publicite';
import { PubliciteService } from '../service/publicite.service';
@Component({
  selector: 'app-show-ads',
  templateUrl: './show-ads.component.html',
  styleUrls: ['./show-ads.component.css']
})
export class ShowAdsComponent implements OnInit {
  pub: Publicite= new Publicite();
  message:any;
  type: string = 'Remise';
  region: string = 'Global';
  place?: string;
  stats: any;
  listpub: any;
  constructor(private service:PubliciteService) { }
  ngOnInit(): void {
    this.listAd();
    this.getStats();
  }

  public effacerAd(id:number){
    let resp = this.service.DeleteAd(id);
    resp.subscribe((data)=>this.message = data);

  }

  public getStats(){
    let resp = this.service.getstats();
    resp.subscribe((data)=>{
      this.stats = data;
      console.log(data);
    });
    // console.log("data is : "+this.stats[0]);
  }

  public listAd(){
    // console.log("region : "+this.region.toUpperCase()+" type : "+this.type.toUpperCase()+" place : "+this.place);
    let resp =  
      this.region == 'Nationnal' ? this.service.afficherregion({
        pubregion:this.region.toUpperCase(),
        pubtype:this.type.toUpperCase(),
        'country':this.place
      })
      : this.service.afficherregion({
        pubregion:this.region.toUpperCase(),
        pubtype:this.type.toUpperCase()
      });
    resp.subscribe((datas)=>{
      this.listpub =  datas;
      console.log(datas);
    });
    // console.log(this.listpub);
  }

}
