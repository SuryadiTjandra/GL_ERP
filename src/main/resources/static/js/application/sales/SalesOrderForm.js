import BasicForm from "./SalesOrderFormBasicInfo.js";
import ItemForm from "./SalesOrderFormItemList.js";
import ShippingForm from "./SalesOrderFormShippingInfo.js";
//import Summary from "./PurchaseOrderSummary.js";
import AJAXPerformer from "/js/util/AJAXPerformer.js";

var SalesOrderForm = {
		components: {
			BasicForm, ItemForm, ShippingForm//, Summary
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
		<b-form @submit.prevent.stop="onSubmit" novalidate :validated="validated">
		<b-tabs v-model="activeTab">
			<b-tab title="Info">
				</br>
				<BasicForm :editable="editable" v-model="formItem">
				</BasicForm>
			</b-tab>
			<b-tab title="Items">
				</br>
				<ItemForm v-model="formItem.details">
				</ItemForm>
			</b-tab>
			<b-tab title="Shipping">
				</br>
				<ShippingForm v-model="formItem" :editable="editable">
				</ShippingForm>
			</b-tab>
			<!--<b-tab title="Summary">
				</br>
				<Summary v-model="formItem" :editable="editable">
				</Summary>
			</b-tab>-->
		</b-tabs>
		</br>
		<b-button variant="secondary" @click="$emit('cancel')">Kembali</b-button>
		<b-button type="submit" variant="primary"  v-if="editable">Simpan</b-button>
		</b-form>
		`,
		data: function(){
			return {
				formItem: {},
				validated: false,
				activeTab: 0
			}
		},
		computed: {
			title: function(){
				switch(this.mode) {
					case "add": return "Buat Sales Order Baru";
					case "edit": return "Ubah Sales Order";
					case "view": return "Lihat Informasi Sales Order";
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
				this.validated = false;
				if (this.formItem.salesOrderNumber == 0){
					this.formItem.salesOrderNumber = null
				}
				delete(this.formItem.inputUserId);
				delete(this.formItem.lastUpdateUserId);
				delete(this.formItem.inputDateTime);
				delete(this.formItem.lastUpdateDateTime);
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
			},
			onSubmit: function(event){
				this.validated = true;
				let form = event.target;
				
				
				if (form.checkValidity()){
					if (this.formItem.details == null || this.formItem.details.length == 0){
						alert("Daftar item tidak boleh kosong!");
						this.activeTab = 1;
					} else {
						this.$emit('save', this.formItem);
					}
				}else {
					this.activeTab = 0;
				}
			}
		}
}

export default SalesOrderForm;