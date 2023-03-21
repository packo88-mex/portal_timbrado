<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="timbradoCatalogosApp.tipoCfdi.home.createOrEditLabel"
          data-cy="TipoCfdiCreateUpdateHeading"
          v-text="$t('timbradoCatalogosApp.tipoCfdi.home.createOrEditLabel')"
        >
          Create or edit a TipoCfdi
        </h2>
        <div>
          <div class="form-group" v-if="tipoCfdi.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="tipoCfdi.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.tipoCfdi.fecha')" for="tipo-cfdi-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="tipo-cfdi-fecha"
                  v-model="$v.tipoCfdi.fecha.$model"
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
                id="tipo-cfdi-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.tipoCfdi.fecha.$invalid, invalid: $v.tipoCfdi.fecha.$invalid }"
                v-model="$v.tipoCfdi.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.tipoCfdi.tipoCfdi')" for="tipo-cfdi-tipoCfdi"
              >Tipo Cfdi</label
            >
            <input
              type="text"
              class="form-control"
              name="tipoCfdi"
              id="tipo-cfdi-tipoCfdi"
              data-cy="tipoCfdi"
              :class="{ valid: !$v.tipoCfdi.tipoCfdi.$invalid, invalid: $v.tipoCfdi.tipoCfdi.$invalid }"
              v-model="$v.tipoCfdi.tipoCfdi.$model"
            />
            <div v-if="$v.tipoCfdi.tipoCfdi.$anyDirty && $v.tipoCfdi.tipoCfdi.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.tipoCfdi.tipoCfdi.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.tipoCfdi.estatus')" for="tipo-cfdi-estatus">Estatus</label>
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.tipoCfdi.estatus.$invalid, invalid: $v.tipoCfdi.estatus.$invalid }"
              v-model="$v.tipoCfdi.estatus.$model"
              id="tipo-cfdi-estatus"
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
              v-text="$t('timbradoCatalogosApp.tipoCfdi.fechaModificacion')"
              for="tipo-cfdi-fechaModificacion"
              >Fecha Modificacion</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="tipo-cfdi-fechaModificacion"
                  v-model="$v.tipoCfdi.fechaModificacion.$model"
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
                id="tipo-cfdi-fechaModificacion"
                data-cy="fechaModificacion"
                type="text"
                class="form-control"
                name="fechaModificacion"
                :class="{ valid: !$v.tipoCfdi.fechaModificacion.$invalid, invalid: $v.tipoCfdi.fechaModificacion.$invalid }"
                v-model="$v.tipoCfdi.fechaModificacion.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.tipoCfdi.usuario')" for="tipo-cfdi-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="tipo-cfdi-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.tipoCfdi.usuario.$invalid, invalid: $v.tipoCfdi.usuario.$invalid }"
              v-model="$v.tipoCfdi.usuario.$model"
            />
            <div v-if="$v.tipoCfdi.usuario.$anyDirty && $v.tipoCfdi.usuario.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.tipoCfdi.usuario.maxLength"
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
            :disabled="$v.tipoCfdi.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./tipo-cfdi-update.component.ts"></script>
