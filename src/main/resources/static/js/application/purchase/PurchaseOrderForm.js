import BasicForm from "./PurchaseOrderFormBasicInfo.js";
import AJAXPerformer from "/js/util/AJAXPerformer.js";

var PurchaseOrderForm = {
		components: {
			BasicForm
		},
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
		<div>
		<b-tabs>
			<b-tab title="Info">
				<BasicForm :editable="editable" v-model="formItem">
				</BasicForm>
			</b-tab>
			<b-tab title="Items">
				Items
			</b-tab>
			<b-tab title="Shipping">
				Shipping
			</b-tab>
		</b-tabs>

		<b-button variant="secondary" @click="$emit('cancel')">Kembali</b-button>
		<b-button variant="primary" @click="$emit('save', formItem)" v-if="editable">Simpan</b-button>
		</div>
		`,
		data: function(){
			return {
				formItem: {},
				vendor: null,
				receiver: null
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
				if (this.formItem.purchaseOrderNumber == 0){
					this.formItem.purchaseOrderNumber = null
				}
			}
		},
		methods: {
			onVendorChange: async function(vendorId, vendor){				
				this.formItem.vendorId = vendorId;
				this.vendor = vendor;
				if (vendor == null)
					return;
				
				if (this.receiver == null){
					this.formItem.receiverId = vendorId;
					this.receiver = vendor;
				}
				
				let apSetting = await AJAXPerformer.getAsJson(vendor._links.apSetting.href);				
				if (this.formItem.transactionCurrency == null){
					this.formItem.transactionCurrency = apSetting.currencyCodeTransaction;
				}
					
				if (this.formItem.paymentTermCode == null){
					this.formItem.paymentTermCode = apSetting.paymentTermCode;
				}
				
			},
			onReceiverChange: function(receiverId, receiver){
				this.receiverId = receiverId;
				this.receiver = receiver;
			}
		}
}

export default PurchaseOrderForm;