import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { IProduct, Product } from 'app/shared/model/product.model';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './products.component.html',
    styleUrls: ['products.css']
})
export class ProductsComponent implements AfterViewInit {
    activeCount: number;
    products: Product;
    constructor(private http: HttpClient, private router: Router) {}
    ngAfterViewInit() {
        this.http.get<IProduct>('api/products').subscribe(p => {
            this.products = p;
        });
    }
}
