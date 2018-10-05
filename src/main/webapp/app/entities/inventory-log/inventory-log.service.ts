import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInventoryLog } from 'app/shared/model/inventory-log.model';

type EntityResponseType = HttpResponse<IInventoryLog>;
type EntityArrayResponseType = HttpResponse<IInventoryLog[]>;

@Injectable({ providedIn: 'root' })
export class InventoryLogService {
    private resourceUrl = SERVER_API_URL + 'api/inventory-logs';

    constructor(private http: HttpClient) {}

    create(inventoryLog: IInventoryLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(inventoryLog);
        return this.http
            .post<IInventoryLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(inventoryLog: IInventoryLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(inventoryLog);
        return this.http
            .put<IInventoryLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IInventoryLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IInventoryLog[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(inventoryLog: IInventoryLog): IInventoryLog {
        const copy: IInventoryLog = Object.assign({}, inventoryLog, {
            date: inventoryLog.date != null && inventoryLog.date.isValid() ? inventoryLog.date.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((inventoryLog: IInventoryLog) => {
            inventoryLog.date = inventoryLog.date != null ? moment(inventoryLog.date) : null;
        });
        return res;
    }
}
