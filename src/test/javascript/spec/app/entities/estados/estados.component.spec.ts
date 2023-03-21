/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import EstadosComponent from '@/entities/estados/estados.vue';
import EstadosClass from '@/entities/estados/estados.component';
import EstadosService from '@/entities/estados/estados.service';
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
  describe('Estados Management Component', () => {
    let wrapper: Wrapper<EstadosClass>;
    let comp: EstadosClass;
    let estadosServiceStub: SinonStubbedInstance<EstadosService>;

    beforeEach(() => {
      estadosServiceStub = sinon.createStubInstance<EstadosService>(EstadosService);
      estadosServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EstadosClass>(EstadosComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          estadosService: () => estadosServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      estadosServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEstadoss();
      await comp.$nextTick();

      // THEN
      expect(estadosServiceStub.retrieve.called).toBeTruthy();
      expect(comp.estados[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      estadosServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(estadosServiceStub.retrieve.callCount).toEqual(1);

      comp.removeEstados();
      await comp.$nextTick();

      // THEN
      expect(estadosServiceStub.delete.called).toBeTruthy();
      expect(estadosServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
