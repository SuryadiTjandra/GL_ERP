<template>
  <b-table :fields="fields" :items="detailDataWithVariant" small responsive showEmpty>
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

    <template slot="serialOrLotNumbers" slot-scope="detail">
      <template v-if="useSerialOrLotNumbers[detail.index] && editable && !detail.item.voided">
        <template v-for="n in Math.max(detail.item.quantity, detail.item.serialOrLotNumbers.length)">
          <b-input-group>
            <b-input type="text" size="sm"
              v-model="detail.item.serialOrLotNumbers[n-1]"
              :state="n <= detail.item.quantity
                      && detail.item.serialOrLotNumbers[n-1] != null
                      && detail.item.serialOrLotNumbers[n-1].length > 0">
            </b-input>
            <b-input-group-append v-if="n > detail.item.quantity">
              <VoidButton @void-click="onRemoveSerialNo(detail.item, n-1)"></VoidButton>
            </b-input-group-append>
          </b-input-group>
        </template>
      </template>
      <template v-else-if="useSerialOrLotNumbers[detail.index]">{{detail.item.serialOrLotNumbers}}</template>
      <template v-else>N/A</template>
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
      type: Array,
      required: true
    },
    editable: Boolean
  },
  data: function(){
    return {
      fields: [
        {
          key:'action', label:'', thStyle:{width:'3%'}
        }, {
          key:'itemInfo', label: 'Item', thStyle:{width:'45%'}
        }, {
          key:'quantityInfo', label: 'Qty', thStyle: {width:'40%'}
        }, {
          key:'serialOrLotNumbers', label: 'Serial/Lot Numbers'
        }
      ],

      useSerialOrLotNumbers: []
    }
  },
  computed: {
    detailData: {
      get: function(){
        return this.details;
      },
      set: function(details){
        this.$emit("update:details", details);
      }
    },
    detailDataWithVariant(){
      if (this.detailData == null)
        return [];
      return this.detailData.map(detail => Object.assign(detail, { _rowVariant: detail.voided? "danger" : ''} ));
    }
  },
  methods: {
    onVoidDetail(item, index){
      if (item.inputUserId == null || item.inputDateTime == null){
        this.detailData.splice(index, 1);
      } else {
        if (confirm("Void transaksi ini?")){
          this.$emit("void-detail", item);
        }
      }
    },
    onAddNewRow(){
      //alert("new row");
      this.$emit("new-detail")
    },
    onRemoveSerialNo(detail, index){
      detail.serialOrLotNumbers.splice(index, 1);
    },
    onAddSerialNo(detail, newSerialNo){
      if (newSerialNo != null && newSerialNo.length > 0)
        detail.serialOrLotNumbers.push(newSerialNo);
    }
  },
  watch: {
    details: async function(newDetails, oldDetails){
      for (let i = 0; i < newDetails.length; i++){
        if (oldDetails!= null && oldDetails[i] != null && newDetails[i].itemCode === oldDetails[i].itemCode)
          continue;

        let item = await fetch(newDetails[i]._links.item.href);
        item = await item.json();
        this.$set(this.useSerialOrLotNumbers, i, item.serialNumberRequired);
        //this.useSerialOrLotNumbers[i] = item.serialNumberRequired;
      }
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
