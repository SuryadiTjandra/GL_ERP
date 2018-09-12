var PurchaseOrderForm = {
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
		template: `
		<b-form>
			<b-form-group label="No. Order" horizontal>
				<b-form-input placeholder="Nomor Order" type="number" 
					v-model="formItem.purchaseOrderNumber" 
					:readOnly="!editable"
					style="width:40%; display:inline-block">
				</b-form-input>
				<b-form-input placeholder="Tipe Order" type="text" 
					v-model="formItem.purchaseOrderType" 
					:readOnly="!editable"
					style="width:20%; display:inline-block">
				</b-form-input>
				<b-form-input placeholder="Unit Usaha" type="text"
					v-model="formItem.companyId"
					:readOnly="!editable"
					style="width:38%; display:inline-block">
				</b-form-input>
			</b-form-group>
			<b-form-group label="Unit Kerja" horizontal>
				<b-form-input type="text"
					v-model="formItem.businessUnitId"
					:readOnly="!editable">
				</b-form-input>
			</b-form-group>
			<b-form-group label="Tanggal Order" horizontal>
				<b-form-input type="date"
					v-model="formItem.orderDate"
					:readOnly="!editable">
				</b-form-input>
			</b-form-group>
			<b-form-group label="Est. Tanggal Terima" horizontal>
				<b-form-input type="date"
					v-model="formItem.promisedDeliveryDate"
					:readOnly="!editable">
				</b-form-input>
			</b-form-group>
			<b-button variant="secondary" @click="$emit('cancel')">Cancel</b-button>
			<b-button variant="primary" @click="$emit('save')">Save</b-button>
		</b-form>
		`,
		data: function(){
			return {
				formItem: {}
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
			},
			details: function(){
				return this.formItem.details == null ? [] : this.formItem.details
			}
		},
		watch: {
			item: function(item){
				//deepcopy to prevent change in forms to affect property item
				this.formItem = JSON.parse(JSON.stringify(item));
			}
		}
}

export default PurchaseOrderForm;