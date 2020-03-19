import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MaacBeaconApiSharedModule } from 'app/shared/shared.module';
import { BeaconComponent } from './beacon.component';
import { BeaconDetailComponent } from './beacon-detail.component';
import { BeaconUpdateComponent } from './beacon-update.component';
import { BeaconDeleteDialogComponent } from './beacon-delete-dialog.component';
import { beaconRoute } from './beacon.route';

@NgModule({
  imports: [MaacBeaconApiSharedModule, RouterModule.forChild(beaconRoute)],
  declarations: [BeaconComponent, BeaconDetailComponent, BeaconUpdateComponent, BeaconDeleteDialogComponent],
  entryComponents: [BeaconDeleteDialogComponent]
})
export class MaacBeaconApiBeaconModule {}
