import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Symbol, SymbolHistorical } from '../models/interfaces';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SymbolService {

  endpoint = environment.apiUrl;


  constructor(private http: HttpClient) { }

  getAllNemotecnicos(): Observable<Symbol[]> { 
    return this.http
      .get<Symbol[]>(`${this.endpoint}/symbols`)
      .pipe()
  }
  
  getAllHistorical() {
    return this.http.get(`${this.endpoint}/historical`);
  }

  getHistoricalBySymbol(symbol: any): Observable<SymbolHistorical[]> {
    return this.http
      .get<SymbolHistorical[]>(`${this.endpoint}/historical/${symbol}`)
      .pipe();
  }

}
