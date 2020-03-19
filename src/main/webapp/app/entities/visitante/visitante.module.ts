import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MaacBeaconApiSharedModule } from 'app/shared/shared.module';
import { VisitanteComponent } from './visitante.component';
import { VisitanteDetailComponent } from './visitante-detail.component';
import { VisitanteUpdateComponent } from './visitante-update.component';
import { VisitanteDeleteDialogComponent } from './visitante-delete-dialog.component';
import { visitanteRoute } from './visitante.route';

@NgModule({
  imports: [MaacBeaconApiSharedModule, RouterModule.forChild(visitanteRoute)],
  declarations: [VisitanteComponent, VisitanteDetailComponent, VisitanteUpdateComponent, VisitanteDeleteDialogComponent],
  entryComponents: [VisitanteDeleteDialogComponent]
})
export class MaacBeaconApiVisitanteModule {}
