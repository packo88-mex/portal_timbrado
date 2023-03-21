/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RegimenFiscalComponent from '@/entities/regimen-fiscal/regimen-fiscal.vue';
import RegimenFiscalClass from '@/entities/regimen-fiscal/regimen-fiscal.component';
import RegimenFiscalService from '@/entities/regimen-fiscal/regimen-fiscal.service';
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
  describe('RegimenFiscal Management Component', () => {
    let wrapper: Wrapper<RegimenFiscalClass>;
    let comp: RegimenFiscalClass;
    let regimenFiscalServiceStub: SinonStubbedInstance<RegimenFiscalService>;

    beforeEach(() => {
      regimenFiscalServiceStub = sinon.createStubInstance<RegimenFiscalService>(RegimenFiscalService);
      regimenFiscalServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RegimenFiscalClass>(RegimenFiscalComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          regimenFiscalService: () => regimenFiscalServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      regimenFiscalServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRegimenFiscals();
      await comp.$nextTick();

      // THEN
      expect(regimenFiscalServiceStub.retrieve.called).toBeTruthy();
      expect(comp.regimenFiscals[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      regimenFiscalServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(regimenFiscalServiceStub.retrieve.callCount).toEqual(1);

      comp.removeRegimenFiscal();
      await comp.$nextTick();

      // THEN
      expect(regimenFiscalServiceStub.delete.called).toBeTruthy();
      expect(regimenFiscalServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
