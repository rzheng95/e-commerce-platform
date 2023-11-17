import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent {
  forgotFormGroup: FormGroup;
  showSuccessMessage = false;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService
  ) {
    this.forgotFormGroup = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSendResetEmail(): void {
    if (this.forgotFormGroup.valid) {
      this.showSuccessMessage = true;
    }

    // send reset email
  }
}
