<template>
  <div>
    <h2 id="page-heading" data-cy="MotivosCancelacionHeading">
      <span v-text="$t('timbradoCatalogosApp.motivosCancelacion.home.title')" id="motivos-cancelacion-heading">Motivos Cancelacions</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('timbradoCatalogosApp.motivosCancelacion.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MotivosCancelacionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-motivos-cancelacion"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('timbradoCatalogosApp.motivosCancelacion.home.createLabel')"> Create a new Motivos Cancelacion </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && motivosCancelacions && motivosCancelacions.length === 0">
      <span v-text="$t('timbradoCatalogosApp.motivosCancelacion.home.notFound')">No motivosCancelacions found</span>
    </div>
    <div class="table-responsive" v-if="motivosCancelacions && motivosCancelacions.length > 0">
      <table class="table table-striped" aria-describedby="motivosCancelacions">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.motivosCancelacion.fecha')">Fecha</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.motivosCancelacion.motivoCancelacion')">Motivo Cancelacion</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.motivosCancelacion.estatus')">Estatus</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.motivosCancelacion.fechaModificacion')">Fecha Modificacion</span></th>
            <th scope="row"><span v-text="$t('timbradoCatalogosApp.motivosCancelacion.usuario')">Usuario</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="motivosCancelacion in motivosCancelacions" :key="motivosCancelacion.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MotivosCancelacionView', params: { motivosCancelacionId: motivosCancelacion.id } }">{{
                motivosCancelacion.id
              }}</router-link>
            </td>
            <td>{{ motivosCancelacion.fecha }}</td>
            <td>{{ motivosCancelacion.motivoCancelacion }}</td>
            <td v-text="$t('timbradoCatalogosApp.Estatus.' + motivosCancelacion.estatus)">{{ motivosCancelacion.estatus }}</td>
            <td>{{ motivosCancelacion.fechaModificacion }}</td>
            <td>{{ motivosCancelacion.usuario }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MotivosCancelacionView', params: { motivosCancelacionId: motivosCancelacion.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'MotivosCancelacionEdit', params: { motivosCancelacionId: motivosCancelacion.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(motivosCancelacion)"
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
          id="timbradoCatalogosApp.motivosCancelacion.delete.question"
          data-cy="motivosCancelacionDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p
          id="jhi-delete-motivosCancelacion-heading"
          v-text="$t('timbradoCatalogosApp.motivosCancelacion.delete.question', { id: removeId })"
        >
          Are you sure you want to delete this Motivos Cancelacion?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-motivosCancelacion"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMotivosCancelacion()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./motivos-cancelacion.component.ts"></script>
