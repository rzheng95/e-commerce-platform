import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

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

        return control1 && control2 && control1 === control2
          ? null
          : { isMatch: mismatchErrorMessage };
      }
      return null;
    };
  }
}
