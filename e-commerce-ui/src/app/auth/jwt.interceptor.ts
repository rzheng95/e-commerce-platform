import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    const jwtToken = localStorage.getItem('jwtToken');
    if (jwtToken) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${jwtToken}`
        }
      });
    }
    return next.handle(request);
  }
}
