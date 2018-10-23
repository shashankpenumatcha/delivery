import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['dashboard.css']
})
export class DashboardComponent implements AfterViewInit {
    activeCount: number;
    constructor(private http: HttpClient, private router: Router) {}
    ngAfterViewInit() {
        this.http.get<any>('api/dashboard/orders/active/count').subscribe(res => {
            this.activeCount = res;
        });
    }
}
