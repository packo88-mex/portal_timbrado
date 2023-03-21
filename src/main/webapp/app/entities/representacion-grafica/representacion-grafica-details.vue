<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="representacionGrafica">
        <h2 class="jh-entity-heading" data-cy="representacionGraficaDetailsHeading">
          <span v-text="$t('timbradoCatalogosApp.representacionGrafica.detail.title')">RepresentacionGrafica</span>
          {{ representacionGrafica.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('timbradoCatalogosApp.representacionGrafica.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ representacionGrafica.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.representacionGrafica.representacionGrafica')">Representacion Grafica</span>
          </dt>
          <dd>
            <span>{{ representacionGrafica.representacionGrafica }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.representacionGrafica.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('timbradoCatalogosApp.Estatus.' + representacionGrafica.estatus)">{{ representacionGrafica.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.representacionGrafica.fechaModificacion')">Fecha Modificacion</span>
          </dt>
          <dd>
            <span>{{ representacionGrafica.fechaModificacion }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.representacionGrafica.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ representacionGrafica.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.representacionGrafica.tiporecibo')">Tiporecibo</span>
          </dt>
          <dd>
            <div v-if="representacionGrafica.tiporecibo">
              <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: representacionGrafica.tiporecibo.id } }">{{
                representacionGrafica.tiporecibo.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="representacionGrafica.id"
          :to="{ name: 'RepresentacionGraficaEdit', params: { representacionGraficaId: representacionGrafica.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./representacion-grafica-details.component.ts"></script>
