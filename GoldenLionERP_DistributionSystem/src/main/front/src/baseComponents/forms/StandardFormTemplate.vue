<template>
	<b-form @submit.prevent.stop="$emit('submit', $event)" novalidate :validated="validated">
    <slot></slot>
    <b-form-row class="mt-2 mb-4">
      <b-button variant="secondary" @click="$emit('cancel')" class="mx-2">Kembali</b-button>
      <b-button type="submit" variant="primary"  v-if="submittable" class="mx-2">Simpan</b-button>
    </b-form-row>
  </b-form>
</template>

<script>
export default {
  props: {
    submittable: Boolean,
    validated: {
      type:Boolean,
      default: false
    }
  }
}
</script>
