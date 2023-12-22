import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


const API_URL: string = "http://127.0.0.1:8080/v1/";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<any> {
    return this.http.get(API_URL + 'users/all', { responseType: 'json' });
  }

  getUsers(page:number=0): Observable<any> {
    return this.http.get(API_URL + 'users?page='+page.toString(), { responseType: 'json' });
  }

  getUser(id:number): Observable<any>{
    return this.http.get(API_URL+"users/"+id.toString(), {responseType: 'json'})
  }

  createUser(fname:string,lname:string): void{
    this.http.post(API_URL+ 'user/create',{
      "first_name": fname,
      "last_name": lname
    }).subscribe({
      next: (response) => null,
      error: (error) => console.log(error),
    });
  }

  updateUser(fname:string,lname:string, id: number): void{
    this.http.put(API_URL+ 'user/update/' +id.toString(),{
      "first_name": fname,
      "last_name": lname
    }).subscribe({
      next: (response) => null,
      error: (error) => console.log(error),
    });
  }

  deleteUser(id: number): void{
    this.http.delete(API_URL+ 'user/delete/' +id.toString()).subscribe({
      next: (response) => null,
      error: (error) => console.log(error),
    });
  }

}
