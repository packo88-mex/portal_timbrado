<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="timbradoCatalogosApp.motivosCancelacion.home.createOrEditLabel"
          data-cy="MotivosCancelacionCreateUpdateHeading"
          v-text="$t('timbradoCatalogosApp.motivosCancelacion.home.createOrEditLabel')"
        >
          Create or edit a MotivosCancelacion
        </h2>
        <div>
          <div class="form-group" v-if="motivosCancelacion.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="motivosCancelacion.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.motivosCancelacion.fecha')" for="motivos-cancelacion-fecha"
              >Fecha</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="motivos-cancelacion-fecha"
                  v-model="$v.motivosCancelacion.fecha.$model"
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
                id="motivos-cancelacion-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.motivosCancelacion.fecha.$invalid, invalid: $v.motivosCancelacion.fecha.$invalid }"
                v-model="$v.motivosCancelacion.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('timbradoCatalogosApp.motivosCancelacion.motivoCancelacion')"
              for="motivos-cancelacion-motivoCancelacion"
              >Motivo Cancelacion</label
            >
            <input
              type="text"
              class="form-control"
              name="motivoCancelacion"
              id="motivos-cancelacion-motivoCancelacion"
              data-cy="motivoCancelacion"
              :class="{
                valid: !$v.motivosCancelacion.motivoCancelacion.$invalid,
                invalid: $v.motivosCancelacion.motivoCancelacion.$invalid,
              }"
              v-model="$v.motivosCancelacion.motivoCancelacion.$model"
            />
            <div v-if="$v.motivosCancelacion.motivoCancelacion.$anyDirty && $v.motivosCancelacion.motivoCancelacion.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.motivosCancelacion.motivoCancelacion.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('timbradoCatalogosApp.motivosCancelacion.estatus')"
              for="motivos-cancelacion-estatus"
              >Estatus</label
            >
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.motivosCancelacion.estatus.$invalid, invalid: $v.motivosCancelacion.estatus.$invalid }"
              v-model="$v.motivosCancelacion.estatus.$model"
              id="motivos-cancelacion-estatus"
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
              v-text="$t('timbradoCatalogosApp.motivosCancelacion.fechaModificacion')"
              for="motivos-cancelacion-fechaModificacion"
              >Fecha Modificacion</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="motivos-cancelacion-fechaModificacion"
                  v-model="$v.motivosCancelacion.fechaModificacion.$model"
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
                id="motivos-cancelacion-fechaModificacion"
                data-cy="fechaModificacion"
                type="text"
                class="form-control"
                name="fechaModificacion"
                :class="{
                  valid: !$v.motivosCancelacion.fechaModificacion.$invalid,
                  invalid: $v.motivosCancelacion.fechaModificacion.$invalid,
                }"
                v-model="$v.motivosCancelacion.fechaModificacion.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('timbradoCatalogosApp.motivosCancelacion.usuario')"
              for="motivos-cancelacion-usuario"
              >Usuario</label
            >
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="motivos-cancelacion-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.motivosCancelacion.usuario.$invalid, invalid: $v.motivosCancelacion.usuario.$invalid }"
              v-model="$v.motivosCancelacion.usuario.$model"
            />
            <div v-if="$v.motivosCancelacion.usuario.$anyDirty && $v.motivosCancelacion.usuario.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.motivosCancelacion.usuario.maxLength"
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
            :disabled="$v.motivosCancelacion.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./motivos-cancelacion-update.component.ts"></script>
