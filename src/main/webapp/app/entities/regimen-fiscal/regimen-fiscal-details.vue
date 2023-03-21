<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="regimenFiscal">
        <h2 class="jh-entity-heading" data-cy="regimenFiscalDetailsHeading">
          <span v-text="$t('timbradoCatalogosApp.regimenFiscal.detail.title')">RegimenFiscal</span> {{ regimenFiscal.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('timbradoCatalogosApp.regimenFiscal.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ regimenFiscal.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.regimenFiscal.regimenFiscal')">Regimen Fiscal</span>
          </dt>
          <dd>
            <span>{{ regimenFiscal.regimenFiscal }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.regimenFiscal.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('timbradoCatalogosApp.Estatus.' + regimenFiscal.estatus)">{{ regimenFiscal.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.regimenFiscal.fechaModificacion')">Fecha Modificacion</span>
          </dt>
          <dd>
            <span>{{ regimenFiscal.fechaModificacion }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.regimenFiscal.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ regimenFiscal.usuario }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="regimenFiscal.id"
          :to="{ name: 'RegimenFiscalEdit', params: { regimenFiscalId: regimenFiscal.id } }"
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

<script lang="ts" src="./regimen-fiscal-details.component.ts"></script>
