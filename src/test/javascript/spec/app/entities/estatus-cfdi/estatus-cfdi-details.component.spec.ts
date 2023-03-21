/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EstatusCfdiDetailComponent from '@/entities/estatus-cfdi/estatus-cfdi-details.vue';
import EstatusCfdiClass from '@/entities/estatus-cfdi/estatus-cfdi-details.component';
import EstatusCfdiService from '@/entities/estatus-cfdi/estatus-cfdi.service';
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
  describe('EstatusCfdi Management Detail Component', () => {
    let wrapper: Wrapper<EstatusCfdiClass>;
    let comp: EstatusCfdiClass;
    let estatusCfdiServiceStub: SinonStubbedInstance<EstatusCfdiService>;

    beforeEach(() => {
      estatusCfdiServiceStub = sinon.createStubInstance<EstatusCfdiService>(EstatusCfdiService);

      wrapper = shallowMount<EstatusCfdiClass>(EstatusCfdiDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { estatusCfdiService: () => estatusCfdiServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEstatusCfdi = { id: 123 };
        estatusCfdiServiceStub.find.resolves(foundEstatusCfdi);

        // WHEN
        comp.retrieveEstatusCfdi(123);
        await comp.$nextTick();

        // THEN
        expect(comp.estatusCfdi).toBe(foundEstatusCfdi);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEstatusCfdi = { id: 123 };
        estatusCfdiServiceStub.find.resolves(foundEstatusCfdi);

        // WHEN
        comp.beforeRouteEnter({ params: { estatusCfdiId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.estatusCfdi).toBe(foundEstatusCfdi);
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
