/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import EstatusCfdiComponent from '@/entities/estatus-cfdi/estatus-cfdi.vue';
import EstatusCfdiClass from '@/entities/estatus-cfdi/estatus-cfdi.component';
import EstatusCfdiService from '@/entities/estatus-cfdi/estatus-cfdi.service';
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
  describe('EstatusCfdi Management Component', () => {
    let wrapper: Wrapper<EstatusCfdiClass>;
    let comp: EstatusCfdiClass;
    let estatusCfdiServiceStub: SinonStubbedInstance<EstatusCfdiService>;

    beforeEach(() => {
      estatusCfdiServiceStub = sinon.createStubInstance<EstatusCfdiService>(EstatusCfdiService);
      estatusCfdiServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EstatusCfdiClass>(EstatusCfdiComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          estatusCfdiService: () => estatusCfdiServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      estatusCfdiServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEstatusCfdis();
      await comp.$nextTick();

      // THEN
      expect(estatusCfdiServiceStub.retrieve.called).toBeTruthy();
      expect(comp.estatusCfdis[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      estatusCfdiServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(estatusCfdiServiceStub.retrieve.callCount).toEqual(1);

      comp.removeEstatusCfdi();
      await comp.$nextTick();

      // THEN
      expect(estatusCfdiServiceStub.delete.called).toBeTruthy();
      expect(estatusCfdiServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
