/* @author Mónica Miranda Mijangos 
  @author Eduardo Leónel Sánchez Velasco 
  Version: 1
  Fecha: 02/10/2023 */
  
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegionComponent } from '../region/components/region/region.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CustomerComponent } from './components/customer/customer.component';
import { CustomerImageComponent } from './components/customer-image/customer-image.component';
import { NgxPaginationModule } from 'ngx-pagination';
import {NgxPhotoEditorModule} from "ngx-photo-editor";
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    RegionComponent,
    CustomerComponent,
    CustomerImageComponent,    
  ],
  imports: [
    SharedModule,
    CommonModule,
    FormsModule,
    NgxPaginationModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
    NgxPhotoEditorModule,    
  ],
  exports: [
    CustomerComponent,
    RegionComponent
  ]
})
export class CustomerModule { }
