import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegionComponent } from './modules/region/components/region/region.component';
import { DatosUnamComponent } from './modules/consume-api/datos-unam/datos-unam.component';
import { CustomerComponent } from './modules/customer/components/customer/customer.component';
import { CustomerImageComponent } from './modules/customer/components/customer-image/customer-image.component';
import { ProductComponent } from './modules/product/components/product/product.component';
import { CategoryComponent } from './modules/category/components/category/category.component';
import { ProductImageComponent } from './modules/product/components/product-image/product-image.component';
import { InvoiceComponent } from './modules/invoice/components/invoice/invoice.component';
import { CartComponent } from './modules/invoice/components/cart/cart.component';
import { InvoiceListComponent } from './modules/invoice/components/invoice-list/invoice-list.component';

const routes: Routes = [
  { path: "", component: ProductComponent },
  { path: "category", component: CategoryComponent },
  { path: "region", component: RegionComponent },
  { path: "consume-api", component: DatosUnamComponent },
  { path: "customer", component: CustomerComponent },
  { path: "customer/:rfc", component: CustomerImageComponent },
  { path: "product", component: ProductComponent },
  { path: "product/:gtin", component: ProductImageComponent },
  { path: "cart/:rfc", component: CartComponent },
  { path: "invoice", component: InvoiceListComponent },
  { path: "invoice/:id", component: InvoiceComponent },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }