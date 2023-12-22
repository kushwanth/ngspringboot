import { Component, OnInit } from '@angular/core';
import { AuthService } from './_services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public isLoggedIn = false;
  username?: string;
  title="Simple CRUD API";
  constructor(private authService: AuthService){}

  ngOnInit(): void {
  this.isLoggedIn = !!this.authService.checkCredentials();

    if (this.isLoggedIn) {
      this.username = "User";
    }
  }

  logout(): void {
    this.authService.logout();
    window.location.href="/home"
  }
}
