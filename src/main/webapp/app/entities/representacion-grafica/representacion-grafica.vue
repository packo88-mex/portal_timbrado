<template>
  <div>
    <h2 id="page-heading" data-cy="RepresentacionGraficaHeading">
      <span v-text="$t('timbradoCatalogosApp.representacionGrafica.home.title')" id="representacion-grafica-heading"
        >Representacion Graficas</span
      >
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('timbradoCatalogosApp.representacionGrafica.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RepresentacionGraficaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-representacion-grafica"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('timbradoCatalogosApp.representacionGrafica.home.createLabel')"> Create a new Representacion Grafica </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && representacionGraficas && representacionGraficas.length === 0">
      <span v-text="$t('timbradoCatalogosApp.representacionGrafica.home.notFound')">No representacionGraficas found</span>
    </div>
    <div class="table-responsive" v-if="representacionGraficas && representacionGraficas.length > 0">
      <table class="table table-striped" aria-describedby="representacionGraficas">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.representacionGrafica.fecha')">Fecha</span></th>
            <th scope="row">
              <span v-text="$t('timbradoCatalogosApp.representacionGrafica.representacionGrafica')">Representacion Grafica</span>
            </th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.representacionGrafica.estatus')">Estatus</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.representacionGrafica.fechaModificacion')">Fecha Modificacion</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.representacionGrafica.usuario')">Usuario</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.representacionGrafica.tiporecibo')">Tiporecibo</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="representacionGrafica in representacionGraficas" :key="representacionGrafica.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RepresentacionGraficaView', params: { representacionGraficaId: representacionGrafica.id } }">{{
                representacionGrafica.id
              }}</router-link>
            </td>
            <td>{{ representacionGrafica.fecha }}</td>
            <td>{{ representacionGrafica.representacionGrafica }}</td>
            <td v-text="$t('timbradoCatalogosApp.Estatus.' + representacionGrafica.estatus)">{{ representacionGrafica.estatus }}</td>
            <td>{{ representacionGrafica.fechaModificacion }}</td>
            <td>{{ representacionGrafica.usuario }}</td>
            <td>
              <div v-if="representacionGrafica.tiporecibo">
                <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: representacionGrafica.tiporecibo.id } }">{{
                  representacionGrafica.tiporecibo.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RepresentacionGraficaView', params: { representacionGraficaId: representacionGrafica.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RepresentacionGraficaEdit', params: { representacionGraficaId: representacionGrafica.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(representacionGrafica)"
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
          id="timbradoCatalogosApp.representacionGrafica.delete.question"
          data-cy="representacionGraficaDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p
          id="jhi-delete-representacionGrafica-heading"
          v-text="$t('timbradoCatalogosApp.representacionGrafica.delete.question', { id: removeId })"
        >
          Are you sure you want to delete this Representacion Grafica?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-representacionGrafica"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRepresentacionGrafica()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./representacion-grafica.component.ts"></script>
