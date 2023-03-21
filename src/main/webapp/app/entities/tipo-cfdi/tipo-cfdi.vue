<template>
  <div>
    <h2 id="page-heading" data-cy="TipoCfdiHeading">
      <span v-text="$t('timbradoCatalogosApp.tipoCfdi.home.title')" id="tipo-cfdi-heading">Tipo Cfdis</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('timbradoCatalogosApp.tipoCfdi.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TipoCfdiCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-tipo-cfdi"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('timbradoCatalogosApp.tipoCfdi.home.createLabel')"> Create a new Tipo Cfdi </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tipoCfdis && tipoCfdis.length === 0">
      <span v-text="$t('timbradoCatalogosApp.tipoCfdi.home.notFound')">No tipoCfdis found</span>
    </div>
    <div class="table-responsive" v-if="tipoCfdis && tipoCfdis.length > 0">
      <table class="table table-striped" aria-describedby="tipoCfdis">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoCfdi.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoCfdi.tipoCfdi')">Tipo Cfdi</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoCfdi.estatus')">Estatus</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoCfdi.fechaModificacion')">Fecha Modificacion</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.tipoCfdi.usuario')">Usuario</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tipoCfdi in tipoCfdis" :key="tipoCfdi.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TipoCfdiView', params: { tipoCfdiId: tipoCfdi.id } }">{{ tipoCfdi.id }}</router-link>
            </td>
            <td>{{ tipoCfdi.fecha }}</td>
            <td>{{ tipoCfdi.tipoCfdi }}</td>
            <td v-text="$t('timbradoCatalogosApp.Estatus.' + tipoCfdi.estatus)">{{ tipoCfdi.estatus }}</td>
            <td>{{ tipoCfdi.fechaModificacion }}</td>
            <td>{{ tipoCfdi.usuario }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TipoCfdiView', params: { tipoCfdiId: tipoCfdi.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TipoCfdiEdit', params: { tipoCfdiId: tipoCfdi.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(tipoCfdi)"
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
        ><span id="timbradoCatalogosApp.tipoCfdi.delete.question" data-cy="tipoCfdiDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-tipoCfdi-heading" v-text="$t('timbradoCatalogosApp.tipoCfdi.delete.question', { id: removeId })">
          Are you sure you want to delete this Tipo Cfdi?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-tipoCfdi"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTipoCfdi()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./tipo-cfdi.component.ts"></script>
