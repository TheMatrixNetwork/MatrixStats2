import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { LayoutModule } from '@angular/cdk/layout';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import {CookieModule} from "ngx-cookie";
import {JWT_OPTIONS, JwtHelperService} from "@auth0/angular-jwt";
import {NgOptimizedImage} from "@angular/common";
import {AddHeaderInterceptor} from "./interceptor/backend-interceptor";
import { PlayerStatusComponent } from './components/player-status/player-status.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    PlayerStatusComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    CookieModule.withOptions(),
    BrowserAnimationsModule,
    AppRoutingModule,
    LayoutModule,
    ReactiveFormsModule,
    NgOptimizedImage,
  ],
  exports: [
  ],
  providers:[{ provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AddHeaderInterceptor,
      multi: true,
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
