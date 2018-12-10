<template>
  <b-table :fields="fields" :items="detailData" small responsive showEmpty>
    <template slot="action" slot-scope="detail">
      <VoidButton v-if="editable && !detail.item.voided"
        @void-click="onVoidDetail(detail.item, detail.index)">
      </VoidButton>
    </template>

    <template slot="itemInfo" slot-scope="detail">
        <span>{{detail.item.itemDescription}}</span>
    </template>

    <template slot="quantityInfo" slot-scope="detail">
      <template v-if="editable && !detail.item.voided">
        <b-input type="number" size="sm"
          :readOnly="!editable || detail.item.voided"
          v-model="detail.item.quantity"
          class="half-length">
        </b-input>
        <DataCodeInput productCode="00" systemCode="UM" size="sm"
          :readOnly="!editable || detail.item.voided"
          v-model="detail.item.unitOfMeasure"
          class="half-length">
        </DataCodeInput>
      </template>
      <template v-else>
        {{detail.item.quantity}} {{detail.item.unitOfMeasure}}
      </template>
    </template>

    <template slot="bottom-row" slot-scope="row">
      <td v-for="field in row.fields">
        <template v-if="field.key === 'action'">
          <b-button variant="outline-success" @click="onAddNewRow" size="sm"
            v-if="editable"
            style="padding:0; border-color:transparent;">
            <span class="oi oi-plus"></span>
          </b-button>
        </template>
      </td>
    </template>

  </b-table>
</template>

<script>
import DataCodeInput from "baseComponents/inputs/DataCodeInput";
import VoidButton from "baseComponents/buttons/VoidButton";

export default {
  components: { VoidButton, DataCodeInput},
  props: {
    details: {
      type: Array
    },
    editable: Boolean
  },
  data: function(){
    return {
      fields: [
        {
          key:'action', label:'', thStyle:{width:'3%'}
        }, {
          key:'itemInfo', label: 'Item', thStyle:{width:'32%'}
        }, {
          key:'quantityInfo', label: 'Qty', thStyle:{width:'15%'}
        }
      ]
    }
  },
  computed: {
    detailData: function(){
      return this.details;
    }
  },
  methods: {
    onVoidDetail(item){
      alert(item + " voided")
    },
    onAddNewRow(){
      //alert("new row");
      this.$emit("new-detail")
    }
  }
}
</script>

<style scoped>
.half-length {
  display: inline;
  width: 45%;
}

</style>
