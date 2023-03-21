/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MotivosCancelacionComponent from '@/entities/motivos-cancelacion/motivos-cancelacion.vue';
import MotivosCancelacionClass from '@/entities/motivos-cancelacion/motivos-cancelacion.component';
import MotivosCancelacionService from '@/entities/motivos-cancelacion/motivos-cancelacion.service';
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
  describe('MotivosCancelacion Management Component', () => {
    let wrapper: Wrapper<MotivosCancelacionClass>;
    let comp: MotivosCancelacionClass;
    let motivosCancelacionServiceStub: SinonStubbedInstance<MotivosCancelacionService>;

    beforeEach(() => {
      motivosCancelacionServiceStub = sinon.createStubInstance<MotivosCancelacionService>(MotivosCancelacionService);
      motivosCancelacionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MotivosCancelacionClass>(MotivosCancelacionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          motivosCancelacionService: () => motivosCancelacionServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      motivosCancelacionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMotivosCancelacions();
      await comp.$nextTick();

      // THEN
      expect(motivosCancelacionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.motivosCancelacions[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      motivosCancelacionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(motivosCancelacionServiceStub.retrieve.callCount).toEqual(1);

      comp.removeMotivosCancelacion();
      await comp.$nextTick();

      // THEN
      expect(motivosCancelacionServiceStub.delete.called).toBeTruthy();
      expect(motivosCancelacionServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
