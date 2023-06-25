import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TokenGeneratorComponent} from "./token-generator/token-generator.component";
import {TokenVerifierComponent} from "./token-verifier/token-verifier.component";

const routes: Routes = [
  {path:'',component: TokenGeneratorComponent},
  {path:'verify',component: TokenVerifierComponent},
  {path:'', redirectTo:'',pathMatch:'full'},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
