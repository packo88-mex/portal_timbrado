import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMotivosCancelacion } from '@/shared/model/motivos-cancelacion.model';
import MotivosCancelacionService from './motivos-cancelacion.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MotivosCancelacionDetails extends Vue {
  @Inject('motivosCancelacionService') private motivosCancelacionService: () => MotivosCancelacionService;
  @Inject('alertService') private alertService: () => AlertService;

  public motivosCancelacion: IMotivosCancelacion = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.motivosCancelacionId) {
        vm.retrieveMotivosCancelacion(to.params.motivosCancelacionId);
      }
    });
  }

  public retrieveMotivosCancelacion(motivosCancelacionId) {
    this.motivosCancelacionService()
      .find(motivosCancelacionId)
      .then(res => {
        this.motivosCancelacion = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
