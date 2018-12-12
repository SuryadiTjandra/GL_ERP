<template>
  <BasicWireframe>
    <h4>Pengiriman Barang</h4>
    <SalesShipmentPage
        :default-item="defaultItem"
        :apiUrl="apiUrl">
    </SalesShipmentPage>
  </BasicWireframe>
</template>

<script>
import SalesShipmentPage from './SalesShipmentPage'
import BasicWireframe from 'baseComponents/wireframes/BasicWireframe'

export default {
  props: ['defaultItem'],
  data(){
    return {
      apiUrl: "/api/salesShipments/"
    }
  },
  components: {SalesShipmentPage, BasicWireframe}
}
</script>
