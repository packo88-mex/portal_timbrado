import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRepresentacionGrafica } from '@/shared/model/representacion-grafica.model';

import RepresentacionGraficaService from './representacion-grafica.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RepresentacionGrafica extends Vue {
  @Inject('representacionGraficaService') private representacionGraficaService: () => RepresentacionGraficaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public representacionGraficas: IRepresentacionGrafica[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRepresentacionGraficas();
  }

  public clear(): void {
    this.retrieveAllRepresentacionGraficas();
  }

  public retrieveAllRepresentacionGraficas(): void {
    this.isFetching = true;
    this.representacionGraficaService()
      .retrieve()
      .then(
        res => {
          this.representacionGraficas = res.data;
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

  public prepareRemove(instance: IRepresentacionGrafica): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRepresentacionGrafica(): void {
    this.representacionGraficaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('timbradoCatalogosApp.representacionGrafica.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllRepresentacionGraficas();
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
