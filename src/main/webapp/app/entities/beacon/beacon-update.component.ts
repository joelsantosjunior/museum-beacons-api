import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBeacon, Beacon } from 'app/shared/model/beacon.model';
import { BeaconService } from './beacon.service';

@Component({
  selector: 'jhi-beacon-update',
  templateUrl: './beacon-update.component.html'
})
export class BeaconUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    local: [null, [Validators.required]],
    idBeacon: [null, [Validators.required]],
    tipoConteudo: [],
    conteudo: [null, [Validators.maxLength(500)]],
    legenda: [null, [Validators.maxLength(250)]]
  });

  constructor(protected beaconService: BeaconService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beacon }) => {
      this.updateForm(beacon);
    });
  }

  updateForm(beacon: IBeacon): void {
    this.editForm.patchValue({
      id: beacon.id,
      local: beacon.local,
      idBeacon: beacon.idBeacon,
      tipoConteudo: beacon.tipoConteudo,
      conteudo: beacon.conteudo,
      legenda: beacon.legenda
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const beacon = this.createFromForm();
    if (beacon.id !== undefined) {
      this.subscribeToSaveResponse(this.beaconService.update(beacon));
    } else {
      this.subscribeToSaveResponse(this.beaconService.create(beacon));
    }
  }

  private createFromForm(): IBeacon {
    return {
      ...new Beacon(),
      id: this.editForm.get(['id'])!.value,
      local: this.editForm.get(['local'])!.value,
      idBeacon: this.editForm.get(['idBeacon'])!.value,
      tipoConteudo: this.editForm.get(['tipoConteudo'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      legenda: this.editForm.get(['legenda'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeacon>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
