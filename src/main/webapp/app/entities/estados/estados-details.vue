<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="estados">
        <h2 class="jh-entity-heading" data-cy="estadosDetailsHeading">
          <span v-text="$t('timbradoCatalogosApp.estados.detail.title')">Estados</span> {{ estados.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estados.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ estados.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estados.pais')">Pais</span>
          </dt>
          <dd>
            <span>{{ estados.pais }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estados.estado')">Estado</span>
          </dt>
          <dd>
            <span>{{ estados.estado }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estados.descripcionEstado')">Descripcion Estado</span>
          </dt>
          <dd>
            <span>{{ estados.descripcionEstado }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estados.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('timbradoCatalogosApp.Estatus.' + estados.estatus)">{{ estados.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estados.fechaModificacion')">Fecha Modificacion</span>
          </dt>
          <dd>
            <span>{{ estados.fechaModificacion }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.estados.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ estados.usuario }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="estados.id" :to="{ name: 'EstadosEdit', params: { estadosId: estados.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./estados-details.component.ts"></script>
