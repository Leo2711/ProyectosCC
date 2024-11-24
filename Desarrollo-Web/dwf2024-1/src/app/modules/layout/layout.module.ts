import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './footer/footer.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { NavbarComponent } from './navbar/navbar.component';
import { RouterModule } from '@angular/router';
import { RouterNavbarComponent } from './router-navbar/router-navbar.component';

@NgModule({
  declarations: [
    FooterComponent,
    SidebarComponent,
    NavbarComponent,
    RouterNavbarComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ], 
  exports: [
    FooterComponent, 
    SidebarComponent, 
    NavbarComponent,
    RouterNavbarComponent
  ]
})
export class LayoutModule { }
