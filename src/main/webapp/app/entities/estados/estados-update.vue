<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="timbradoCatalogosApp.estados.home.createOrEditLabel"
          data-cy="EstadosCreateUpdateHeading"
          v-text="$t('timbradoCatalogosApp.estados.home.createOrEditLabel')"
        >
          Create or edit a Estados
        </h2>
        <div>
          <div class="form-group" v-if="estados.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="estados.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.estados.fecha')" for="estados-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="estados-fecha"
                  v-model="$v.estados.fecha.$model"
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
                id="estados-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.estados.fecha.$invalid, invalid: $v.estados.fecha.$invalid }"
                v-model="$v.estados.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.estados.pais')" for="estados-pais">Pais</label>
            <input
              type="text"
              class="form-control"
              name="pais"
              id="estados-pais"
              data-cy="pais"
              :class="{ valid: !$v.estados.pais.$invalid, invalid: $v.estados.pais.$invalid }"
              v-model="$v.estados.pais.$model"
            />
            <div v-if="$v.estados.pais.$anyDirty && $v.estados.pais.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.estados.pais.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.estados.estado')" for="estados-estado">Estado</label>
            <input
              type="text"
              class="form-control"
              name="estado"
              id="estados-estado"
              data-cy="estado"
              :class="{ valid: !$v.estados.estado.$invalid, invalid: $v.estados.estado.$invalid }"
              v-model="$v.estados.estado.$model"
            />
            <div v-if="$v.estados.estado.$anyDirty && $v.estados.estado.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.estados.estado.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.estados.descripcionEstado')" for="estados-descripcionEstado"
              >Descripcion Estado</label
            >
            <input
              type="text"
              class="form-control"
              name="descripcionEstado"
              id="estados-descripcionEstado"
              data-cy="descripcionEstado"
              :class="{ valid: !$v.estados.descripcionEstado.$invalid, invalid: $v.estados.descripcionEstado.$invalid }"
              v-model="$v.estados.descripcionEstado.$model"
            />
            <div v-if="$v.estados.descripcionEstado.$anyDirty && $v.estados.descripcionEstado.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.estados.descripcionEstado.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 150 })"
              >
                This field cannot be longer than 150 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.estados.estatus')" for="estados-estatus">Estatus</label>
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.estados.estatus.$invalid, invalid: $v.estados.estatus.$invalid }"
              v-model="$v.estados.estatus.$model"
              id="estados-estatus"
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
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.estados.fechaModificacion')" for="estados-fechaModificacion"
              >Fecha Modificacion</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="estados-fechaModificacion"
                  v-model="$v.estados.fechaModificacion.$model"
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
                id="estados-fechaModificacion"
                data-cy="fechaModificacion"
                type="text"
                class="form-control"
                name="fechaModificacion"
                :class="{ valid: !$v.estados.fechaModificacion.$invalid, invalid: $v.estados.fechaModificacion.$invalid }"
                v-model="$v.estados.fechaModificacion.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('timbradoCatalogosApp.estados.usuario')" for="estados-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="estados-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.estados.usuario.$invalid, invalid: $v.estados.usuario.$invalid }"
              v-model="$v.estados.usuario.$model"
            />
            <div v-if="$v.estados.usuario.$anyDirty && $v.estados.usuario.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.estados.usuario.maxLength"
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
            :disabled="$v.estados.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./estados-update.component.ts"></script>
