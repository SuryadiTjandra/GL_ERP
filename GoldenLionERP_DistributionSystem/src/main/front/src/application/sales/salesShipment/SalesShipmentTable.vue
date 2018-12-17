<template>
  <ResourceTable
    :apiUrl="apiUrl"
    resultPath="_embedded.salesShipments"
    :loadOnCreate="true"
    :fields="['actions', 'companyId', 'documentNumber', 'description']"
    :primaryKeys="['companyId', 'documentNumber', 'documentType', 'sequence']"
    :itemEditable="true"
    :itemDeletable="false"
    @create-clicked="$emit('create-clicked')"
    @edit-clicked="$emit('edit-clicked', $event)"
    @view-clicked="$emit('view-clicked', $event)"
  >

  </ResourceTable>
</template>

<script>
import ResourceTable from "baseComponents/tables/ResourceTable";

export default {
  components: {ResourceTable},
  props: {
    apiUrl: String
  }
}

</script>
