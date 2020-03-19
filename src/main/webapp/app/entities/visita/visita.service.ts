import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVisita } from 'app/shared/model/visita.model';

type EntityResponseType = HttpResponse<IVisita>;
type EntityArrayResponseType = HttpResponse<IVisita[]>;

@Injectable({ providedIn: 'root' })
export class VisitaService {
  public resourceUrl = SERVER_API_URL + 'api/visitas';

  constructor(protected http: HttpClient) {}

  create(visita: IVisita): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(visita);
    return this.http
      .post<IVisita>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(visita: IVisita): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(visita);
    return this.http
      .put<IVisita>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IVisita>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVisita[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(visita: IVisita): IVisita {
    const copy: IVisita = Object.assign({}, visita, {
      data: visita.data && visita.data.isValid() ? visita.data.toJSON() : undefined
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
      res.body.forEach((visita: IVisita) => {
        visita.data = visita.data ? moment(visita.data) : undefined;
      });
    }
    return res;
  }
}
