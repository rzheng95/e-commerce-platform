import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

interface LoginResponse {
  email: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  onLogin(email: string): Observable<LoginResponse> {
    return this.http.get<LoginResponse>(
      `http://localhost:8080/api/v1/users/check-email?email=${email}`
    );
  }

  onSignup(email: string): void {}
  // onSignup(email: string): Observable<LoginResponse> {
  //   return this.http.get<LoginResponse>(
  //     `http://localhost:8080/api/v1/users/check-email?email=${email}`
  //   );
  // }
}
