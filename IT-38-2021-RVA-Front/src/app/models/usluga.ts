import { Filijala } from "./filijala";
import { KorisnikUsluge } from "./korisnik_usluge";

export class Usluga {
    id!:number;
    naziv!:string;
    opisUsluge!:string;
    provizija!:number;
    datumUgovora!:Date;
    filijala!:Filijala;
    korisnik_usluge!: KorisnikUsluge;

}