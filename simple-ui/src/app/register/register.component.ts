import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../_services/auth.service';
import { UserService } from "../_services/user.service";


interface signup_data {
  username: string,
  fname: string,
  lname: string,
  phone: number,
  email: string,
  password: string,
  cpassword: string
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form_data!: FormGroup
  signupData: signup_data;

  constructor(private apiRequest: UserService, private authService: AuthService, private router: Router, private http: HttpClient) { 
    this.signupData = { } as signup_data;
  }

  data: any;
  public failed = false;
  public success = false;

  ngOnInit(): void {

    this.form_data = new FormGroup({
      username: new FormControl(this.signupData.username, [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern("([a-zA-Z0-9\s]+){1,}")
      ]),
      fname: new FormControl(this.signupData.fname, [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern("([a-zA-Z\s]+){1,}")
      ]),
      lname: new FormControl(this.signupData.lname, [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern("([a-zA-Z\s]+){1,}")
      ]),
      phone: new FormControl(this.signupData.phone, [
        Validators.required,
        Validators.minLength(12),
        Validators.maxLength(12),
        Validators.pattern("([0-9\s]+)")
      ]),
      email: new FormControl(this.signupData.email, [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(100)
      ]),
      password: new FormControl(this.signupData.password, [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(16),
      ]),
      cpassword: new FormControl(this.signupData.cpassword, [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(16),
      ]),
    });

  }

  registration(): void {
    if(this.form_data.value.password!=this.form_data.value.cpassword){
      this.failed = true;
    }
    else{
      let reqBody = {
        "username": this.form_data.value.username,
        "password": this.form_data.value.password,
        "first_name": this.form_data.value.fname,
        "last_name": this.form_data.value.lname,
        "email": this.form_data.value.email,
        "phone": this.form_data.value.phone
      }
      try {
        this.http.post("http://127.0.0.1:8080/superuser/register",reqBody,{ responseType: 'text'}).subscribe((data) => this.success = true);
      } catch {
        this.failed=true;
      }
    }
  }

  reset(): void {
    window.location.reload()
  }

}
