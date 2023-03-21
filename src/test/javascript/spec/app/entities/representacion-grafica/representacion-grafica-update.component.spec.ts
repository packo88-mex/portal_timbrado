/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RepresentacionGraficaUpdateComponent from '@/entities/representacion-grafica/representacion-grafica-update.vue';
import RepresentacionGraficaClass from '@/entities/representacion-grafica/representacion-grafica-update.component';
import RepresentacionGraficaService from '@/entities/representacion-grafica/representacion-grafica.service';

import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('RepresentacionGrafica Management Update Component', () => {
    let wrapper: Wrapper<RepresentacionGraficaClass>;
    let comp: RepresentacionGraficaClass;
    let representacionGraficaServiceStub: SinonStubbedInstance<RepresentacionGraficaService>;

    beforeEach(() => {
      representacionGraficaServiceStub = sinon.createStubInstance<RepresentacionGraficaService>(RepresentacionGraficaService);

      wrapper = shallowMount<RepresentacionGraficaClass>(RepresentacionGraficaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          representacionGraficaService: () => representacionGraficaServiceStub,
          alertService: () => new AlertService(),

          tipoReciboService: () =>
            sinon.createStubInstance<TipoReciboService>(TipoReciboService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.representacionGrafica = entity;
        representacionGraficaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(representacionGraficaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.representacionGrafica = entity;
        representacionGraficaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(representacionGraficaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRepresentacionGrafica = { id: 123 };
        representacionGraficaServiceStub.find.resolves(foundRepresentacionGrafica);
        representacionGraficaServiceStub.retrieve.resolves([foundRepresentacionGrafica]);

        // WHEN
        comp.beforeRouteEnter({ params: { representacionGraficaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.representacionGrafica).toBe(foundRepresentacionGrafica);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
