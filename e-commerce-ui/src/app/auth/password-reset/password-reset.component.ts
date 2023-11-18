import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { SharedService } from 'src/app/shared/shared.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss']
})
export class PasswordResetComponent {
  passwordResetFormGroup: FormGroup;
  showPassword = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private SharedService: SharedService
  ) {
    this.passwordResetFormGroup = this.fb.group({
      password: ['', [Validators.required]],
      confirmPassword: [
        '',
        [
          Validators.required,
          this.SharedService.matchValidator(
            'password',
            'confirmPassword',
            'Passwords do not match'
          )
        ]
      ]
    });
  }

  onPasswordReset(): void {
    if (this.passwordResetFormGroup.valid) {
      // make a http call to reset password endpoint
    }

    if (this.passwordResetFormGroup.invalid) {
      this.errorMessage = this.SharedService.getFormErrors(
        this.passwordResetFormGroup
      );
      return;
    }

    this.errorMessage = '';
  }
}
