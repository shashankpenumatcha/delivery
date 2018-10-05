import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderItems } from 'app/shared/model/order-items.model';

type EntityResponseType = HttpResponse<IOrderItems>;
type EntityArrayResponseType = HttpResponse<IOrderItems[]>;

@Injectable({ providedIn: 'root' })
export class OrderItemsService {
    private resourceUrl = SERVER_API_URL + 'api/order-items';

    constructor(private http: HttpClient) {}

    create(orderItems: IOrderItems): Observable<EntityResponseType> {
        return this.http.post<IOrderItems>(this.resourceUrl, orderItems, { observe: 'response' });
    }

    update(orderItems: IOrderItems): Observable<EntityResponseType> {
        return this.http.put<IOrderItems>(this.resourceUrl, orderItems, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOrderItems>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOrderItems[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
