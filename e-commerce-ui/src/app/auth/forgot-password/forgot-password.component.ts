import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SharedService } from 'src/app/shared/shared.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent {
  forgotFormGroup: FormGroup;
  showSuccessMessage = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private sharedService: SharedService
  ) {
    this.forgotFormGroup = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSendResetEmail(): void {
    if (this.forgotFormGroup.valid) {
      this.showSuccessMessage = true;
      // send reset email
    }

    if (this.forgotFormGroup.invalid) {
      this.errorMessage = this.sharedService.getFormErrors(
        this.forgotFormGroup
      );
      return;
    }

    this.errorMessage = '';
  }
}
