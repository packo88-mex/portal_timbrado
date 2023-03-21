<template>
  <div>
    <h2 id="page-heading" data-cy="TipoReciboHeading">
      <span v-text="$t('timbradoCatalogosApp.tipoRecibo.home.title')" id="tipo-recibo-heading">Tipo Recibos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('timbradoCatalogosApp.tipoRecibo.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TipoReciboCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-tipo-recibo"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('timbradoCatalogosApp.tipoRecibo.home.createLabel')"> Create a new Tipo Recibo </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tipoRecibos && tipoRecibos.length === 0">
      <span v-text="$t('timbradoCatalogosApp.tipoRecibo.home.notFound')">No tipoRecibos found</span>
    </div>
    <div class="table-responsive" v-if="tipoRecibos && tipoRecibos.length > 0">
      <table class="table table-striped" aria-describedby="tipoRecibos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoRecibo.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoRecibo.tipoRecibo')">Tipo Recibo</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoRecibo.clave')">Clave</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoRecibo.estatus')">Estatus</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoRecibo.fechaModificacion')">Fecha Modificacion</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoRecibo.usuario')">Usuario</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoRecibo.tipocfdi')">Tipocfdi</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tipoRecibo in tipoRecibos" :key="tipoRecibo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: tipoRecibo.id } }">{{ tipoRecibo.id }}</router-link>
            </td>
            <td>{{ tipoRecibo.fecha }}</td>
            <td>{{ tipoRecibo.tipoRecibo }}</td>
            <td>{{ tipoRecibo.clave }}</td>
            <td v-text="$t('timbradoCatalogosApp.Estatus.' + tipoRecibo.estatus)">{{ tipoRecibo.estatus }}</td>
            <td>{{ tipoRecibo.fechaModificacion }}</td>
            <td>{{ tipoRecibo.usuario }}</td>
            <td>
              <div v-if="tipoRecibo.tipocfdi">
                <router-link :to="{ name: 'TipoCfdiView', params: { tipoCfdiId: tipoRecibo.tipocfdi.id } }">{{
                  tipoRecibo.tipocfdi.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: tipoRecibo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TipoReciboEdit', params: { tipoReciboId: tipoRecibo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(tipoRecibo)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="timbradoCatalogosApp.tipoRecibo.delete.question"
          data-cy="tipoReciboDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-tipoRecibo-heading" v-text="$t('timbradoCatalogosApp.tipoRecibo.delete.question', { id: removeId })">
          Are you sure you want to delete this Tipo Recibo?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-tipoRecibo"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTipoRecibo()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./tipo-recibo.component.ts"></script>
