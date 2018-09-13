import DataCodeInput from "../../baseComponents/inputs/DataCodeInput.js";
import ResourceInput from "../../baseComponents/inputs/ResourceInput.js";
import AJAXPerformer from "/js/util/AJAXPerformer.js";

var PurchaseOrderForm = {
		components: {
			DataCodeInput, ResourceInput
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
		<b-tabs>
			<b-tab title="Info">
				<b-form>
					<b-form-row>
						<b-col cols="6">
							<b-form-group label="No. Order" horizontal label-size="sm" label-text-align="right">
								<b-form-input placeholder="Nomor Order" type="number" required size="sm" style="width:65%;display:inline"
									v-model="formItem.purchaseOrderNumber" 
									:readOnly="!editable"
									>
								</b-form-input>
								<DataCodeInput required productCode="00" systemCode="DT" size="sm"  style="width:30%;display:inline"
									v-model="formItem.purchaseOrderType"
									:readOnly="!editable">
								</DataCodeInput>
							</b-form-group>
							<b-form-group label="Unit Usaha" horizontal label-size="sm" label-text-align="right">
								<ResourceInput  style="display:inline" size="sm"
									v-model="formItem.companyId"
									:readOnly="!editable"
									:resourceMetadata="{
										apiUrl:'/api/companies',
										dataPath:'companies', 
										idPath:'companyId', 
										descPath:'description'}"
									>
								</ResourceInput>
							</b-form-group>
							<b-form-group label="Unit Kerja" horizontal label-size="sm" label-text-align="right">
								<ResourceInput size="sm"
									v-model="formItem.businessUnitId"
									:readOnly="!editable"
									:resourceMetadata="{
										apiUrl:'/api/businessUnits',
										dataPath:'businessUnits', 
										idPath:'businessUnitId', 
										descPath:'description'}"
									>
								</ResourceInput>
							</b-form-group>
							<b-form-group label="Tanggal Order" horizontal label-size="sm" label-text-align="right">
								<b-form-input type="date" required size="sm"
									v-model="formItem.orderDate"
									:readOnly="!editable">
								</b-form-input>
							</b-form-group>
							<b-form-group label="Est. Tanggal Terima" horizontal label-size="sm" label-text-align="right">
								<b-form-input type="date" size="sm"
									v-model="formItem.promisedDeliveryDate"
									:readOnly="!editable">
								</b-form-input>
							</b-form-group>
						</b-col>
						<b-col>
							<b-form-group label="Supplier" label-size="sm" horizontal label-text-align="right">
								<ResourceInput size="sm"
									v-model="formItem.vendorId"
									:readOnly="!editable"
									:resourceMetadata="{
										apiUrl:'/api/addresses',
										dataPath:'addresses', 
										idPath:'addressNumber', 
										descPath:'name'}"
									@change="onVendorChange"
									>
								</ResourceInput>
							</b-form-group>
							<b-form-group label="Alamat Kirim" label-size="sm" horizontal label-text-align="right">
								<ResourceInput size="sm"
									v-model="formItem.receiverId"
									:readOnly="!editable"
									:resourceMetadata="{
										apiUrl:'/api/addresses',
										dataPath:'addresses', 
										idPath:'addressNumber', 
										descPath:'name'}"
									@change="onReceiverChange"
									>
								</ResourceInput>
								<p class="mb-0">{{receiver == null ? "" : receiver.currentAddress.address1}}</p>
								<p class="mb-0">{{receiver == null ? "" : receiver.currentAddress.address2}}</p>
								<p class="mb-0">{{receiver == null ? "" : receiver.currentAddress.address3}}</p>
								<p class="mb-0">{{receiver == null ? "" : receiver.currentAddress.address4}}</p>
							</b-form-group>
						</b-col>
					</b-form-row>
					<b-form-row>
						<b-col>
							<b-form-group label="Mata Uang" label-size="sm" horizontal label-text-align="right">
								<DataCodeInput productCode="00" systemCode="CC" size="sm"
									v-model="formItem.transactionCurrency"
									:readOnly="!editable">
								</DataCodeInput>
							</b-form-group>
							<b-form-group label="Tipe Pembayaran" label-size="sm" horizontal label-text-align="right">
								<ResourceInput size="sm"
									v-model="formItem.paymentTermCode"
									:readOnly="!editable"
									:resourceMetadata="{
										apiUrl:'/api/paymentTerms',
										dataPath:'paymentTerms', 
										idPath:'paymentTermCode', 
										descPath:'description'
									}">
								</ResourceInput>
							</b-form-group>
							<b-form-group label="Keterangan" label-size="sm" horizontal label-text-align="right">
								<b-input type="text" size="sm" placeholder="Keterangan"
									:readOnly="!editable"
									v-model="formItem.description">
								</b-input>
							</b-form-group>
						</b-col>
						<b-col>
							<b-form-group label="Kurs" label-size="sm" horizontal label-text-align="right">
								<b-input type="number" size="sm" placeholder="Kurs"
									:readOnly="!editable"
									:formatter="num => Number(num).toFixed(2)"
									v-model="formItem.exchangeRate">
								</b-input>
							</b-form-group>
							<b-form-group label="Tipe PPN" label-size="sm" horizontal label-text-align="right">
								<ResourceInput size="sm"
									v-model="formItem.taxCode"
									:readOnly="!editable"
									:resourceMetadata="{
										apiUrl:'/api/taxRules',
										dataPath:'taxRules', 
										idPath:'taxCode', 
										descPath:'description'
									}">
								</ResourceInput>
							</b-form-group>
							<b-form-group label="Customer" label-size="sm" horizontal label-text-align="right">
								<ResourceInput size="sm"
									v-model="formItem.customerId"
									:readOnly="!editable"
									:resourceMetadata="{
										apiUrl:'/api/addresses',
										dataPath:'addresses', 
										idPath:'addressNumber', 
										descPath:'name'}"
									>
								</ResourceInput>
							</b-form-group>
						</b-col>
					</b-form-row>
					<b-form-row>
						<b-button variant="secondary" @click="$emit('cancel')">Kembali</b-button>
						<b-button variant="primary" @click="$emit('save', formItem)" v-if="editable">Simpan</b-button>
					</b-form-row>
				</b-form>
			</b-tab>
			<b-tab title="Items">
				Items
			</b-tab>
			<b-tab title="Shipping">
				Shipping
			</b-tab>
		</b-tabs>
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