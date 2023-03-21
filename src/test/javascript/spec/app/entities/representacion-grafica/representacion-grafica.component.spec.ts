/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RepresentacionGraficaComponent from '@/entities/representacion-grafica/representacion-grafica.vue';
import RepresentacionGraficaClass from '@/entities/representacion-grafica/representacion-grafica.component';
import RepresentacionGraficaService from '@/entities/representacion-grafica/representacion-grafica.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('RepresentacionGrafica Management Component', () => {
    let wrapper: Wrapper<RepresentacionGraficaClass>;
    let comp: RepresentacionGraficaClass;
    let representacionGraficaServiceStub: SinonStubbedInstance<RepresentacionGraficaService>;

    beforeEach(() => {
      representacionGraficaServiceStub = sinon.createStubInstance<RepresentacionGraficaService>(RepresentacionGraficaService);
      representacionGraficaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RepresentacionGraficaClass>(RepresentacionGraficaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          representacionGraficaService: () => representacionGraficaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      representacionGraficaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRepresentacionGraficas();
      await comp.$nextTick();

      // THEN
      expect(representacionGraficaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.representacionGraficas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      representacionGraficaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(representacionGraficaServiceStub.retrieve.callCount).toEqual(1);

      comp.removeRepresentacionGrafica();
      await comp.$nextTick();

      // THEN
      expect(representacionGraficaServiceStub.delete.called).toBeTruthy();
      expect(representacionGraficaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
