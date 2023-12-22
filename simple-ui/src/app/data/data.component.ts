import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../_services/auth.service';
import { UserService } from '../_services/user.service';


interface user_data {
  fname: string;
  lname: string;
}

@Component({
  selector: 'app-data',
  templateUrl: './data.component.html',
  styleUrls: ['./data.component.css']
})

export class DataComponent implements OnInit {

  form_data!: FormGroup
  userData: user_data;

  constructor(private apiRequest: UserService, private authService: AuthService, private router: Router) { 
    this.userData = { } as user_data;
  }
  users: any;
  data: any;
  public page: number =0;
  public isLoggedIn = false;
  public userUpdate = false;
  public failed = false;
  public success = false;
  ngOnInit(): void {
    this.isLoggedIn = !!this.authService.checkCredentials();

    if (!this.isLoggedIn) {
      this.authService.login();
    }

    let url = new URL(window.location.href).pathname;
    if(url.includes("update")){
      const id = parseInt(url.split("/update/")[1])
      this.apiRequest.getUser(id).subscribe(data =>this.data=data)
      this.userUpdate = true;
    }
    this.form_data = new FormGroup({
      fname: new FormControl(this.userData.fname, [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern("([a-zA-Z\s]+){1,}")
      ]),
      lname: new FormControl(this.userData.lname, [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern("([a-zA-Z\s]+){1,}")
      ]),
    });
  }

  operation(): void {
    let url = new URL(window.location.href).pathname;
    if(url=="/create"){this.createUser()}
    else if(url.includes("update")){
      const id = parseInt(url.split("/update/")[1])
      this.updateUser(id)
    }
    else {window.location.href="/home"}
  }

  
  async createUser():Promise<void> {
    console.log(this.form_data)
    try {
     await this.apiRequest.createUser(this.form_data.value.fname,this.form_data.value.lname);
      this.success=true;
    } catch(error) {
      this.failed = true;
    } 
  }

    async updateUser(id:number):Promise<void> {
      //this.apiRequest.getUser(id).subscribe(data =>this.data=data)
      try {
        await this.apiRequest.updateUser(this.form_data.value.fname,this.form_data.value.lname,id);
        this.success=true;
      } catch {
        this.failed = true;
      }
    }

    redirect(){
      this.router.navigate(['home'])
    }

}
