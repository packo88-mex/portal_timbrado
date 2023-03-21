/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TipoCfdiComponent from '@/entities/tipo-cfdi/tipo-cfdi.vue';
import TipoCfdiClass from '@/entities/tipo-cfdi/tipo-cfdi.component';
import TipoCfdiService from '@/entities/tipo-cfdi/tipo-cfdi.service';
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
  describe('TipoCfdi Management Component', () => {
    let wrapper: Wrapper<TipoCfdiClass>;
    let comp: TipoCfdiClass;
    let tipoCfdiServiceStub: SinonStubbedInstance<TipoCfdiService>;

    beforeEach(() => {
      tipoCfdiServiceStub = sinon.createStubInstance<TipoCfdiService>(TipoCfdiService);
      tipoCfdiServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TipoCfdiClass>(TipoCfdiComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          tipoCfdiService: () => tipoCfdiServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      tipoCfdiServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTipoCfdis();
      await comp.$nextTick();

      // THEN
      expect(tipoCfdiServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tipoCfdis[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      tipoCfdiServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(tipoCfdiServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTipoCfdi();
      await comp.$nextTick();

      // THEN
      expect(tipoCfdiServiceStub.delete.called).toBeTruthy();
      expect(tipoCfdiServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
