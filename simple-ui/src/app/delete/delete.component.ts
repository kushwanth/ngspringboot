import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.css']
})
export class DeleteComponent implements OnInit {

  constructor(private apiRequest: UserService, private authService: AuthService) { 
  }
  data: any
  public isLoggedIn = false;
  public failed = false;
  public success = false;
  ngOnInit(): void {
    this.isLoggedIn = !!this.authService.checkCredentials();
    if (!this.isLoggedIn) {
      this.authService.login();
    }
    let url = new URL(window.location.href).pathname;
    const id = parseInt(url.split("/delete/")[1])
    this.apiRequest.getUser(id).subscribe(data => this.data=data);
    console.log(this.data)
  }

    async deleteUser(id:number):Promise<void>{
      try {
        await this.apiRequest.deleteUser(id);
        this.success=true;
      } catch {
        this.failed = true;
      }
     }    
}
