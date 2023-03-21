/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TipoCfdiDetailComponent from '@/entities/tipo-cfdi/tipo-cfdi-details.vue';
import TipoCfdiClass from '@/entities/tipo-cfdi/tipo-cfdi-details.component';
import TipoCfdiService from '@/entities/tipo-cfdi/tipo-cfdi.service';
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
  describe('TipoCfdi Management Detail Component', () => {
    let wrapper: Wrapper<TipoCfdiClass>;
    let comp: TipoCfdiClass;
    let tipoCfdiServiceStub: SinonStubbedInstance<TipoCfdiService>;

    beforeEach(() => {
      tipoCfdiServiceStub = sinon.createStubInstance<TipoCfdiService>(TipoCfdiService);

      wrapper = shallowMount<TipoCfdiClass>(TipoCfdiDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { tipoCfdiService: () => tipoCfdiServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTipoCfdi = { id: 123 };
        tipoCfdiServiceStub.find.resolves(foundTipoCfdi);

        // WHEN
        comp.retrieveTipoCfdi(123);
        await comp.$nextTick();

        // THEN
        expect(comp.tipoCfdi).toBe(foundTipoCfdi);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTipoCfdi = { id: 123 };
        tipoCfdiServiceStub.find.resolves(foundTipoCfdi);

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
