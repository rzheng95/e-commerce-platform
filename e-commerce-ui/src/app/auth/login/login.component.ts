import { HttpStatusCode } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginParams } from 'src/app/util/login';
import { LoginStatus } from 'src/app/util/login-status';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  showPassword = false;
  loginFailed = false;

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
          this.loginFailed = false;
        } else if (loginStatus === LoginStatus.UNAUTHORIZED) {
          console.log('Login failed: unauthorized');
          this.loginForm.reset({ email: '', password: '' });
          this.loginFailed = true;
        }
      },
      error: err => {
        if (err.status === HttpStatusCode.Unauthorized) {
          console.log('Login failed: in error');
          this.loginForm.reset({ email: '', password: '' });
          this.loginFailed = true;
        }
      }
    });
  }

  test(): void {
    this.auth.test().subscribe(res => console.log(res));
  }
}
