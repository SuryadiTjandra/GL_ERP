<template>
  <div>
  <b-form-row>
    <b-col cols="4">
      <b-form-group label="No. Penerimaan" horizontal label-size="sm" label-text-align="right">
        <b-form-input placeholder="Tidak perlu diisi, akan diisi otomatis oleh sistem" type="number" size="sm"
          v-model="formItem.documentNumber"
          :readOnly="!editable"
          >
        </b-form-input>
      </b-form-group>
    </b-col>  <b-col cols="4">
      <b-form-group label="Unit Usaha" horizontal label-size="sm" label-text-align="right">
        <ResourceInput  style="display:inline" size="sm" required
          v-model="formItem.companyId"
          :readOnly="!editable"
          :resourceMetadata="{
            apiUrl:'/api/companies',
            dataPath:'companies',
            idPath:'companyId',
            descPath:'description'}"
          >
        </ResourceInput>
      </b-form-group>
    </b-col> <b-col cols="4">
      <b-form-group label="Unit Kerja" horizontal label-size="sm" label-text-align="right">
        <ResourceInput size="sm" required
          v-model="formItem.businessUnitId"
          :readOnly="!editable"
          :resourceMetadata="{
            apiUrl:'/api/businessUnits',
            dataPath:'businessUnits',
            idPath:'businessUnitId',
            descPath:'description'}"
          >
        </ResourceInput>
      </b-form-group>
    </b-col>
  </b-form-row>

  <b-form-row>
    <b-col cols="4">
      <b-form-group label="Supplier" label-size="sm" horizontal label-text-align="right">
        <ResourceInput size="sm" required
          v-model="formItem.vendorId"
          :readOnly="!editable"
          :resourceMetadata="{
            apiUrl:'/api/addresses',
            dataPath:'addresses',
            idPath:'addressNumber',
            descPath:'name'}"
          >
        </ResourceInput>
      </b-form-group>
    </b-col>
    <b-col cols="4">
      <b-form-group label="Tanggal Terima" horizontal label-size="sm" label-text-align="right">
        <b-form-input type="date" required size="sm"
          v-model="formItem.transactionDate"
          :readOnly="!editable">
        </b-form-input>
      </b-form-group>
    </b-col><b-col cols="4">
      <b-form-group label="No. D.O." horizontal label-size="sm" label-text-align="right">
        <b-form-input type="text" required size="sm" placeholder="Nomor Delivery Order"
          v-model="formItem.customerOrderNumber"
          :readOnly="!editable">
        </b-form-input>
      </b-form-group>
    </b-col>
  </b-form-row>

  <b-form-row>
    <b-col cols="4">
      <b-form-group label="Keterangan" label-size="sm" horizontal label-text-align="right">
        <b-input type="text" size="sm" placeholder="Keterangan"
          :readOnly="!editable"
          v-model="formItem.description">
        </b-input>
      </b-form-group>
    </b-col>
  </b-form-row>
</div>
</template>

<script>
import ResourceInput from "baseComponents/inputs/ResourceInput";

export default {
  components: {ResourceInput},
  props: {
    formItem: Object,
    editable: Boolean
  }
}
</script>
