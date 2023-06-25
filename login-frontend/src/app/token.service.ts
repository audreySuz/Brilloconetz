import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User }  from './user';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private baseURL= "http://localhost:8080/api"
  constructor(private httpClient: HttpClient) { }

  generateToken(user: User): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`+"/validate", user)
  }

  verifyToken(token: string): Observable<any> {
    const url = `${this.baseURL}/verify`;
    const params = { token: token };
    return this.httpClient.post(url, null, { params: params });
  }

}
