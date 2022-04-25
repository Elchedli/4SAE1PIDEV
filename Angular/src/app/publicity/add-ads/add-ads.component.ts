import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { PubliciteService } from '../service/publicite.service';
// import {Country} from '@angular-material-extensions/select-country'; 
@Component({
  selector: 'app-add-ads',
  templateUrl: './add-ads.component.html',
  styleUrls: ['./add-ads.component.css']
})
export class AddAdsComponent implements OnInit {
  region:string = "Global";
  type:string = "Remise";
  country:string  = "Tunisia";
  message: any;
  constructor(private service:PubliciteService) { }

  ngOnInit(): void {
  }

  // onCountrySelected(country: Country) {
  //   console.log(country);
  // }

  save(f: NgForm){
    console.log(f.value);
    f.value['pubRegion'] = f.value['pubRegion'].toUpperCase();
    f.value['pubType'] = f.value['pubType'].toUpperCase();
    this.ajouterAd(f.value);
  }
  // submit({value,valid}: {value:User,valid:boolean}){
  //   this.user = value;
  //   console.log(this.user);
  // }

  public ajouterAd(value:any){
    let resp = this.service.ajouterAd(value);
    resp.subscribe((data)=>this.message = data);
  }
}
