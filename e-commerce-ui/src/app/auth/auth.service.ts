import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginParams } from '../util/login';
import { LoginStatus } from '../util/login-status';
import { SignupBody } from '../util/signup';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly USER_PATH = `${environment.backendUrl}/api/v1/users`;

  constructor(private http: HttpClient) {}

  onLogin(loginParams: LoginParams): Observable<LoginStatus> {
    return this.http
      .post<string>(`${this.USER_PATH}/login`, null, {
        params: {
          email: loginParams.email,
          password: loginParams.password
        },
        responseType: 'text' as 'json'
      })
      .pipe(
        map(res => {
          if (res.toLowerCase().includes('successful')) {
            return LoginStatus.SUCCESS;
          }
          return LoginStatus.UNAUTHORIZED;
        })
      );
  }

  onSignup(signupBody: SignupBody): Observable<string> {
    return this.http.post<string>(`${this.USER_PATH}/signup`, signupBody, {
      responseType: 'text' as 'json'
    });
  }
}
