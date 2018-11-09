import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd } from '@angular/router';
import { MessagingService } from '../../shared/service/messaging.service';
import { Principal } from '../../core/auth/principal.service';
import { JhiTrackerService } from 'app/core';
import {
    trigger,
    state,
    style,
    animate,
    transition,
    query,
  } from '@angular/animations';
import { Title } from '@angular/platform-browser';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html',
    animations: [
        trigger('routerAnimation', [
          transition('* => listing, user-cart => user-orders, my-account => user-orders, my-account => user-cart, * => dashboard, dashboard-order-list => dashboard-orders, order-tracker => user-orders, order-tracker => dashboard-order-list', [
           // Initial state of old route
           query(':leave',
           style({
             position: 'absolute',
             width: '100%',
             transform: 'translateX(100%)',
             height: '100vh'
           }),
           {optional: true}),
            // Initial state of new route
            query(':enter',
              style({
              //  opacity: 0,
                position: 'absolute',
                width: '100%',
                transform: 'translateX(-100%)',
                height: '100vh'
              }),
              {optional: true}),

            // move page off screen right on leave
            query(':leave',
              animate('160ms ease',
                style({
                  opacity: 0,
                  position: 'absolute',
                  width: '100%',
                  transform: 'translateX(100%)',
                  height: '100vh'
                })
              ),
            {optional: true}),

            // move page in screen from left to right
            query(':enter',
              animate('160ms ease',
                style({
                  opacity: 1,
                  transform: 'translateX(0%)'
                })
              ),
            {optional: true}),
          ]), transition('* => my-account, listing => user-cart, user-orders => user-cart, listing => user-orders, dashboard => dashboard-products, dashboard => dashboard-orders,dashboard-orders => dashboard-order-list, dashboard => inventory-logs, user-orders => order-tracker, dashboard-order-list => order-tracker', [

            // Initial state of old route
            query(':leave',
              style({
                  opacity: 0,
                position: 'absolute',
                width: '200%',
                transform: 'translateX(-100%)',
                height: '100vh'
              }),
              {optional: true}),
            // Initial state of new route
            query(':enter',
              style({
                //  opacity: 0,
                position: 'absolute',
                width: '200%',
                transform: 'translateX(100%)',
                height: '100vh'
              }),
              {optional: true}),

            // move page off screen right on leave
            query(':leave',
              animate('160ms ease',
                style({
                    opacity: 0,
                  position: 'absolute',
                  width: '100%',
                  transform: 'translateX(-100%)',
                  height: '100vh'
                })
              ),
            {optional: true}),

            // move page in screen from left to right
            query(':enter',
              animate('160ms ease',
                style({
                  opacity: 1,
                  transform: 'translateX(0%)',
                  height: '100vh'
                })
              ),
            {optional: true}),
          ])
        ])
      ]
})
export class JhiMainComponent implements OnInit {
    constructor(private trackerService: JhiTrackerService,private principal: Principal, private messagingService: MessagingService, private titleService: Title, private router: Router) {}

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'deliveryApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    ngOnInit() {
        this.principal.identity(true).then(account => {
            this.messagingService.init();
            this.trackerService.connect();
            this.trackerService.receive().subscribe(res => {
                alert('new order received - main');

            });
             this.trackerService.subscribeOrder();
            if (account !== null && account.authorities !== null && account.authorities.indexOf('ROLE_ADMIN') !== -1) {
                this.trackerService.subscribe();
            }
        });

        this.router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.titleService.setTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
        });
    }

    getRouteAnimation(outlet) {
        return outlet.activatedRouteData.animation;
      }
}
