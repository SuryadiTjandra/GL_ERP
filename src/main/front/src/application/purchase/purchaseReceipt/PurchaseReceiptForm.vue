<template>
  <StandardForm
    :submittable="editable"
    :validate="validated"
    @submit="onFormSubmit"
    @cancel="$emit('cancel')">
    <PurchaseReceiptFormHeader :formItem="formItem2" :editable="editable">
    </PurchaseReceiptFormHeader>

    <PurchaseReceiptFormDetailList :details="formItem2.details">
    </PurchaseReceiptFormDetailList>
  </StandardForm>
</template>

<script>
import StandardForm from "baseComponents/forms/StandardFormTemplate";
import formMixin from "baseComponents/forms/StandardFormMixin"
import PurchaseReceiptFormHeader from "./PurchaseReceiptFormHeader"
import PurchaseReceiptFormDetailList from "./PurchaseReceiptFormDetailList"

export default {
  components: {StandardForm, PurchaseReceiptFormHeader, PurchaseReceiptFormDetailList},
  mixins: [formMixin],
  methods: {
    onFormSubmit(){
      alert("submit");
    }
  },
  data: function(){
    return {
      formItem2: {}
    }
  },
  watch: {
    formItem: function(item){
      if (item == null)
        this.formItem2 == null;

      fetch(item._links.self.href)
        .then(res => res.json())
        .then(res => this.formItem2 = res);
    }
  }
}
</script>
