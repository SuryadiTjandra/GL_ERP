<template>

  <b-modal :visible="visible"  size="lg" no-close-on-backdrop
    @change="$emit('update:visible', $event)"
    @ok="onOk"
    @show="onShow">
    <template v-for="order in filteredOrders">
      {{order.documentNumber}} on {{order.orderDate}}
      <b-table :items="order.details | excludeClosed | excludeExcluded(excludedIds)" :fields="fields" small>
        <template slot="checkbox" slot-scope="data">
          <b-form-checkbox
            :checked="selectedItems.includes(data.item)"
            @change="onItemSelect($event, data.item)">
          </b-form-checkbox>
        </template>
      </b-table>
    </template>
    <b-table v-if="filteredOrders.length == 0" :items="[]" showEmpty></b-table>
  </b-modal>
</template>

<script>
import _isEqual from "lodash/isEqual";
import _get from "lodash/get";

export default {
  props: {
    param: {
      type:Object,
      default(){ return {}; }
    },
    excluded:{
      type: Array,
      default(){return []; }
    },
    apiUrl: {
      type: String,
      required: true
    },
    apiResultPath: {
      type: String,
      required: true
    },
    fields: {
      type: Array,
      default(){ return  ["checkbox", "itemDescription", "openQuantity"] }
    },
    visible: Boolean
  },
  data: function(){
    return {
      orders: [],

      selectedItems: []
    }
  },
  computed: {
    excludedIds(){
      return this.excluded.map(ex => ({
        documentNumber: ex.orderNumber,
        documentType: ex.orderType,
        sequence: ex.orderSequence
      }));
    },
    filteredOrders(){
      return this.orders.filter(order => {
        let details = order.details;
        details = this.$options.filters.excludeClosed(details);
        details = this.$options.filters.excludeExcluded(details, this.excludedIds);
        return details.length > 0;
      })
    }
  },
  methods: {
    onOk(){
      this.$emit('select-orders', this.selectedItems);
      this.selectedItems = [];
    },
    onShow(){
      this.loadData();
    },
    onItemSelect(checked, selectedItem){
      if (checked){
        this.selectedItems.push(selectedItem);
      } else {
        let index = this.selectedItems.indexOf(selectedItem);
        this.selectedItems.splice(0, 1);
      }
    },
    loadData(){
      let paramStr = Object.keys(this.param).map(key => key + '=' + this.param[key]).join('&');
      fetch(this.apiUrl + "?" + paramStr)
        .then(res => res.json())
        .then(res => this.orders = _get(res, this.apiResultPath));
    }
  },
  filters: {
    excludeClosed(orders){
      return orders.filter(order => order.openQuantity > 0)
    },
    excludeExcluded(orders, excludedIds){
      return orders.filter(order => {
        let orderId = {documentNumber: order.documentNumber, documentType: order.documentType, sequence: order.sequence }
        return excludedIds.findIndex(exId => _isEqual(exId, orderId)) < 0;
      })
    }
  }
}
</script>
