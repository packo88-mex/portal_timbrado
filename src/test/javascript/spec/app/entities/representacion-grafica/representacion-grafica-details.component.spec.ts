/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RepresentacionGraficaDetailComponent from '@/entities/representacion-grafica/representacion-grafica-details.vue';
import RepresentacionGraficaClass from '@/entities/representacion-grafica/representacion-grafica-details.component';
import RepresentacionGraficaService from '@/entities/representacion-grafica/representacion-grafica.service';
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
  describe('RepresentacionGrafica Management Detail Component', () => {
    let wrapper: Wrapper<RepresentacionGraficaClass>;
    let comp: RepresentacionGraficaClass;
    let representacionGraficaServiceStub: SinonStubbedInstance<RepresentacionGraficaService>;

    beforeEach(() => {
      representacionGraficaServiceStub = sinon.createStubInstance<RepresentacionGraficaService>(RepresentacionGraficaService);

      wrapper = shallowMount<RepresentacionGraficaClass>(RepresentacionGraficaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { representacionGraficaService: () => representacionGraficaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRepresentacionGrafica = { id: 123 };
        representacionGraficaServiceStub.find.resolves(foundRepresentacionGrafica);

        // WHEN
        comp.retrieveRepresentacionGrafica(123);
        await comp.$nextTick();

        // THEN
        expect(comp.representacionGrafica).toBe(foundRepresentacionGrafica);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRepresentacionGrafica = { id: 123 };
        representacionGraficaServiceStub.find.resolves(foundRepresentacionGrafica);

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
