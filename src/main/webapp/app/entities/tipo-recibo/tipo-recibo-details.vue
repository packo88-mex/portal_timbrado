<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tipoRecibo">
        <h2 class="jh-entity-heading" data-cy="tipoReciboDetailsHeading">
          <span v-text="$t('timbradoCatalogosApp.tipoRecibo.detail.title')">TipoRecibo</span> {{ tipoRecibo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoRecibo.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ tipoRecibo.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoRecibo.tipoRecibo')">Tipo Recibo</span>
          </dt>
          <dd>
            <span>{{ tipoRecibo.tipoRecibo }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoRecibo.clave')">Clave</span>
          </dt>
          <dd>
            <span>{{ tipoRecibo.clave }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoRecibo.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('timbradoCatalogosApp.Estatus.' + tipoRecibo.estatus)">{{ tipoRecibo.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoRecibo.fechaModificacion')">Fecha Modificacion</span>
          </dt>
          <dd>
            <span>{{ tipoRecibo.fechaModificacion }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoRecibo.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ tipoRecibo.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('timbradoCatalogosApp.tipoRecibo.tipocfdi')">Tipocfdi</span>
          </dt>
          <dd>
            <div v-if="tipoRecibo.tipocfdi">
              <router-link :to="{ name: 'TipoCfdiView', params: { tipoCfdiId: tipoRecibo.tipocfdi.id } }">{{
                tipoRecibo.tipocfdi.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="tipoRecibo.id"
          :to="{ name: 'TipoReciboEdit', params: { tipoReciboId: tipoRecibo.id } }"
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

<script lang="ts" src="./tipo-recibo-details.component.ts"></script>
