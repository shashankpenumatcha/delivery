import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICartItems } from 'app/shared/model/cart-items.model';

type EntityResponseType = HttpResponse<ICartItems>;
type EntityArrayResponseType = HttpResponse<ICartItems[]>;

@Injectable({ providedIn: 'root' })
export class CartItemsService {
    private resourceUrl = SERVER_API_URL + 'api/cart-items';

    constructor(private http: HttpClient) {}

    create(cartItems: ICartItems): Observable<EntityResponseType> {
        return this.http.post<ICartItems>(this.resourceUrl, cartItems, { observe: 'response' });
    }

    update(cartItems: ICartItems): Observable<EntityResponseType> {
        return this.http.put<ICartItems>(this.resourceUrl, cartItems, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICartItems>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICartItems[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
