import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MaacBeaconApiSharedModule } from 'app/shared/shared.module';
import { VisitaComponent } from './visita.component';
import { VisitaDetailComponent } from './visita-detail.component';
import { VisitaUpdateComponent } from './visita-update.component';
import { VisitaDeleteDialogComponent } from './visita-delete-dialog.component';
import { visitaRoute } from './visita.route';

@NgModule({
  imports: [MaacBeaconApiSharedModule, RouterModule.forChild(visitaRoute)],
  declarations: [VisitaComponent, VisitaDetailComponent, VisitaUpdateComponent, VisitaDeleteDialogComponent],
  entryComponents: [VisitaDeleteDialogComponent]
})
export class MaacBeaconApiVisitaModule {}
