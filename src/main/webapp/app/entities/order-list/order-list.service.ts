import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderList } from 'app/shared/model/order-list.model';

type EntityResponseType = HttpResponse<IOrderList>;
type EntityArrayResponseType = HttpResponse<IOrderList[]>;

@Injectable({ providedIn: 'root' })
export class OrderListService {
    private resourceUrl = SERVER_API_URL + 'api/order-lists';

    constructor(private http: HttpClient) {}

    create(orderList: IOrderList): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(orderList);
        return this.http
            .post<IOrderList>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(orderList: IOrderList): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(orderList);
        return this.http
            .put<IOrderList>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IOrderList>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOrderList[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(orderList: IOrderList): IOrderList {
        const copy: IOrderList = Object.assign({}, orderList, {
            lastUpdated: orderList.lastUpdated != null && orderList.lastUpdated.isValid() ? orderList.lastUpdated.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.lastUpdated = res.body.lastUpdated != null ? moment(res.body.lastUpdated) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((orderList: IOrderList) => {
            orderList.lastUpdated = orderList.lastUpdated != null ? moment(orderList.lastUpdated) : null;
        });
        return res;
    }
}
