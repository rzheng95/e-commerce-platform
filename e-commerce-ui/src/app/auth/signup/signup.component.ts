import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SignupBody } from 'src/app/util/signup';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  signupForm: FormGroup;
  showPassword = false;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService
  ) {
    this.signupForm = this.fb.group({
      username: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
      // confirmPassword: ['', [Validators.required]]
    });
  }

  onSignup(): void {
    if (!this.signupForm.valid) return;

    const { username, firstName, lastName, email, password } = this.signupForm.value;

    const signupBody: SignupBody = {
      username,
      firstName,
      lastName,
      email,
      passwordHash: password
    };

    this.auth.onSignup(signupBody).subscribe({
      next: res => {
        console.log('Signup successful: ', res);
      },
      error: err => {
        console.log('Signup failed:', err.error);
      }
    });
  }
}
