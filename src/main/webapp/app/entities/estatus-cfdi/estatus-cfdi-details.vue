<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="estatusCfdi">
        <h2 class="jh-entity-heading" data-cy="estatusCfdiDetailsHeading">
          <span v-text="$t('timbradoCatalogosApp.estatusCfdi.detail.title')">EstatusCfdi</span> {{ estatusCfdi.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estatusCfdi.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ estatusCfdi.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estatusCfdi.descripcionEstatusCfdi')">Descripcion Estatus Cfdi</span>
          </dt>
          <dd>
            <span>{{ estatusCfdi.descripcionEstatusCfdi }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estatusCfdi.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('timbradoCatalogosApp.Estatus.' + estatusCfdi.estatus)">{{ estatusCfdi.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estatusCfdi.fechaModificacion')">Fecha Modificacion</span>
          </dt>
          <dd>
            <span>{{ estatusCfdi.fechaModificacion }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estatusCfdi.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ estatusCfdi.usuario }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="estatusCfdi.id"
          :to="{ name: 'EstatusCfdiEdit', params: { estatusCfdiId: estatusCfdi.id } }"
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

<script lang="ts" src="./estatus-cfdi-details.component.ts"></script>
