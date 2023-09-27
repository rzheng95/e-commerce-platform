import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  emailForm: FormGroup;
  emailControl: FormControl;

  constructor() {
    this.emailControl = new FormControl('example@example.com', [Validators.required, Validators.email]);
    this.emailForm = new FormGroup({
      email: this.emailControl
    });
  }
}