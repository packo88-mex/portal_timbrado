import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITipoCfdi } from '@/shared/model/tipo-cfdi.model';

import TipoCfdiService from './tipo-cfdi.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TipoCfdi extends Vue {
  @Inject('tipoCfdiService') private tipoCfdiService: () => TipoCfdiService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public tipoCfdis: ITipoCfdi[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTipoCfdis();
  }

  public clear(): void {
    this.retrieveAllTipoCfdis();
  }

  public retrieveAllTipoCfdis(): void {
    this.isFetching = true;
    this.tipoCfdiService()
      .retrieve()
      .then(
        res => {
          this.tipoCfdis = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: ITipoCfdi): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTipoCfdi(): void {
    this.tipoCfdiService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('timbradoCatalogosApp.tipoCfdi.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTipoCfdis();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
