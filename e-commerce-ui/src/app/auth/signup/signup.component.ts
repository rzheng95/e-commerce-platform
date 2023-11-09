import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SharedService } from 'src/app/shared/shared.service';
import { Role, SignupBody } from 'src/app/util/signup';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  signupFormGroup: FormGroup;
  showPassword = false;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private sharedService: SharedService
  ) {
    this.signupFormGroup = this.fb.group({
      username: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      confirmPassword: [
        '',
        [
          Validators.required,
          this.sharedService.matchValidator(
            'password',
            'confirmPassword',
            'Passwords do not match'
          )
        ]
      ]
    });
  }

  onSignup(): void {
    if (!this.signupFormGroup.valid) return;

    const { username, firstName, lastName, email, password } =
      this.signupFormGroup.value;

    const signupBody: SignupBody = {
      username,
      firstName,
      lastName,
      email,
      password,
      role: Role.CUSTOMER
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
