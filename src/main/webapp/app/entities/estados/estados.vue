<template>
  <div>
    <h2 id="page-heading" data-cy="EstadosHeading">
      <span v-text="$t('timbradoCatalogosApp.estados.home.title')" id="estados-heading">Estados</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('timbradoCatalogosApp.estados.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EstadosCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-estados"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('timbradoCatalogosApp.estados.home.createLabel')"> Create a new Estados </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && estados && estados.length === 0">
      <span v-text="$t('timbradoCatalogosApp.estados.home.notFound')">No estados found</span>
    </div>
    <div class="table-responsive" v-if="estados && estados.length > 0">
      <table class="table table-striped" aria-describedby="estados">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.estados.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.estados.pais')">Pais</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.estados.estado')">Estado</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.estados.descripcionEstado')">Descripcion Estado</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.estados.estatus')">Estatus</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.estados.fechaModificacion')">Fecha Modificacion</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.estados.usuario')">Usuario</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="estados in estados" :key="estados.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EstadosView', params: { estadosId: estados.id } }">{{ estados.id }}</router-link>
            </td>
            <td>{{ estados.fecha }}</td>
            <td>{{ estados.pais }}</td>
            <td>{{ estados.estado }}</td>
            <td>{{ estados.descripcionEstado }}</td>
            <td v-text="$t('timbradoCatalogosApp.Estatus.' + estados.estatus)">{{ estados.estatus }}</td>
            <td>{{ estados.fechaModificacion }}</td>
            <td>{{ estados.usuario }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EstadosView', params: { estadosId: estados.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EstadosEdit', params: { estadosId: estados.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(estados)"
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
        ><span id="timbradoCatalogosApp.estados.delete.question" data-cy="estadosDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-estados-heading" v-text="$t('timbradoCatalogosApp.estados.delete.question', { id: removeId })">
          Are you sure you want to delete this Estados?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-estados"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEstados()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./estados.component.ts"></script>
