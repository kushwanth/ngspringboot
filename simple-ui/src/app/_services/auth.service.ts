import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';

export class Token {
  constructor(
    public access_token: string, 
    public refresh_token: string,
    public scope: string,
    public id_token:string,
    public token_type:string,
    public expires_in: number
  ) { }
} 

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public clientId = 'Client';
  private clientSecret = "6mq$zEo^E0iRo$0K9W5JRY&8S96ylN#d@^0d^7DCvf!4r5uP";
  private credential = btoa(`${this.clientId}:${this.clientSecret}`);
  public redirectUri = 'http://127.0.0.1:4200/oauth2/callback';
  constructor(private _http: HttpClient) { }

  login() {
    let code: string = new URL(window.location.href).searchParams.get('code') || "";
    if (code != ""){this.retrieveToken(code)}
    else{
    window.location.href = 
      'http://127.0.0.1:8080/oauth2/authorize?response_type=code&client_id=' + 
         this.clientId + '&redirect_uri='+ this.redirectUri;}
    }

  retrieveToken(code: string) :any {
    //window.location.href = 'http://127.0.0.1:8080/oauth2/authorize?response_type=code&scope=openid&client_id=' + this.clientId + '&redirect_uri='+ this.redirectUri;
    let Authparams = new URLSearchParams();   
    Authparams.append('grant_type','authorization_code');
    Authparams.append('client_id', this.clientId);
    Authparams.append('redirect_uri', 'http://127.0.0.1:4200/oauth2/callback');
    Authparams.append('code',code);
      this._http.post('http://127.0.0.1:8080/oauth2/token?'+ Authparams.toString(),null ,{
      headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          "Authorization": `Basic ${this.credential}`
      }
     })
      .subscribe( token => this.saveToken(token));
          //err => alert('Invalid Credentials')
          
  }

  saveToken(token: any) {
    let expireDate = new Date().getTime() + (token.expires_in);
    sessionStorage.setItem("access_token", token.access_token);
    sessionStorage.setItem("refresh_token", token.refresh_token)
    sessionStorage.setItem(" expireDate", expireDate.toString())
    console.log('Obtained Access token');
    window.location.href = 'http://127.0.0.1:4200';
  }

  checkCredentials() {
    if (sessionStorage.getItem('access_token')!=null){return true} else {return false};
  } 

  logout() {
    sessionStorage.clear();
    window.location.href="http://127.0.0.1:4200/home";
  }
}