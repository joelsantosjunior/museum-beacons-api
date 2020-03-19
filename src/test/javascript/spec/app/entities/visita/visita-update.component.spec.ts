import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MaacBeaconApiTestModule } from '../../../test.module';
import { VisitaUpdateComponent } from 'app/entities/visita/visita-update.component';
import { VisitaService } from 'app/entities/visita/visita.service';
import { Visita } from 'app/shared/model/visita.model';

describe('Component Tests', () => {
  describe('Visita Management Update Component', () => {
    let comp: VisitaUpdateComponent;
    let fixture: ComponentFixture<VisitaUpdateComponent>;
    let service: VisitaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MaacBeaconApiTestModule],
        declarations: [VisitaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VisitaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VisitaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VisitaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Visita('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Visita();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
