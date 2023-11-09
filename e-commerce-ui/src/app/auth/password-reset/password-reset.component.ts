import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { LoginParams } from 'src/app/util/login';
// import { LoginStatus } from 'src/app/util/login-status';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss']
})
export class PasswordResetComponent {
  pwResetFormGroup: FormGroup;
  showPassword = false;
  // arePasswordsMatching = false;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService
  ) {
    this.pwResetFormGroup = this.fb.group({
      password: ['', [Validators.required]],
      confirmPassword: ['', [Validators.required, this.passwordValidator]]
    });
  }

  passwordValidator(confirmPassword: string): any {
    const password = this.pwResetFormGroup.get('password')?.value;
    // const confirmPassword = this.pwResetFormGroup.get('confirmPassword')?.value;
    password === confirmPassword
      ? { passwordsMatched: true }
      : { passwordsMatched: false };
  }

  onPwReset(): void {
    // const [password, confirmPassword] = this.pwResetFormGroup.value;
    const password = this.pwResetFormGroup.get('password')?.value;
    const confirmPassword = this.pwResetFormGroup.get('confirmPassword')?.value;

    console.log(this.pwResetFormGroup.value, password === confirmPassword);

    // this.pwResetFormGroup.valid && password === confirmPassword
    //   ? (this.arePasswordsMatching = true)
    //   : (this.arePasswordsMatching = false);
  }
}
