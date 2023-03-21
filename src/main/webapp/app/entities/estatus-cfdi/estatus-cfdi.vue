<template>
  <div>
    <h2 id="page-heading" data-cy="EstatusCfdiHeading">
      <span v-text="$t('timbradoCatalogosApp.estatusCfdi.home.title')" id="estatus-cfdi-heading">Estatus Cfdis</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('timbradoCatalogosApp.estatusCfdi.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EstatusCfdiCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-estatus-cfdi"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('timbradoCatalogosApp.estatusCfdi.home.createLabel')"> Create a new Estatus Cfdi </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && estatusCfdis && estatusCfdis.length === 0">
      <span v-text="$t('timbradoCatalogosApp.estatusCfdi.home.notFound')">No estatusCfdis found</span>
    </div>
    <div class="table-responsive" v-if="estatusCfdis && estatusCfdis.length > 0">
      <table class="table table-striped" aria-describedby="estatusCfdis">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.estatusCfdi.fecha')">Fecha</span></th>
            <th scope="row">
              <span v-text="$t('timbradoCatalogosApp.estatusCfdi.descripcionEstatusCfdi')">Descripcion Estatus Cfdi</span>
            </th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.estatusCfdi.estatus')">Estatus</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.estatusCfdi.fechaModificacion')">Fecha Modificacion</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.estatusCfdi.usuario')">Usuario</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="estatusCfdi in estatusCfdis" :key="estatusCfdi.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EstatusCfdiView', params: { estatusCfdiId: estatusCfdi.id } }">{{ estatusCfdi.id }}</router-link>
            </td>
            <td>{{ estatusCfdi.fecha }}</td>
            <td>{{ estatusCfdi.descripcionEstatusCfdi }}</td>
            <td v-text="$t('timbradoCatalogosApp.Estatus.' + estatusCfdi.estatus)">{{ estatusCfdi.estatus }}</td>
            <td>{{ estatusCfdi.fechaModificacion }}</td>
            <td>{{ estatusCfdi.usuario }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EstatusCfdiView', params: { estatusCfdiId: estatusCfdi.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EstatusCfdiEdit', params: { estatusCfdiId: estatusCfdi.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(estatusCfdi)"
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
          id="timbradoCatalogosApp.estatusCfdi.delete.question"
          data-cy="estatusCfdiDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-estatusCfdi-heading" v-text="$t('timbradoCatalogosApp.estatusCfdi.delete.question', { id: removeId })">
          Are you sure you want to delete this Estatus Cfdi?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-estatusCfdi"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEstatusCfdi()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./estatus-cfdi.component.ts"></script>
