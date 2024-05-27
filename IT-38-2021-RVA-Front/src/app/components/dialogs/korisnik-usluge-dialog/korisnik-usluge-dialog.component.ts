import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { KorisnikUsluge } from 'src/app/models/korisnik_usluge';
import { KorisnikUslugeService } from 'src/app/services/korisnik-usluge.service';

@Component({
  selector: 'app-korisnik-usluge-dialog',
  templateUrl: './korisnik-usluge-dialog.component.html',
  styleUrls: ['./korisnik-usluge-dialog.component.css']
})
export class KorisnikUslugeDialogComponent {
  
  flag!:number;

  constructor(
    public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<KorisnikUsluge>,
    @Inject (MAT_DIALOG_DATA) public data: KorisnikUsluge,
    public service: KorisnikUslugeService
  ) {}

  public add() {
    this.service.addKorisnikUsluge(this.data).subscribe(
      (data) => {
        this.snackBar.open(`Uspesno dodat korisnik:   ` + (this.data.ime) + ` ` + (this.data.prezime), `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno dodavanje`, `Zatvori`, {duration:1000});
    }
  }

  public update() {
    this.service.updateKorisnikUsluge(this.data).subscribe(
      (data) => {
        this.snackBar.open(`Uspesno azuriran korisnik:   ` + (this.data.ime) + ` ` + (this.data.prezime), `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno azuriranje`, `Zatvori`, {duration:1000});
    }

  }

  public delete() {
    this.service.deleteKorisnikUsluge(this.data.id).subscribe(
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
