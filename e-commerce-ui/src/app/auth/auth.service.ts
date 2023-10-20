import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly USER_PATH = `${environment.backendUrl}/api/v1/users`;

  constructor(private http: HttpClient) {}

  onLogin(): Observable<string> {
    return this.http.post<string>(`${this.USER_PATH}/login`, {});
  }

  onSignup(): Observable<string> {
    return this.http.get<string>(`${this.USER_PATH}/register`);
  }
}
