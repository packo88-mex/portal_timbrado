/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MotivosCancelacionDetailComponent from '@/entities/motivos-cancelacion/motivos-cancelacion-details.vue';
import MotivosCancelacionClass from '@/entities/motivos-cancelacion/motivos-cancelacion-details.component';
import MotivosCancelacionService from '@/entities/motivos-cancelacion/motivos-cancelacion.service';
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
  describe('MotivosCancelacion Management Detail Component', () => {
    let wrapper: Wrapper<MotivosCancelacionClass>;
    let comp: MotivosCancelacionClass;
    let motivosCancelacionServiceStub: SinonStubbedInstance<MotivosCancelacionService>;

    beforeEach(() => {
      motivosCancelacionServiceStub = sinon.createStubInstance<MotivosCancelacionService>(MotivosCancelacionService);

      wrapper = shallowMount<MotivosCancelacionClass>(MotivosCancelacionDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { motivosCancelacionService: () => motivosCancelacionServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMotivosCancelacion = { id: 123 };
        motivosCancelacionServiceStub.find.resolves(foundMotivosCancelacion);

        // WHEN
        comp.retrieveMotivosCancelacion(123);
        await comp.$nextTick();

        // THEN
        expect(comp.motivosCancelacion).toBe(foundMotivosCancelacion);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMotivosCancelacion = { id: 123 };
        motivosCancelacionServiceStub.find.resolves(foundMotivosCancelacion);

        // WHEN
        comp.beforeRouteEnter({ params: { motivosCancelacionId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.motivosCancelacion).toBe(foundMotivosCancelacion);
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
