import { Injectable } from '@angular/core';
import {Account, Token} from "./stats.service";
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {environment} from "../environments/environment";
import {CookieService} from "ngx-cookie";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
              private _cookieService: CookieService) { }

  public async login(username: string, password: string): Promise<boolean> {
    return new Promise<boolean>(res => {
      this.postAccount({
        username,
        password
      }).subscribe(
        (data: Token) => {
          this._cookieService.put("token", data.token.toString());
          res(true);
        },
        (error:Error) =>  {
          res(false);
        }
      );
    });
  }

  private postAccount(account: Account): any {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
      })
    };

    return this.http.post<Token>(environment.url + `/api/auth`, account, httpOptions);
  }

  public getToken() {
    return this._cookieService.get("token");
  }

  public isLoggedIn(): boolean {
    let str = this._cookieService.get("token");
    return str != null;
  }
}
