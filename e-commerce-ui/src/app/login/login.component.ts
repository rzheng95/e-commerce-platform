import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  emailForm: FormGroup;
  emailControl: FormControl;
  errorMessage: string = '';

  constructor(private http: HttpClient) {
    this.emailControl = new FormControl('example@example.com', [
      Validators.required,
      Validators.email,
    ]);
    this.emailForm = new FormGroup({
      email: this.emailControl,
    });
  }

  onLogin() {
    this.http
      .get(
        `http://localhost:8080/api/v1/users/check-email?email=${this.emailForm.value.email}`
      )
      .subscribe(
        (data) => {
          console.log(data, this.emailForm.value);
          this.errorMessage = '';
        },
        (error) => {
          this.errorMessage = error.message;
        }
      );
  }
}
