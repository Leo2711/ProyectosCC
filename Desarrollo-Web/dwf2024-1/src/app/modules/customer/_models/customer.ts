import { CustomerImage } from "./customer-image";

export class Customer {
    address: string = "";
    customer_id: number = 0;
    image: CustomerImage = new CustomerImage();
    
    mail: string = "";
    name: string = "";
    region_id: number = 0;
    rfc: string = "";
    status: number = 0;
    surname: string = "";
}