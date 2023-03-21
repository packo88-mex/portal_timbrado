import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

import TipoReciboService from './tipo-recibo.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TipoRecibo extends Vue {
  @Inject('tipoReciboService') private tipoReciboService: () => TipoReciboService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public tipoRecibos: ITipoRecibo[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTipoRecibos();
  }

  public clear(): void {
    this.retrieveAllTipoRecibos();
  }

  public retrieveAllTipoRecibos(): void {
    this.isFetching = true;
    this.tipoReciboService()
      .retrieve()
      .then(
        res => {
          this.tipoRecibos = res.data;
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

  public prepareRemove(instance: ITipoRecibo): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTipoRecibo(): void {
    this.tipoReciboService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('timbradoCatalogosApp.tipoRecibo.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTipoRecibos();
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
