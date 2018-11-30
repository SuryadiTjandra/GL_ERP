export default {
  props: {
    item: {
      type: Object,
      required: true,
      validator: (value) => value != null
    },
    mode: {
      validator: (value) => ["add", "edit", "view"].indexOf(value) !== -1
    }
  },
  data: function(){
    return {
      formItem: {},
      validated: false,
      activeTab: 0
    }
  },
  computed: {
    editable: function(){
      return this.mode !== "view";
    }
  },
  watch: {
    item: function(item){
      //deepcopy to prevent change in forms to affect property item
      this.formItem = JSON.parse(JSON.stringify(item));
      this.validated = false;
      if (this.formItem.documentNumber && this.formItem.documentNumber == 0){
        this.formItem.documentNumber = null
      }
      delete(this.formItem.inputUserId);
      delete(this.formItem.lastUpdateUserId);
      delete(this.formItem.inputDateTime);
      delete(this.formItem.lastUpdateDateTime);
    }
  },
}
