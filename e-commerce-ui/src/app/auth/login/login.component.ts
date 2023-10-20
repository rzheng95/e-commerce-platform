import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { LoginParams } from 'src/app/util/login';
import { LoginStatus } from 'src/app/util/login-status';
import { HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  showPassword = false;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  onLogin(): void {
    if (!this.loginForm.valid) {
      return;
    }

    const email = this.loginForm.get('email')?.value;
    const password = this.loginForm.get('password')?.value;

    const loginParams: LoginParams = {
      email,
      password
    };

    this.auth.onLogin(loginParams).subscribe({
      next: (loginStatus: LoginStatus) => {
        if (loginStatus === LoginStatus.SUCCESS) {
          console.log('Login successful');
        } else if (loginStatus === LoginStatus.UNAUTHORIZED) {
          console.log('Login failed');
        }
      },
      error: err => {
        if (err.status === HttpStatusCode.Unauthorized) {
          console.log('Login failed');
        }
      }
    });
  }
}
