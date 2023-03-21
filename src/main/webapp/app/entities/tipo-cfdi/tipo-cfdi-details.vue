<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tipoCfdi">
        <h2 class="jh-entity-heading" data-cy="tipoCfdiDetailsHeading">
          <span v-text="$t('timbradoCatalogosApp.tipoCfdi.detail.title')">TipoCfdi</span> {{ tipoCfdi.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoCfdi.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ tipoCfdi.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoCfdi.tipoCfdi')">Tipo Cfdi</span>
          </dt>
          <dd>
            <span>{{ tipoCfdi.tipoCfdi }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoCfdi.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('timbradoCatalogosApp.Estatus.' + tipoCfdi.estatus)">{{ tipoCfdi.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoCfdi.fechaModificacion')">Fecha Modificacion</span>
          </dt>
          <dd>
            <span>{{ tipoCfdi.fechaModificacion }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoCfdi.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ tipoCfdi.usuario }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="tipoCfdi.id" :to="{ name: 'TipoCfdiEdit', params: { tipoCfdiId: tipoCfdi.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./tipo-cfdi-details.component.ts"></script>
