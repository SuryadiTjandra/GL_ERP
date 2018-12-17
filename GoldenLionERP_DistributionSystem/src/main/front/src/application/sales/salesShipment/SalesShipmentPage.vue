<template>
  <BasePage :formVisible="formVisible">
    <template slot="table">
      <SalesShipmentTable
        :apiUrl="apiUrl"
        v-on="standardTableListeners">

      </SalesShipmentTable>
    </template>

    <template slot="form">
      <SalesShipmentForm
        v-bind="standardFormProps"
        v-on="standardFormListeners">
      </SalesShipmentForm>
    </template>
  </BasePage>
</template>

<script>
import BasePage from "baseComponents/pages/TableAndFormPage";
import pageMixin from "baseComponents/pages/TableAndFormPageMixin";
import SalesShipmentTable from "./SalesShipmentTable";
import SalesShipmentForm from "./SalesShipmentForm";

export default {
  components: {BasePage, SalesShipmentTable, SalesShipmentForm},
  mixins: [pageMixin]
}
</script>
