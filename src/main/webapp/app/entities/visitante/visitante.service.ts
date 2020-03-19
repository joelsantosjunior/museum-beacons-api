import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVisitante } from 'app/shared/model/visitante.model';

type EntityResponseType = HttpResponse<IVisitante>;
type EntityArrayResponseType = HttpResponse<IVisitante[]>;

@Injectable({ providedIn: 'root' })
export class VisitanteService {
  public resourceUrl = SERVER_API_URL + 'api/visitantes';

  constructor(protected http: HttpClient) {}

  create(visitante: IVisitante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(visitante);
    return this.http
      .post<IVisitante>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(visitante: IVisitante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(visitante);
    return this.http
      .put<IVisitante>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IVisitante>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVisitante[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(visitante: IVisitante): IVisitante {
    const copy: IVisitante = Object.assign({}, visitante, {
      data: visitante.data && visitante.data.isValid() ? visitante.data.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((visitante: IVisitante) => {
        visitante.data = visitante.data ? moment(visitante.data) : undefined;
      });
    }
    return res;
  }
}
