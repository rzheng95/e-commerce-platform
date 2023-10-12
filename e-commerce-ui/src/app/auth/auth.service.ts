import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  onLogin(email: string) {
    this.http
      .get(`http://localhost:8080/api/v1/users/check-email?email=${email}`)
      .subscribe(
        (data) => {
          console.log(data);
        },
        (error) => {}
      );
  }
}
