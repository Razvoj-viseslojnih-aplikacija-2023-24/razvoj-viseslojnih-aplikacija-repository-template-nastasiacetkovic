import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Filijala } from 'src/app/models/filijala';
import { KorisnikUsluge } from 'src/app/models/korisnik_usluge';
import { Usluga } from 'src/app/models/usluga';
import { FilijalaService } from 'src/app/services/filijala.service';
import { KorisnikUslugeService } from 'src/app/services/korisnik-usluge.service';
import { UslugaService } from 'src/app/services/usluga.service';

@Component({
  selector: 'app-usluga-dialog',
  templateUrl: './usluga-dialog.component.html',
  styleUrls: ['./usluga-dialog.component.css']
})
export class UslugaDialogComponent implements OnInit {

  flag!:number;
  korisniciUsluge!:KorisnikUsluge[];

  constructor(
    public snackBar : MatSnackBar,
    public dialogRef: MatDialogRef<Usluga>,
    @Inject (MAT_DIALOG_DATA) public data: Usluga,
    public service:UslugaService,
    public korisnikUslugeService:KorisnikUslugeService
  ){};

  ngOnInit(): void {
    this.korisnikUslugeService.getAllKorisnikUsluges().subscribe(
      (data)=>{
        this.korisniciUsluge=data;
      }
    )
  }


  public compare(a:any, b:any){
    return a.id==b.id;
  }

  public add() {
    this.service.addUsluga(this.data).subscribe(
      (data) => {
        this.snackBar.open(`Uspesno dodata usluga sa nazivom:   ` + (this.data.naziv), `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno dodavanje`, `Zatvori`, {duration:1000});
    }
  }

  public update() {
    this.service.updateUsluga(this.data).subscribe(
      (data) => {
        this.snackBar.open(`Uspesno azurirana usluga sa nazivom:   ` + (this.data.naziv), `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno azuriranje`, `Zatvori`, {duration:1000});
    }

  }

  public delete() {
    this.service.deleteUsluga(this.data.id).subscribe(
      (data) => {
        this.snackBar.open((data), `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno brisanje`, `Zatvori`, {duration:1000});
    }
  }

  public cancel(){
    this.dialogRef.close();
    this.snackBar.open(`Odustali ste od zimena`, `Zatvori`, {duration:2500});
  }
}