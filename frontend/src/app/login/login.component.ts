import { Component, OnInit } from '@angular/core';
import {NgForm, UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";
import {StatsService, Token} from "../stats.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {
  failedLogin: boolean;
  username: string = "";
  password: string = "";

  constructor(private formBuilder: UntypedFormBuilder,
              private statsService: StatsService,
              private authService: AuthService,
              private router: Router) {
    this.failedLogin = false;
  }

  ngOnInit(): void {
    if(this.authService.isLoggedIn()) {
      this.router.navigate(['home']);
    }
  }

  async submit(form: NgForm) {
    if (!form.valid) {
      return;
    }

    this.authService.login(this.username,this.password).then(success => {
      if(this.authService.isLoggedIn()) {
        this.router.navigate(['home']);
      }
      else {
        this.failedLogin = true;
      }
    })

  }

}
