import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'visitante',
        loadChildren: () => import('./visitante/visitante.module').then(m => m.MaacBeaconApiVisitanteModule)
      },
      {
        path: 'visita',
        loadChildren: () => import('./visita/visita.module').then(m => m.MaacBeaconApiVisitaModule)
      },
      {
        path: 'beacon',
        loadChildren: () => import('./beacon/beacon.module').then(m => m.MaacBeaconApiBeaconModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MaacBeaconApiEntityModule {}
