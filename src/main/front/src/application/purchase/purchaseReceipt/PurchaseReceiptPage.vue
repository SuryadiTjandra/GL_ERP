<template>
  <BasePage :formVisible="formVisible">
    <template slot="table">
      <PurchaseReceiptTable
        :apiUrl="apiUrl"
        v-on="standardTableListeners">

      </PurchaseReceiptTable>
    </template>

    <template slot="form">
      <div>HI</div>
    </template>
  </BasePage>
</template>

<script>
import BasePage from 'baseComponents/pages/TableAndFormPage';
import pageMixin from 'baseComponents/pages/TableAndFormPageMixin';
import PurchaseReceiptTable from './PurchaseReceiptTable';

export default {
  components: {BasePage, PurchaseReceiptTable},
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
