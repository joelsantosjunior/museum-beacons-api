import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVisitante, Visitante } from 'app/shared/model/visitante.model';
import { VisitanteService } from './visitante.service';
import { VisitanteComponent } from './visitante.component';
import { VisitanteDetailComponent } from './visitante-detail.component';
import { VisitanteUpdateComponent } from './visitante-update.component';

@Injectable({ providedIn: 'root' })
export class VisitanteResolve implements Resolve<IVisitante> {
  constructor(private service: VisitanteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVisitante> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((visitante: HttpResponse<Visitante>) => {
          if (visitante.body) {
            return of(visitante.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Visitante());
  }
}

export const visitanteRoute: Routes = [
  {
    path: '',
    component: VisitanteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'maacBeaconApiApp.visitante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VisitanteDetailComponent,
    resolve: {
      visitante: VisitanteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'maacBeaconApiApp.visitante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VisitanteUpdateComponent,
    resolve: {
      visitante: VisitanteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'maacBeaconApiApp.visitante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VisitanteUpdateComponent,
    resolve: {
      visitante: VisitanteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'maacBeaconApiApp.visitante.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
