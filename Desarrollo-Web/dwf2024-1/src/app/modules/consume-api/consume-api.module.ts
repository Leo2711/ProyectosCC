import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DatosUnamComponent } from './datos-unam/datos-unam.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms'


@NgModule({
  declarations: [
    DatosUnamComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule, 
    ReactiveFormsModule,
  ],
  exports:[
    DatosUnamComponent
  ]
})
export class ConsumeApiModule { }
