/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TipoCfdiUpdateComponent from '@/entities/tipo-cfdi/tipo-cfdi-update.vue';
import TipoCfdiClass from '@/entities/tipo-cfdi/tipo-cfdi-update.component';
import TipoCfdiService from '@/entities/tipo-cfdi/tipo-cfdi.service';

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
  describe('TipoCfdi Management Update Component', () => {
    let wrapper: Wrapper<TipoCfdiClass>;
    let comp: TipoCfdiClass;
    let tipoCfdiServiceStub: SinonStubbedInstance<TipoCfdiService>;

    beforeEach(() => {
      tipoCfdiServiceStub = sinon.createStubInstance<TipoCfdiService>(TipoCfdiService);

      wrapper = shallowMount<TipoCfdiClass>(TipoCfdiUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          tipoCfdiService: () => tipoCfdiServiceStub,
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
        comp.tipoCfdi = entity;
        tipoCfdiServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tipoCfdiServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.tipoCfdi = entity;
        tipoCfdiServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tipoCfdiServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTipoCfdi = { id: 123 };
        tipoCfdiServiceStub.find.resolves(foundTipoCfdi);
        tipoCfdiServiceStub.retrieve.resolves([foundTipoCfdi]);

        // WHEN
        comp.beforeRouteEnter({ params: { tipoCfdiId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tipoCfdi).toBe(foundTipoCfdi);
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
