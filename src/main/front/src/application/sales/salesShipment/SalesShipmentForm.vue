<template>
  <StandardFormTemplate
    :submittable="editable"
    :validate="validated"
    @submit="onFormSubmit"
    @cancel="$emit('cancel')">

    <SalesShipmentFormHeader :formItem="formItem" :editable="editable">
    </SalesShipmentFormHeader>
  </StandardFormTemplate>
</template>

<script>
import formMixin from "baseComponents/forms/StandardFormMixin";
import StandardFormTemplate from "baseComponents/forms/StandardFormTemplate";
import SalesShipmentFormHeader from "./SalesShipmentFormHeader";

export default {
  components: {StandardFormTemplate, SalesShipmentFormHeader},
  mixins: [formMixin],
  methods: {
    onFormSubmit(){
      alert("submit");
    }
  }
}
</script>
