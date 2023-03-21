/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TipoReciboComponent from '@/entities/tipo-recibo/tipo-recibo.vue';
import TipoReciboClass from '@/entities/tipo-recibo/tipo-recibo.component';
import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';
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
  describe('TipoRecibo Management Component', () => {
    let wrapper: Wrapper<TipoReciboClass>;
    let comp: TipoReciboClass;
    let tipoReciboServiceStub: SinonStubbedInstance<TipoReciboService>;

    beforeEach(() => {
      tipoReciboServiceStub = sinon.createStubInstance<TipoReciboService>(TipoReciboService);
      tipoReciboServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TipoReciboClass>(TipoReciboComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          tipoReciboService: () => tipoReciboServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      tipoReciboServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTipoRecibos();
      await comp.$nextTick();

      // THEN
      expect(tipoReciboServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tipoRecibos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      tipoReciboServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(tipoReciboServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTipoRecibo();
      await comp.$nextTick();

      // THEN
      expect(tipoReciboServiceStub.delete.called).toBeTruthy();
      expect(tipoReciboServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
