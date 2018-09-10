var PurchaseOrderModal = {
	model:{
		prop:'visible',
		event:'change'
	},
	props: {
		visible: Boolean,
		item: Object,
		mode: {
			validator: (value) => ["add", "edit", "view"].indexOf(value) !== -1
		}
	},
	template: `
		<b-modal no-close-on-backdrop no-close-on-esc hide-header-close
			size="lg"
			:title="title"		
			:ok-disabled="!editable"	
			:visible="visible"
			@change="$emit('change', $event)"
			@ok="$emit('ok', itemLink, buildFormItem())">
			
			<b-form-input
				type="text"
				v-model="purchaseOrderNumber"
				:readOnly="!editable">
			</b-form-input>
		</b-modal>
		`,
	data: function(){
		return {
			itemLink: "",
			purchaseOrderNumber: ""
		}
	},
	computed: {
		title: function(){
			switch(this.mode) {
				case "add": return "Buat Purchase Order Baru";
				case "edit": return "Ubah Purchase Order";
				case "view": return "Lihat Informasi Purchase Order";
				default: return null;
			}
		},
		editable: function(){
			return this.mode !== "view";
		}
	},
	watch:{
		item: function(item){
			//deep copy item, so original data won't be mutated
			this.purchaseOrderNumber = item ? item.purchaseOrderNumber : "",
			this.itemLink = item ? item._links.self.href : null
		}
	},
	methods:{
		buildFormItem: function(){
			return {
				purchaseOrderNumber: this.purchaseOrderNumber
			}
		}
	}
};

export default PurchaseOrderModal;