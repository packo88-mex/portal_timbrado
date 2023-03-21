<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="motivosCancelacion">
        <h2 class="jh-entity-heading" data-cy="motivosCancelacionDetailsHeading">
          <span v-text="$t('timbradoCatalogosApp.motivosCancelacion.detail.title')">MotivosCancelacion</span> {{ motivosCancelacion.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('timbradoCatalogosApp.motivosCancelacion.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ motivosCancelacion.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.motivosCancelacion.motivoCancelacion')">Motivo Cancelacion</span>
          </dt>
          <dd>
            <span>{{ motivosCancelacion.motivoCancelacion }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.motivosCancelacion.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('timbradoCatalogosApp.Estatus.' + motivosCancelacion.estatus)">{{ motivosCancelacion.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.motivosCancelacion.fechaModificacion')">Fecha Modificacion</span>
          </dt>
          <dd>
            <span>{{ motivosCancelacion.fechaModificacion }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.motivosCancelacion.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ motivosCancelacion.usuario }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="motivosCancelacion.id"
          :to="{ name: 'MotivosCancelacionEdit', params: { motivosCancelacionId: motivosCancelacion.id } }"
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

<script lang="ts" src="./motivos-cancelacion-details.component.ts"></script>
