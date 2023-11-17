import { Injectable } from '@angular/core';
import {
  AbstractControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn
} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  matchValidator(
    controlName1: string,
    controlName2: string,
    mismatchErrorMessage: string
  ): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (control.parent) {
        const control1 = control.parent.get(controlName1)?.value;
        const control2 = control.parent.get(controlName2)?.value;

        console.log(control1, control2);

        return control1 && control2 && control1 === control2
          ? null
          : { isMatch: mismatchErrorMessage };
      }
      return null;
    };
  }

  capitalizeFirstLetter(str: string): string {
    if (str.length === 0) {
      return str;
    }

    return str.charAt(0).toUpperCase() + str.slice(1);
  }

  getFormErrors(formGroup: FormGroup): string {
    let errorMessage = '';

    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      if (control && control.errors) {
        Object.keys(control.errors).forEach(errorKey => {
          console.log(control.errors);

          if (errorKey === 'required') {
            errorMessage = `${this.capitalizeFirstLetter(key)} is ${errorKey}`;
          } else if (errorKey === 'email') {
            errorMessage = `Invalid ${errorKey}`;
          } else if (errorKey === 'isMatch' && control.errors) {
            errorMessage = control.errors['isMatch'];
          }
        });
      }
    });

    return errorMessage;
  }
}
