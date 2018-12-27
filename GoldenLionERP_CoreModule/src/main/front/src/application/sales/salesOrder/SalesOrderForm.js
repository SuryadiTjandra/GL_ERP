import BasicForm from "./SalesOrderFormBasicInfo.js";
import ItemForm from "./SalesOrderFormItemList.js";
import ShippingForm from "./SalesOrderFormShippingInfo.js";
import Summary from "./SalesOrderSummary.js";
import AJAXPerformer from "util/AJAXPerformer.js";

var SalesOrderForm = {
		components: {
			BasicForm, ItemForm, ShippingForm, Summary
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
				<BasicForm :editable="mode === 'add'" v-model="formItem">
				</BasicForm>
			</b-tab>
			<b-tab title="Items">
				</br>
				<ItemForm v-model="formItem.details" :editable="editable" @void="onVoidDetail">
				</ItemForm>
			</b-tab>
			<b-tab title="Shipping">
				</br>
				<ShippingForm v-model="formItem" :editable="editable">
				</ShippingForm>
			</b-tab>
			<b-tab title="Summary">
				</br>
				<Summary v-model="formItem" :editable="editable">
				</Summary>
			</b-tab><!---->
		</b-tabs>
		<b-form-row class="mt-2 mb-4">
			<b-button variant="secondary" @click="$emit('cancel')" class="mx-2">Kembali</b-button>
			<b-button type="submit" variant="primary"  v-if="editable" class="mx-2">Simpan</b-button>
		</b-form-row>
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
			},
			onVoidDetail: function(detail){
				detail.voided = true;
				
				let seq = detail.purchaseOrderSequence;
				let oldDetIdx = this.formItem.details.findIndex(det => det.purchaseOrderSequence == seq);
				this.formItem.details.splice(oldDetIdx, 1, detail);
			}
		}
}

export default SalesOrderForm;