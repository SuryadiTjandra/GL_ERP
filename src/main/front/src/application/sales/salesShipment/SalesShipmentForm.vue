<template>
  <StandardFormTemplate
    :submittable="editable"
    :validate="validated"
    @submit="onFormSubmit"
    @cancel="$emit('cancel')">

    <SalesShipmentFormHeader
      :formItem="formItem"
      :editable="editable">
    </SalesShipmentFormHeader>
    <SalesShipmentFormDetailList
      :details="formItem.details"
      :editable="editable && formItem.customerId != null"
      @new-detail="onNewDetail"
      @void-detail="onVoidDetail"
      >
    </SalesShipmentFormDetailList>
    <OrderSelector
      apiUrl="/api/salesOrders/"
      apiResultPath="_embedded.salesOrders"
      :param="{customerId: formItem.customerId, status:'OPEN'}"
      :visible.sync="poSelectorVisible"
      :excluded="formItem.details | notVoided"
      @select-orders="onSelectOrders"
      >

    </OrderSelector>
  </StandardFormTemplate>
</template>

<script>
import formMixin from "baseComponents/forms/StandardFormMixin";
import StandardFormTemplate from "baseComponents/forms/StandardFormTemplate";
import SalesShipmentFormHeader from "./SalesShipmentFormHeader";
import SalesShipmentFormDetailList from "./SalesShipmentFormDetailList";
import OrderSelector from "orderandtransaction/OrderSelector";

export default {
  components: {StandardFormTemplate, SalesShipmentFormHeader, SalesShipmentFormDetailList, OrderSelector},
  mixins: [formMixin],
  data: function(){
    return {
      poSelectorVisible: false
    }
  },
  methods: {
    onFormSubmit(){
      this.$emit('save', this.formItem);
    },
    onNewDetail(){
      this.poSelectorVisible = true;
    },
    onVoidDetail(detail){
      detail.voided = true;
    },
    onSelectOrders(selectedOrders){
      let newReceipts = selectedOrders.map(
        order => ({
          companyId: this.formItem.companyId,
          orderNumber: order.salesOrderNumber,
          orderType: order.salesOrderType,
          orderSequence: order.salesOrderSequence,
          itemCode: order.itemCode,
          itemDescription: order.itemDescription,
          quantity: order.openQuantity,
          unitOfMeasure: order.unitOfMeasure,
          serialOrLotNumbers: [],
          _links: {
            item: {
              href: order._links.item.href
            }
          }
        })
      );
      this.formItem.details = this.formItem.details.concat(newReceipts);
    }
  },
  filters: {
    notVoided(details){
      if (details == null)
        return [];
      return details.filter(d => !d.voided)
    }
  }
}
</script>
