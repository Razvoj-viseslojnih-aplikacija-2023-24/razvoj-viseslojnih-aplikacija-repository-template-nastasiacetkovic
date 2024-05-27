import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BankaComponent } from './components/main/banka/banka.component';
import { FilijalaComponent } from './components/main/filijala/filijala.component';
import { KorisnikUslugeComponent } from './components/main/korisnik-usluge/korisnik-usluge.component';
import { UslugaComponent } from './components/main/usluga/usluga.component';
import { AboutComponent } from './components/utility/about/about.component';
import { AuthorComponent } from './components/utility/author/author.component';
import { HomeComponent } from './components/utility/home/home.component';

const routes: Routes = [
  {path:'banka', component: BankaComponent},
  {path:'korisnik_usluge', component: KorisnikUslugeComponent},
  {path:'usluga', component: UslugaComponent},
  {path:'filijala', component: FilijalaComponent},
  {path:'home', component: HomeComponent},
  {path:'author', component: AuthorComponent},
  {path:'about', component: AboutComponent},
  {path:'', component: HomeComponent, pathMatch:'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
