<template>
  <StandardForm
    :submittable="editable"
    :validate="validated"
    @submit="onFormSubmit"
    @cancel="$emit('cancel')">
  <PurchaseReceiptFormHeader :formItem="formItem" :editable="editable">
  </PurchaseReceiptFormHeader>
  </StandardForm>
</template>

<script>
import StandardForm from "baseComponents/forms/StandardFormTemplate";
import formMixin from "baseComponents/forms/StandardFormMixin"
import PurchaseReceiptFormHeader from "./PurchaseReceiptFormHeader"

export default {
  components: {StandardForm, PurchaseReceiptFormHeader},
  mixins: [formMixin],
  methods: {
    onFormSubmit(){
      alert("submit");
    }
  }
}
</script>
