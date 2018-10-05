import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderTracker } from 'app/shared/model/order-tracker.model';

type EntityResponseType = HttpResponse<IOrderTracker>;
type EntityArrayResponseType = HttpResponse<IOrderTracker[]>;

@Injectable({ providedIn: 'root' })
export class OrderTrackerService {
    private resourceUrl = SERVER_API_URL + 'api/order-trackers';

    constructor(private http: HttpClient) {}

    create(orderTracker: IOrderTracker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(orderTracker);
        return this.http
            .post<IOrderTracker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(orderTracker: IOrderTracker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(orderTracker);
        return this.http
            .put<IOrderTracker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IOrderTracker>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOrderTracker[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(orderTracker: IOrderTracker): IOrderTracker {
        const copy: IOrderTracker = Object.assign({}, orderTracker, {
            dateTime: orderTracker.dateTime != null && orderTracker.dateTime.isValid() ? orderTracker.dateTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateTime = res.body.dateTime != null ? moment(res.body.dateTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((orderTracker: IOrderTracker) => {
            orderTracker.dateTime = orderTracker.dateTime != null ? moment(orderTracker.dateTime) : null;
        });
        return res;
    }
}
