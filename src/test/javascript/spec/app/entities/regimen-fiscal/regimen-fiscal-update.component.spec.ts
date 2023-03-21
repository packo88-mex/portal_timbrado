/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RegimenFiscalUpdateComponent from '@/entities/regimen-fiscal/regimen-fiscal-update.vue';
import RegimenFiscalClass from '@/entities/regimen-fiscal/regimen-fiscal-update.component';
import RegimenFiscalService from '@/entities/regimen-fiscal/regimen-fiscal.service';

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
  describe('RegimenFiscal Management Update Component', () => {
    let wrapper: Wrapper<RegimenFiscalClass>;
    let comp: RegimenFiscalClass;
    let regimenFiscalServiceStub: SinonStubbedInstance<RegimenFiscalService>;

    beforeEach(() => {
      regimenFiscalServiceStub = sinon.createStubInstance<RegimenFiscalService>(RegimenFiscalService);

      wrapper = shallowMount<RegimenFiscalClass>(RegimenFiscalUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          regimenFiscalService: () => regimenFiscalServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.regimenFiscal = entity;
        regimenFiscalServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(regimenFiscalServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.regimenFiscal = entity;
        regimenFiscalServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(regimenFiscalServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRegimenFiscal = { id: 123 };
        regimenFiscalServiceStub.find.resolves(foundRegimenFiscal);
        regimenFiscalServiceStub.retrieve.resolves([foundRegimenFiscal]);

        // WHEN
        comp.beforeRouteEnter({ params: { regimenFiscalId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.regimenFiscal).toBe(foundRegimenFiscal);
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
