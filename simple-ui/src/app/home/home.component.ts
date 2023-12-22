import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../_services/auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private apiRequest: UserService, private authService: AuthService, private router: Router) { }

  users: any;
  data: any;
  public page: number =0;
  public isLoggedIn = false;
  public failed = false;
  public success = false;
  ngOnInit(): void {
    this.isLoggedIn = !!this.authService.checkCredentials();

    if (this.isLoggedIn) {
      //this.apiRequest.getAllUsers().subscribe(data => this.users=data);
      this.getUsers(this.page);
    }
  }

  getUsers(page_no:number){
    this.apiRequest.getUsers(page_no).subscribe(data => this.data=data);
  }

  deleteUser(id:number): void {
    try {
      this.apiRequest.deleteUser(id)
      this.success=true
    } catch {
      this.failed = true;
    } finally {
      window.location.reload()
    }
   }
  
  redirect(){
    this.router.navigate(['home'])
  }
}

