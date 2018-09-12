import DataCodeInput from "../../baseComponents/inputs/DataCodeInput.js";

var PurchaseOrderForm = {
		components: {
			DataCodeInput
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
		<b-form>
			<b-form-row>
				<b-col cols="6">
					<b-form-group label="No. Order" horizontal>
						<b-form-input placeholder="Nomor Order" type="number" required class="col-5" style="display:inline"
							v-model="formItem.purchaseOrderNumber" 
							:readOnly="!editable"
							>
						</b-form-input>
						<!--
						<b-form-input placeholder="Tipe Order" type="text" required class="col-2" style="display:inline"
							v-model="formItem.purchaseOrderType" 
							:readOnly="!editable"
							>
						</b-form-input>
						-->
						<DataCodeInput required productCode="00" systemCode="DT" class="col-2" style="display:inline"
							v-model="formItem.purchaseOrderType"
							:readOnly="!editable">
						</DataCodeInput>
						<b-form-input placeholder="Unit Usaha" type="text" required class="col-4" style="display:inline"
							v-model="formItem.companyId"
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Unit Kerja" horizontal>
						<b-form-input type="text" required
							v-model="formItem.businessUnitId"
							:readOnly="!editable">
						</b-form-input>
					</b-form-group>
					<b-form-group label="Tanggal Order" horizontal>
						<b-form-input type="date" required
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
				</b-col>
				<b-col cols="3">
					<b-form-group label="Supplier" >
						<b-form-input type="text"
							v-model="formItem.vendorId"
							:readOnly="!editable">
						</b-form-input>
						<p class="mb-0">{{vendor == null ? "" : vendor.currentAddress.address1}}</p>
						<p class="mb-0">{{vendor == null ? "" : vendor.currentAddress.address2}}</p>
						<p class="mb-0">{{vendor == null ? "" : vendor.currentAddress.address3}}</p>
						<p class="mb-0">{{vendor == null ? "" : vendor.currentAddress.address4}}</p>
					</b-form-group>
				</b-col>
				<b-col cols="3">
					<b-form-group label="Alamat Kirim">
						<b-form-input type="text"
							id="xxx"
							v-model="formItem.receiverId"
							:readOnly="!editable">
						</b-form-input>
						<p class="mb-0">{{receiver == null ? "" : receiver.currentAddress.address1}}</p>
						<p class="mb-0">{{receiver == null ? "" : receiver.currentAddress.address2}}</p>
						<p class="mb-0">{{receiver == null ? "" : receiver.currentAddress.address3}}</p>
						<p class="mb-0">{{receiver == null ? "" : receiver.currentAddress.address4}}</p>
					</b-form-group>
				</b-col>
			</b-form-row>
			<b-form-row>
				<b-button variant="secondary" @click="$emit('cancel')">Cancel</b-button>
				<b-button variant="primary" @click="$emit('save', formItem)">Save</b-button>
			</b-form-row>
		</b-form>
		`,
		data: function(){
			return {
				formItem: {},
				vendor: {currentAddress:{address1:"ABC", address2:"DEF", address3:"GHI", address4:"JKL"}},
				receiver: {currentAddress:{address1:"ABC", address2:"DEF", address3:"GHI", address4:"JKL"}}
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
		}
}

export default PurchaseOrderForm;