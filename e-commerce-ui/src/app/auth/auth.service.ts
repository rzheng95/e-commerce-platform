import {
  HttpClient,
  HttpResponse,
  HttpResponseBase
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginParams } from '../util/login';
import { LoginStatus } from '../util/login-status';
import { SignupBody } from '../util/signup';

export interface MyHttpResponse extends HttpResponseBase {
  body: string | null;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly USER_PATH = `${environment.backendUrl}/api/v1/users`;
  public static readonly BEARER_PREFIX = 'Bearer ';
  public static readonly JWT_LOCAL_STORAGE = 'jwt';
  public static readonly AUTHORIZATION_HEADER = 'Authorization';

  constructor(private http: HttpClient) {}

  onLogin(loginParams: LoginParams): Observable<LoginStatus> {
    return this.http
      .post(`${this.USER_PATH}/login`, loginParams, {
        observe: 'response',
        responseType: 'text'
      })
      .pipe(
        map((res: HttpResponse<string>) => {
          const token = res.headers.get(AuthService.AUTHORIZATION_HEADER);
          if (token && res.body?.toLowerCase().includes('successful')) {
            localStorage.setItem(
              AuthService.JWT_LOCAL_STORAGE,
              token.replace(AuthService.BEARER_PREFIX, '')
            );
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
