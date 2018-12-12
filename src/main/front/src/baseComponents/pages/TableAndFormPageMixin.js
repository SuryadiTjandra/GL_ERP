export default {
  props: {
    apiUrl: {
      type: String,
      required: true
    },
    defaultItem: {
      type:Object,
      default: {}
    }
  },
  data: function(){
    return {
      formItem: this.defaultItem,
      formMode: "add",
      formVisible: false
    }
  },
  methods: {
    onCreateItem: function(event){
      this.formItem = this.defaultItem;
      this.formMode = "add";
      this.formVisible = true;
    },
    onViewItem: async function(item){
      this.formItem = await this.fetchFormItem(item);
      this.formMode = "view";
      this.formVisible = true;
    },
    onEditItem: async function(item){
      this.formItem = await this.fetchFormItem(item);
      this.formMode = "edit";
      this.formVisible = true;
    },
    onFormCancel: function(){
      this.formVisible = false;
      this.formItem = this.defaultItem;
    },
    onFormSave: function(formItem){

      const method = this.formMode == "add" ? "POST" : "PATCH";
      const link = this.formMode == "add" ? this.apiUrl : formItem._links.self.href;

      const csrfHeader = document.getElementsByName("_csrf_header")[0].getAttribute("content");
      const csrfToken = document.getElementsByName("_csrf")[0].getAttribute("content");
      let headers = new Headers();
      headers.append(csrfHeader, csrfToken);
      headers.append('Content-Type', 'application/json');

      fetch(link, {
        method: method,
        body: JSON.stringify(formItem),
        headers: headers
      })
      .then(res => res.ok ?
            res.json() :
            Promise.reject("Error: " + res.status + " " + res.statusText))
      .then(res => {
        alert("Sales Order created");
        this.formVisible = false;
        this.formItem = {};
      })
      .catch(err => alert(err));
    },

    fetchFormItem: async function(item){
      let res = await fetch(item._links.self.href);
      return await res.json();
    }
  },
  computed: {
    standardTableProps(){
      return {
        apiUrl: this.apiUrl
      }
    },
    standardTableListeners(){
      return {
        'create-clicked': this.onCreateItem,
        'edit-clicked': this.onEditItem,
        'view-clicked': this.onViewItem
      }
    },
    standardFormProps(){
      return {
        mode: this.formMode,
        item: this.formItem
      }
    },
    standardFormListeners(){
      return {
        'cancel': this.onFormCancel,
        'save': this.onFormSave
      }
    }
  }
}
