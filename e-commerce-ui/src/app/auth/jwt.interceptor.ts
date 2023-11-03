import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    const jwtToken = localStorage.getItem(AuthService.JWT_LOCAL_STORAGE);
    if (jwtToken) {
      request = request.clone({
        setHeaders: {
          Authorization: `${AuthService.BEARER_PREFIX} ${jwtToken}`
        }
      });
    }
    return next.handle(request);
  }
}
