<template>
  <div>
    <b-dropdown-item to="/regimen-fiscal">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.regimenFiscal')">Regimen Fiscal</span>
    </b-dropdown-item>
    <b-dropdown-item to="/estados">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.estados')">Estados</span>
    </b-dropdown-item>
    <b-dropdown-item to="/estatus-cfdi">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.estatusCfdi')">Estatus Cfdi</span>
    </b-dropdown-item>
    <b-dropdown-item to="/motivos-cancelacion">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.motivosCancelacion')">Motivos Cancelacion</span>
    </b-dropdown-item>
    <b-dropdown-item to="/tipo-cfdi">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.tipoCfdi')">Tipo Cfdi</span>
    </b-dropdown-item>
    <b-dropdown-item to="/tipo-recibo">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.tipoRecibo')">Tipo Recibo</span>
    </b-dropdown-item>
    <b-dropdown-item to="/representacion-grafica">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.representacionGrafica')">Representacion Grafica</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
