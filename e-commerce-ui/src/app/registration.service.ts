import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  
  private registrationUrl = 'http://localhost:4200';  
  
  constructor(private http: HttpClient) { }
  
  
  registerUser(email: string): Observable<any> {
    const body = {email};  
    return this.http.post<any>(this.registrationUrl, body);
  }
}