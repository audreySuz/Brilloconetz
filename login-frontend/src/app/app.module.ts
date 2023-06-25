import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { TokenGeneratorComponent } from './token-generator/token-generator.component';
import {AppRoutingModule} from "./app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {BsDatepickerConfig, BsDatepickerModule} from "ngx-bootstrap/datepicker";
import {TooltipModule} from "ngx-bootstrap/tooltip";
import {AlertConfig} from "ngx-bootstrap/alert";
import {BsModalService, ModalModule} from "ngx-bootstrap/modal";
import {BsDropdownConfig, BsDropdownModule} from "ngx-bootstrap/dropdown";
import { ModalComponent } from './modal/modal.component';
import { TokenVerifierComponent } from './token-verifier/token-verifier.component';



@NgModule({
  declarations: [
    AppComponent,
    TokenGeneratorComponent,
    ModalComponent,
    TokenVerifierComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    TooltipModule.forRoot(),
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    BsDatepickerModule,
    BsDatepickerModule.forRoot(),
    ModalModule.forRoot(),
    BsDropdownModule,
    ModalModule,   ],
  providers: [AlertConfig, BsDatepickerConfig, BsDropdownConfig,BsModalService],
  bootstrap: [AppComponent],
  exports: [BsDatepickerModule]
})
export class AppModule { }
