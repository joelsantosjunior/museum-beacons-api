import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { VisitaService } from 'app/entities/visita/visita.service';
import { IVisita, Visita } from 'app/shared/model/visita.model';

describe('Service Tests', () => {
  describe('Visita Service', () => {
    let injector: TestBed;
    let service: VisitaService;
    let httpMock: HttpTestingController;
    let elemDefault: IVisita;
    let expectedResult: IVisita | IVisita[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VisitaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Visita('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Visita', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );

        service.create(new Visita()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Visita', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            email: 'BBBBBB',
            telefone: 'BBBBBB',
            cep: 'BBBBBB',
            endereco: 'BBBBBB',
            bairro: 'BBBBBB',
            complemento: 'BBBBBB',
            idCelular: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Visita', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            email: 'BBBBBB',
            telefone: 'BBBBBB',
            cep: 'BBBBBB',
            endereco: 'BBBBBB',
            bairro: 'BBBBBB',
            complemento: 'BBBBBB',
            idCelular: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Visita', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
