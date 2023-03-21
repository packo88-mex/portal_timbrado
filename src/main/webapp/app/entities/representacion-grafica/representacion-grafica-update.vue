<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="timbradoCatalogosApp.representacionGrafica.home.createOrEditLabel"
          data-cy="RepresentacionGraficaCreateUpdateHeading"
          v-text="$t('timbradoCatalogosApp.representacionGrafica.home.createOrEditLabel')"
        >
          Create or edit a RepresentacionGrafica
        </h2>
        <div>
          <div class="form-group" v-if="representacionGrafica.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="representacionGrafica.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('timbradoCatalogosApp.representacionGrafica.fecha')"
              for="representacion-grafica-fecha"
              >Fecha</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="representacion-grafica-fecha"
                  v-model="$v.representacionGrafica.fecha.$model"
                  name="fecha"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="representacion-grafica-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.representacionGrafica.fecha.$invalid, invalid: $v.representacionGrafica.fecha.$invalid }"
                v-model="$v.representacionGrafica.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('timbradoCatalogosApp.representacionGrafica.representacionGrafica')"
              for="representacion-grafica-representacionGrafica"
              >Representacion Grafica</label
            >
            <input
              type="text"
              class="form-control"
              name="representacionGrafica"
              id="representacion-grafica-representacionGrafica"
              data-cy="representacionGrafica"
              :class="{
                valid: !$v.representacionGrafica.representacionGrafica.$invalid,
                invalid: $v.representacionGrafica.representacionGrafica.$invalid,
              }"
              v-model="$v.representacionGrafica.representacionGrafica.$model"
            />
            <div v-if="$v.representacionGrafica.representacionGrafica.$anyDirty && $v.representacionGrafica.representacionGrafica.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.representacionGrafica.representacionGrafica.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('timbradoCatalogosApp.representacionGrafica.estatus')"
              for="representacion-grafica-estatus"
              >Estatus</label
            >
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.representacionGrafica.estatus.$invalid, invalid: $v.representacionGrafica.estatus.$invalid }"
              v-model="$v.representacionGrafica.estatus.$model"
              id="representacion-grafica-estatus"
              data-cy="estatus"
            >
              <option
                v-for="estatus in estatusValues"
                :key="estatus"
                v-bind:value="estatus"
                v-bind:label="$t('timbradoCatalogosApp.Estatus.' + estatus)"
              >
                {{ estatus }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('timbradoCatalogosApp.representacionGrafica.fechaModificacion')"
              for="representacion-grafica-fechaModificacion"
              >Fecha Modificacion</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="representacion-grafica-fechaModificacion"
                  v-model="$v.representacionGrafica.fechaModificacion.$model"
                  name="fechaModificacion"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="representacion-grafica-fechaModificacion"
                data-cy="fechaModificacion"
                type="text"
                class="form-control"
                name="fechaModificacion"
                :class="{
                  valid: !$v.representacionGrafica.fechaModificacion.$invalid,
                  invalid: $v.representacionGrafica.fechaModificacion.$invalid,
                }"
                v-model="$v.representacionGrafica.fechaModificacion.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('timbradoCatalogosApp.representacionGrafica.usuario')"
              for="representacion-grafica-usuario"
              >Usuario</label
            >
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="representacion-grafica-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.representacionGrafica.usuario.$invalid, invalid: $v.representacionGrafica.usuario.$invalid }"
              v-model="$v.representacionGrafica.usuario.$model"
            />
            <div v-if="$v.representacionGrafica.usuario.$anyDirty && $v.representacionGrafica.usuario.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.representacionGrafica.usuario.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 30 })"
              >
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('timbradoCatalogosApp.representacionGrafica.tiporecibo')"
              for="representacion-grafica-tiporecibo"
              >Tiporecibo</label
            >
            <select
              class="form-control"
              id="representacion-grafica-tiporecibo"
              data-cy="tiporecibo"
              name="tiporecibo"
              v-model="representacionGrafica.tiporecibo"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  representacionGrafica.tiporecibo && tipoReciboOption.id === representacionGrafica.tiporecibo.id
                    ? representacionGrafica.tiporecibo
                    : tipoReciboOption
                "
                v-for="tipoReciboOption in tipoRecibos"
                :key="tipoReciboOption.id"
              >
                {{ tipoReciboOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.representacionGrafica.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./representacion-grafica-update.component.ts"></script>
