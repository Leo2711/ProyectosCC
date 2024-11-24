import { NgModule } from '@angular/core';
import { SortDirective } from 'src/app/directive/sort.directive';

@NgModule({
  declarations: [
    SortDirective,
  ],
  exports: [
    SortDirective,
  ],
})
export class SharedModule { }