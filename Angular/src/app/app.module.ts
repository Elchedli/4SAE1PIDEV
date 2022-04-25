import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ShowAdsComponent } from './publicity/show-ads/show-ads.component';
import { DeleteAdsComponent } from './publicity/delete-ads/delete-ads.component';
import { PubliciteService } from './publicity/service/publicite.service';
import { MatSelectCountryModule } from '@angular-material-extensions/select-country';
import { AddAdsComponent } from './publicity/add-ads/add-ads.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ListpartnersComponent } from './partner/listpartners/listpartners.component';
import { AjoutparnterComponent } from './partner/ajoutparnter/ajoutparnter.component';

@NgModule({
  declarations: [
    AppComponent,
    ShowAdsComponent,
    DeleteAdsComponent,
    AddAdsComponent,
    ListpartnersComponent,
    AjoutparnterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatSelectCountryModule.forRoot('en'),
    BrowserAnimationsModule
  ],
  providers: [PubliciteService],
  bootstrap: [AppComponent]
})
export class AppModule { }
