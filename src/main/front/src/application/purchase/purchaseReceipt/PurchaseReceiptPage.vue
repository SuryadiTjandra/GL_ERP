<template>
  <BasePage :formVisible="formVisible">
    <template slot="table">
      <PurchaseReceiptTable
        :apiUrl="apiUrl"
        v-on="standardTableListeners">

      </PurchaseReceiptTable>
    </template>

    <template slot="form">
      <PurchaseReceiptForm
        v-bind="standardFormProps"
        v-on="standardFormListeners">

      </PurchaseReceiptForm>
    </template>
  </BasePage>
</template>

<script>
import BasePage from 'baseComponents/pages/TableAndFormPage';
import pageMixin from 'baseComponents/pages/TableAndFormPageMixin';
import PurchaseReceiptTable from './PurchaseReceiptTable';
import PurchaseReceiptForm from "./PurchaseReceiptForm";

export default {
  components: {BasePage, PurchaseReceiptTable, PurchaseReceiptForm},
  mixins: [pageMixin],
  props: {
    defaultItem: Object
  },
  data: function(){
    return {

    }
  }
}
</script>
