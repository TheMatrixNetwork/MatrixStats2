import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {AuthService} from "./auth.service";
import { environment } from 'src/environments/environment';
import {PlayerStats} from "./models/player-stats";
import {PlayerKill} from "./models/player-kill";

export interface Location {
  x: number;
  y: number;
  z: number;
}

export interface MatrixPlayer {
  stats: PlayerStats[];
  username: string;
  uuid: string;
}

export interface Token {
  token: string;
  expiryDate: string;
}

export interface Account {
  username: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class StatsService {
  constructor(private http: HttpClient, private _authService: AuthService) { }

  public getLatestPlayerStats(): Observable<PlayerStats> {
    //return this.http.get<PlayerStats>(`http://localhost:8080/api/stats/${playerName}`);
    return this.http.get<PlayerStats>(environment.url + `/api/stats/latest`);
  }

  public getSkinName(): any {
      return this.http.get<any>(environment.url + `/api/skin/`);
  }

  getMatrixDexPlayerStats(playerName: string) {
    return this.http.get<PlayerStats>(environment.url + `/api/stats/matrixdex/` + playerName);
  }

  getKilledPeople(): Observable<PlayerKill[]> {
    return this.http.get<PlayerKill[]>(environment.url + `/api/kills/all`);
  }
}
