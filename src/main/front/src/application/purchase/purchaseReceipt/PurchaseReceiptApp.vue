<template>
  <BasicWireframe>
    <h4>PurchaseReceipt</h4>
    <PurchaseReceiptPage
        :default-item="defaultItem"
        :apiUrl="apiUrl">
    </PurchaseReceiptPage>
  </BasicWireframe>
</template>

<script>
import PurchaseReceiptPage from './PurchaseReceiptPage'
import BasicWireframe from 'baseComponents/wireframes/BasicWireframe'

export default {
  props: ['defaultItem'],
  data(){
    return {
      apiUrl: "/api/purchaseReceipts/"
    }
  },
  components: {PurchaseReceiptPage, BasicWireframe}
}
</script>
