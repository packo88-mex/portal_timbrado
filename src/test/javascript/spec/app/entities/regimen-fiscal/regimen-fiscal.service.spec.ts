/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import RegimenFiscalService from '@/entities/regimen-fiscal/regimen-fiscal.service';
import { RegimenFiscal } from '@/shared/model/regimen-fiscal.model';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('RegimenFiscal Service', () => {
    let service: RegimenFiscalService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new RegimenFiscalService();
      currentDate = new Date();
      elemDefault = new RegimenFiscal(123, currentDate, 'AAAAAAA', Estatus.Activo, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: dayjs(currentDate).format(DATE_FORMAT),
            fechaModificacion: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a RegimenFiscal', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            fecha: dayjs(currentDate).format(DATE_FORMAT),
            fechaModificacion: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaModificacion: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a RegimenFiscal', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a RegimenFiscal', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: dayjs(currentDate).format(DATE_FORMAT),
            regimenFiscal: 'BBBBBB',
            estatus: 'BBBBBB',
            fechaModificacion: dayjs(currentDate).format(DATE_FORMAT),
            usuario: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaModificacion: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a RegimenFiscal', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a RegimenFiscal', async () => {
        const patchObject = Object.assign(
          {
            regimenFiscal: 'BBBBBB',
            estatus: 'BBBBBB',
          },
          new RegimenFiscal()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaModificacion: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a RegimenFiscal', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of RegimenFiscal', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: dayjs(currentDate).format(DATE_FORMAT),
            regimenFiscal: 'BBBBBB',
            estatus: 'BBBBBB',
            fechaModificacion: dayjs(currentDate).format(DATE_FORMAT),
            usuario: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaModificacion: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of RegimenFiscal', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a RegimenFiscal', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a RegimenFiscal', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
