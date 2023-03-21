<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="timbradoCatalogosApp.estatusCfdi.home.createOrEditLabel"
          data-cy="EstatusCfdiCreateUpdateHeading"
          v-text="$t('timbradoCatalogosApp.estatusCfdi.home.createOrEditLabel')"
        >
          Create or edit a EstatusCfdi
        </h2>
        <div>
          <div class="form-group" v-if="estatusCfdi.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="estatusCfdi.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.estatusCfdi.fecha')" for="estatus-cfdi-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="estatus-cfdi-fecha"
                  v-model="$v.estatusCfdi.fecha.$model"
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
                id="estatus-cfdi-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.estatusCfdi.fecha.$invalid, invalid: $v.estatusCfdi.fecha.$invalid }"
                v-model="$v.estatusCfdi.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('timbradoCatalogosApp.estatusCfdi.descripcionEstatusCfdi')"
              for="estatus-cfdi-descripcionEstatusCfdi"
              >Descripcion Estatus Cfdi</label
            >
            <input
              type="text"
              class="form-control"
              name="descripcionEstatusCfdi"
              id="estatus-cfdi-descripcionEstatusCfdi"
              data-cy="descripcionEstatusCfdi"
              :class="{ valid: !$v.estatusCfdi.descripcionEstatusCfdi.$invalid, invalid: $v.estatusCfdi.descripcionEstatusCfdi.$invalid }"
              v-model="$v.estatusCfdi.descripcionEstatusCfdi.$model"
            />
            <div v-if="$v.estatusCfdi.descripcionEstatusCfdi.$anyDirty && $v.estatusCfdi.descripcionEstatusCfdi.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.estatusCfdi.descripcionEstatusCfdi.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.estatusCfdi.estatus')" for="estatus-cfdi-estatus"
              >Estatus</label
            >
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.estatusCfdi.estatus.$invalid, invalid: $v.estatusCfdi.estatus.$invalid }"
              v-model="$v.estatusCfdi.estatus.$model"
              id="estatus-cfdi-estatus"
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
              v-text="$t('timbradoCatalogosApp.estatusCfdi.fechaModificacion')"
              for="estatus-cfdi-fechaModificacion"
              >Fecha Modificacion</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="estatus-cfdi-fechaModificacion"
                  v-model="$v.estatusCfdi.fechaModificacion.$model"
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
                id="estatus-cfdi-fechaModificacion"
                data-cy="fechaModificacion"
                type="text"
                class="form-control"
                name="fechaModificacion"
                :class="{ valid: !$v.estatusCfdi.fechaModificacion.$invalid, invalid: $v.estatusCfdi.fechaModificacion.$invalid }"
                v-model="$v.estatusCfdi.fechaModificacion.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.estatusCfdi.usuario')" for="estatus-cfdi-usuario"
              >Usuario</label
            >
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="estatus-cfdi-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.estatusCfdi.usuario.$invalid, invalid: $v.estatusCfdi.usuario.$invalid }"
              v-model="$v.estatusCfdi.usuario.$model"
            />
            <div v-if="$v.estatusCfdi.usuario.$anyDirty && $v.estatusCfdi.usuario.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.estatusCfdi.usuario.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 30 })"
              >
                This field cannot be longer than 30 characters.
              </small>
            </div>
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
            :disabled="$v.estatusCfdi.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./estatus-cfdi-update.component.ts"></script>
