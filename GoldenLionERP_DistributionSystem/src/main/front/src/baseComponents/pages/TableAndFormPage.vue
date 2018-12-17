<template>
  <b-container fluid>
    <transition name="fade">
      <div v-show="!formVisible">
        <slot name="table"></slot>
      </div>
    </transition>
    <transition name="fade">
      <b-container fluid v-show="formVisible">
        <slot name="form"></slot>
      </b-container>
    </transition>
  </b-container>
</template>

<script>
export default {
  props: {
    formVisible: {
      type: Boolean,
      required: true
    }
  }
}
</script>
