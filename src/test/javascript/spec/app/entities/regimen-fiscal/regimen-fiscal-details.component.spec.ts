/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RegimenFiscalDetailComponent from '@/entities/regimen-fiscal/regimen-fiscal-details.vue';
import RegimenFiscalClass from '@/entities/regimen-fiscal/regimen-fiscal-details.component';
import RegimenFiscalService from '@/entities/regimen-fiscal/regimen-fiscal.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RegimenFiscal Management Detail Component', () => {
    let wrapper: Wrapper<RegimenFiscalClass>;
    let comp: RegimenFiscalClass;
    let regimenFiscalServiceStub: SinonStubbedInstance<RegimenFiscalService>;

    beforeEach(() => {
      regimenFiscalServiceStub = sinon.createStubInstance<RegimenFiscalService>(RegimenFiscalService);

      wrapper = shallowMount<RegimenFiscalClass>(RegimenFiscalDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { regimenFiscalService: () => regimenFiscalServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRegimenFiscal = { id: 123 };
        regimenFiscalServiceStub.find.resolves(foundRegimenFiscal);

        // WHEN
        comp.retrieveRegimenFiscal(123);
        await comp.$nextTick();

        // THEN
        expect(comp.regimenFiscal).toBe(foundRegimenFiscal);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRegimenFiscal = { id: 123 };
        regimenFiscalServiceStub.find.resolves(foundRegimenFiscal);

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
