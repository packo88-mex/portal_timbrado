<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="timbradoCatalogosApp.tipoRecibo.home.createOrEditLabel"
          data-cy="TipoReciboCreateUpdateHeading"
          v-text="$t('timbradoCatalogosApp.tipoRecibo.home.createOrEditLabel')"
        >
          Create or edit a TipoRecibo
        </h2>
        <div>
          <div class="form-group" v-if="tipoRecibo.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="tipoRecibo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.tipoRecibo.fecha')" for="tipo-recibo-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="tipo-recibo-fecha"
                  v-model="$v.tipoRecibo.fecha.$model"
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
                id="tipo-recibo-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.tipoRecibo.fecha.$invalid, invalid: $v.tipoRecibo.fecha.$invalid }"
                v-model="$v.tipoRecibo.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.tipoRecibo.tipoRecibo')" for="tipo-recibo-tipoRecibo"
              >Tipo Recibo</label
            >
            <input
              type="text"
              class="form-control"
              name="tipoRecibo"
              id="tipo-recibo-tipoRecibo"
              data-cy="tipoRecibo"
              :class="{ valid: !$v.tipoRecibo.tipoRecibo.$invalid, invalid: $v.tipoRecibo.tipoRecibo.$invalid }"
              v-model="$v.tipoRecibo.tipoRecibo.$model"
            />
            <div v-if="$v.tipoRecibo.tipoRecibo.$anyDirty && $v.tipoRecibo.tipoRecibo.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.tipoRecibo.tipoRecibo.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 50 })"
              >
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.tipoRecibo.clave')" for="tipo-recibo-clave">Clave</label>
            <input
              type="text"
              class="form-control"
              name="clave"
              id="tipo-recibo-clave"
              data-cy="clave"
              :class="{ valid: !$v.tipoRecibo.clave.$invalid, invalid: $v.tipoRecibo.clave.$invalid }"
              v-model="$v.tipoRecibo.clave.$model"
            />
            <div v-if="$v.tipoRecibo.clave.$anyDirty && $v.tipoRecibo.clave.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.tipoRecibo.clave.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 10 })"
              >
                This field cannot be longer than 10 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.tipoRecibo.estatus')" for="tipo-recibo-estatus"
              >Estatus</label
            >
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.tipoRecibo.estatus.$invalid, invalid: $v.tipoRecibo.estatus.$invalid }"
              v-model="$v.tipoRecibo.estatus.$model"
              id="tipo-recibo-estatus"
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
              v-text="$t('timbradoCatalogosApp.tipoRecibo.fechaModificacion')"
              for="tipo-recibo-fechaModificacion"
              >Fecha Modificacion</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="tipo-recibo-fechaModificacion"
                  v-model="$v.tipoRecibo.fechaModificacion.$model"
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
                id="tipo-recibo-fechaModificacion"
                data-cy="fechaModificacion"
                type="text"
                class="form-control"
                name="fechaModificacion"
                :class="{ valid: !$v.tipoRecibo.fechaModificacion.$invalid, invalid: $v.tipoRecibo.fechaModificacion.$invalid }"
                v-model="$v.tipoRecibo.fechaModificacion.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.tipoRecibo.usuario')" for="tipo-recibo-usuario"
              >Usuario</label
            >
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="tipo-recibo-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.tipoRecibo.usuario.$invalid, invalid: $v.tipoRecibo.usuario.$invalid }"
              v-model="$v.tipoRecibo.usuario.$model"
            />
            <div v-if="$v.tipoRecibo.usuario.$anyDirty && $v.tipoRecibo.usuario.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.tipoRecibo.usuario.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 30 })"
              >
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.tipoRecibo.tipocfdi')" for="tipo-recibo-tipocfdi"
              >Tipocfdi</label
            >
            <select class="form-control" id="tipo-recibo-tipocfdi" data-cy="tipocfdi" name="tipocfdi" v-model="tipoRecibo.tipocfdi">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="tipoRecibo.tipocfdi && tipoCfdiOption.id === tipoRecibo.tipocfdi.id ? tipoRecibo.tipocfdi : tipoCfdiOption"
                v-for="tipoCfdiOption in tipoCfdis"
                :key="tipoCfdiOption.id"
              >
                {{ tipoCfdiOption.id }}
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
            :disabled="$v.tipoRecibo.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./tipo-recibo-update.component.ts"></script>
