import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVisita, Visita } from 'app/shared/model/visita.model';
import { VisitaService } from './visita.service';
import { VisitaComponent } from './visita.component';
import { VisitaDetailComponent } from './visita-detail.component';
import { VisitaUpdateComponent } from './visita-update.component';

@Injectable({ providedIn: 'root' })
export class VisitaResolve implements Resolve<IVisita> {
  constructor(private service: VisitaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVisita> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((visita: HttpResponse<Visita>) => {
          if (visita.body) {
            return of(visita.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Visita());
  }
}

export const visitaRoute: Routes = [
  {
    path: '',
    component: VisitaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'maacBeaconApiApp.visita.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VisitaDetailComponent,
    resolve: {
      visita: VisitaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'maacBeaconApiApp.visita.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VisitaUpdateComponent,
    resolve: {
      visita: VisitaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'maacBeaconApiApp.visita.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VisitaUpdateComponent,
    resolve: {
      visita: VisitaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'maacBeaconApiApp.visita.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
