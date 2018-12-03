<template>
  <b-table :fields="fields" :items="detailData" small responsive showEmpty>
    <template slot="action" slot-scope="detail">
      Action
    </template>

    <template slot="itemInfo" slot-scope="detail">
        <span>{{detail.item.itemDescription}}</span>
    </template>

    <template slot="quantityInfo" slot-scope="detail">
      {{detail.item.quantity}} {{detail.item.unitOfMeasure}}
    </template>
  </b-table>
</template>

<script>
export default {
  props: {
    details: {
      type: Array
    }
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
  }
}
</script>
