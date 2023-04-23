import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest, HttpHeaders,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import {AuthService} from "../auth.service";
import {environment} from "../../environments/environment";

@Injectable()
export class AddHeaderInterceptor implements HttpInterceptor {
  constructor(private _authService: AuthService) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(this._authService.getToken()) {
      return next.handle(req
        .clone({ headers: req.headers.append('Authorization', this._authService.getToken()!) }))
    }
    return next.handle(req);
  }
}
