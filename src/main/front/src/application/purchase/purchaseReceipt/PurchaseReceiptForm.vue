<template>
  <StandardForm
    :submittable="editable"
    :validate="validated"
    @submit="onFormSubmit"
    @cancel="$emit('cancel')">
    <PurchaseReceiptFormHeader :formItem="formItem" :editable="editable">
    </PurchaseReceiptFormHeader>

    <PurchaseReceiptFormDetailList
      :details.sync="formItem.details"
      :editable="editable && formItem.vendorId != null"
      @new-detail="onNewDetail"
      @void-detail="onVoidDetail">
    </PurchaseReceiptFormDetailList>

    <PurchaseOrderSelector
      :param="{vendorId: formItem.vendorId, status:'OPEN'}"
      :visible.sync="poSelectorVisible"
      :excluded="formItem.details | notVoided"
      @select-orders="onSelectOrders">
    </PurchaseOrderSelector>
  </StandardForm>
</template>

<script>
import StandardForm from "baseComponents/forms/StandardFormTemplate";
import formMixin from "baseComponents/forms/StandardFormMixin";
import PurchaseReceiptFormHeader from "./PurchaseReceiptFormHeader";
import PurchaseReceiptFormDetailList from "./PurchaseReceiptFormDetailList";
import PurchaseOrderSelector from "./PurchaseOrderSelector";

export default {
  components: {StandardForm, PurchaseReceiptFormHeader, PurchaseReceiptFormDetailList, PurchaseOrderSelector},
  mixins: [formMixin],
  data: function(){
    return {
      poSelectorVisible: false
    }
  },
  methods: {
    onSelectOrders(selectedOrders){
      let newReceipts = selectedOrders.map(
        order => ({
          companyId: this.formItem.companyId,
          orderNumber: order.purchaseOrderNumber,
          orderType: order.purchaseOrderType,
          orderSequence: order.purchaseOrderSequence,
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
      //this.formItem.details.push(...newReceipts);
    },
    onFormSubmit(){
      this.$emit('save', this.formItem);
      //alert("submit");
    },
    onNewDetail(){
      this.poSelectorVisible = true;
    },
    onVoidDetail(detail){
      detail.voided = true;
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
