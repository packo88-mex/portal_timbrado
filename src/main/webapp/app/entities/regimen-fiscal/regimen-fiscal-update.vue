<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="timbradoCatalogosApp.regimenFiscal.home.createOrEditLabel"
          data-cy="RegimenFiscalCreateUpdateHeading"
          v-text="$t('timbradoCatalogosApp.regimenFiscal.home.createOrEditLabel')"
        >
          Create or edit a RegimenFiscal
        </h2>
        <div>
          <div class="form-group" v-if="regimenFiscal.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="regimenFiscal.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.regimenFiscal.fecha')" for="regimen-fiscal-fecha"
              >Fecha</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="regimen-fiscal-fecha"
                  v-model="$v.regimenFiscal.fecha.$model"
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
                id="regimen-fiscal-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.regimenFiscal.fecha.$invalid, invalid: $v.regimenFiscal.fecha.$invalid }"
                v-model="$v.regimenFiscal.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('timbradoCatalogosApp.regimenFiscal.regimenFiscal')"
              for="regimen-fiscal-regimenFiscal"
              >Regimen Fiscal</label
            >
            <input
              type="text"
              class="form-control"
              name="regimenFiscal"
              id="regimen-fiscal-regimenFiscal"
              data-cy="regimenFiscal"
              :class="{ valid: !$v.regimenFiscal.regimenFiscal.$invalid, invalid: $v.regimenFiscal.regimenFiscal.$invalid }"
              v-model="$v.regimenFiscal.regimenFiscal.$model"
            />
            <div v-if="$v.regimenFiscal.regimenFiscal.$anyDirty && $v.regimenFiscal.regimenFiscal.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.regimenFiscal.regimenFiscal.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.regimenFiscal.estatus')" for="regimen-fiscal-estatus"
              >Estatus</label
            >
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.regimenFiscal.estatus.$invalid, invalid: $v.regimenFiscal.estatus.$invalid }"
              v-model="$v.regimenFiscal.estatus.$model"
              id="regimen-fiscal-estatus"
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
              v-text="$t('timbradoCatalogosApp.regimenFiscal.fechaModificacion')"
              for="regimen-fiscal-fechaModificacion"
              >Fecha Modificacion</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="regimen-fiscal-fechaModificacion"
                  v-model="$v.regimenFiscal.fechaModificacion.$model"
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
                id="regimen-fiscal-fechaModificacion"
                data-cy="fechaModificacion"
                type="text"
                class="form-control"
                name="fechaModificacion"
                :class="{ valid: !$v.regimenFiscal.fechaModificacion.$invalid, invalid: $v.regimenFiscal.fechaModificacion.$invalid }"
                v-model="$v.regimenFiscal.fechaModificacion.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.regimenFiscal.usuario')" for="regimen-fiscal-usuario"
              >Usuario</label
            >
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="regimen-fiscal-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.regimenFiscal.usuario.$invalid, invalid: $v.regimenFiscal.usuario.$invalid }"
              v-model="$v.regimenFiscal.usuario.$model"
            />
            <div v-if="$v.regimenFiscal.usuario.$anyDirty && $v.regimenFiscal.usuario.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.regimenFiscal.usuario.maxLength"
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
            :disabled="$v.regimenFiscal.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./regimen-fiscal-update.component.ts"></script>
