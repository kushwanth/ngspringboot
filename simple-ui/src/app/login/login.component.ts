import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService) { }
  private loggedIn:boolean=false;

  ngOnInit(): void {
    if (sessionStorage.getItem("access_token") ==  null){
      this.authService.login();
    }
    else{
      this.loggedIn=true;
    }
  }

  reloadPage(): void {
    window.location.reload();
  }

}
