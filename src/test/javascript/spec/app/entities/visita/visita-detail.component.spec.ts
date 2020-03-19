import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MaacBeaconApiTestModule } from '../../../test.module';
import { VisitaDetailComponent } from 'app/entities/visita/visita-detail.component';
import { Visita } from 'app/shared/model/visita.model';

describe('Component Tests', () => {
  describe('Visita Management Detail Component', () => {
    let comp: VisitaDetailComponent;
    let fixture: ComponentFixture<VisitaDetailComponent>;
    const route = ({ data: of({ visita: new Visita('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MaacBeaconApiTestModule],
        declarations: [VisitaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VisitaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VisitaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load visita on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.visita).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
