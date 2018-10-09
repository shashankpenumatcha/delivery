import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-listing',
    templateUrl: './listing.component.html',
    styleUrls: ['listing.css']
})
export class ListingComponent implements OnInit {
    products: any[];
    loading = true;
    constructor(private http: HttpClient) {}
    ngOnInit() {
        this.http.get<any>('api/activeProducts', { observe: 'response' }).subscribe(
            (res: any) => {
                if (res.body !== undefined && res.body.length > 0) {
                    this.products = res.body;
                    this.loading = false;
                }
            },
            (error: any) => {
                this.loading = false;
            }
        );
    }
}
