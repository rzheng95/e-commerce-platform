import { HttpStatusCode } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SharedService } from 'src/app/shared/shared.service';
import { LoginParams } from 'src/app/util/login';
import { LoginStatus } from 'src/app/util/login-status';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginFormGroup: FormGroup;
  showPassword = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private sharedService: SharedService
  ) {
    this.loginFormGroup = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  onLogin(): void {
    if (this.loginFormGroup.invalid) {
      this.errorMessage = this.sharedService.getFormErrors(this.loginFormGroup);
      return;
    }
    this.errorMessage = '';

    const email = this.loginFormGroup.get('email')?.value;
    const password = this.loginFormGroup.get('password')?.value;

    const loginParams: LoginParams = {
      email,
      password
    };

    this.authService.onLogin(loginParams).subscribe({
      next: (loginStatus: LoginStatus) => {
        if (loginStatus === LoginStatus.SUCCESS) {
          console.log('Login successful');
        } else if (loginStatus === LoginStatus.UNAUTHORIZED) {
          console.log('Login failed: unauthorized');
          this.loginFormGroup.reset({ email: '', password: '' });
          this.errorMessage = 'Invalid email or password';
        }
      },
      error: err => {
        if (err.status === HttpStatusCode.Unauthorized) {
          console.log('Login failed: in error');
          this.loginFormGroup.reset({ email: '', password: '' });
          this.errorMessage = 'Invalid email or password';
        }
      }
    });
  }
}
