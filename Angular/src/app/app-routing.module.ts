import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddAdsComponent } from './publicity/add-ads/add-ads.component';
import { DeleteAdsComponent } from './publicity/delete-ads/delete-ads.component';
import { ShowAdsComponent } from './publicity/show-ads/show-ads.component';

const routes: Routes = [
  {path:"",redirectTo:"showAds",pathMatch:"full"},
  {path:"showAds",component:ShowAdsComponent},
  {path:"addAds",component:AddAdsComponent},
  {path:"deleteAds",component:DeleteAdsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
