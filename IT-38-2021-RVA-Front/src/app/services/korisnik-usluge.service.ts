import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { KORISNIK_USLUGE_URL } from '../constants';
import { KorisnikUsluge } from '../models/korisnik_usluge';

@Injectable({
  providedIn: 'root'
})
export class KorisnikUslugeService {

  constructor(private httpClient:HttpClient) { }

  public getAllKorisnikUsluges():Observable<any>{
    return this.httpClient.get(`${KORISNIK_USLUGE_URL}`)
  }

  public addKorisnikUsluge(korisnik_usluge:KorisnikUsluge):Observable<any>{
    return this.httpClient.post(`${KORISNIK_USLUGE_URL}`, korisnik_usluge);
  }

  public updateKorisnikUsluge(korisnik_usluge:KorisnikUsluge): Observable<any> {
    return this.httpClient.put(`${KORISNIK_USLUGE_URL}/id/${korisnik_usluge.id}`, korisnik_usluge)
  }

  public deleteKorisnikUsluge(korisnik_uslugeId: number):Observable<any> {
    return this.httpClient.delete(`${KORISNIK_USLUGE_URL}/id/${korisnik_uslugeId}`, {responseType: 'text'});
  }
}
