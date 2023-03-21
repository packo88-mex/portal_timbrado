import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRepresentacionGrafica } from '@/shared/model/representacion-grafica.model';
import RepresentacionGraficaService from './representacion-grafica.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RepresentacionGraficaDetails extends Vue {
  @Inject('representacionGraficaService') private representacionGraficaService: () => RepresentacionGraficaService;
  @Inject('alertService') private alertService: () => AlertService;

  public representacionGrafica: IRepresentacionGrafica = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.representacionGraficaId) {
        vm.retrieveRepresentacionGrafica(to.params.representacionGraficaId);
      }
    });
  }

  public retrieveRepresentacionGrafica(representacionGraficaId) {
    this.representacionGraficaService()
      .find(representacionGraficaId)
      .then(res => {
        this.representacionGrafica = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
