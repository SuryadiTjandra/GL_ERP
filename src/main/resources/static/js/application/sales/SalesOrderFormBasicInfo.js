import DataCodeInput from "../../baseComponents/inputs/DataCodeInput.js";
import ResourceInput from "../../baseComponents/inputs/ResourceInput.js";
import AJAXPerformer from "/js/util/AJAXPerformer.js";

var form = {
		components: {
			DataCodeInput, ResourceInput
		},
		model: {
			prop:'item',
			event:'change'
		},
		props: {
			item: {
				type: Object,
				required: true,
				validator: (value) => value != null
			},
			editable: Boolean
		},
		template: `
		<div>
			<b-form-row>
				<b-col cols="6">
					<b-form-group label="No. Order" horizontal label-size="sm" label-text-align="right">
						<b-form-input placeholder="Nomor Order" type="number" size="sm" style="width:65%;display:inline"
							v-model="formItem.salesOrderNumber" 
							:readOnly="!editable"
							>
						</b-form-input>
						<DataCodeInput required productCode="00" systemCode="DT" size="sm"  style="width:30%;display:inline"
							v-model="formItem.salesOrderType"
							:readOnly="!editable">
						</DataCodeInput>
					</b-form-group>
					<b-form-group label="Unit Usaha" horizontal label-size="sm" label-text-align="right">
						<ResourceInput  style="display:inline" size="sm" required
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
						<ResourceInput size="sm" required
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
					<b-form-row><b-col>
						<b-form-group label="Tanggal Order" horizontal label-size="sm" label-text-align="right" label-cols="6">
							<b-form-input type="date" required size="sm"
								v-model="formItem.orderDate"
								:readOnly="!editable">
							</b-form-input>
						</b-form-group>
					</b-col> <b-col>
						<b-form-group label="Permintaan Tgl. Kirim" horizontal label-size="sm" label-text-align="right" label-cols="6">
							<b-form-input type="date" size="sm"
								v-model="formItem.requestDate"
								:readOnly="!editable">
							</b-form-input>
						</b-form-group>
					</b-col></b-form-row>
					<b-form-group label="Profit Center" horizontal label-size="sm" label-text-align="right">
						<ResourceInput size="sm" required
							v-model="formItem.profitCenterId"
							:readOnly="!editable"
							:resourceMetadata="{
								apiUrl:'/api/businessUnits',
								dataPath:'businessUnits', 
								idPath:'businessUnitId', 
								descPath:'description'}"
							>
						</ResourceInput>
					</b-form-group>
				</b-col>
				<b-col>
					<b-form-group label="Supplier" label-size="sm" horizontal label-text-align="right">
						<ResourceInput size="sm" required
							v-model="formItem.customerId"
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
					<b-form-row><b-col>
						<b-form-group label="Mata Uang" label-size="sm" horizontal label-text-align="right" label-cols="6">
							<DataCodeInput productCode="00" systemCode="CC" size="sm" required
								v-model="formItem.transactionCurrency"
								:readOnly="!editable">
							</DataCodeInput>
						</b-form-group>
					</b-col><b-col>
						<b-form-group label="Kurs" label-size="sm" horizontal label-text-align="right" label-cols="6">
							<b-input type="number" size="sm" placeholder="Kurs" required
								:readOnly="!editable"
								:formatter="num => Number(num).toFixed(2)"
								v-model="formItem.exchangeRate">
							</b-input>
						</b-form-group>
					</b-col></b-form-row>
					<b-form-group label="Tipe Pembayaran" label-size="sm" horizontal label-text-align="right">
						<ResourceInput size="sm" required
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
					<b-form-group label="Tipe PPN" label-size="sm" horizontal label-text-align="right">
						<ResourceInput size="sm"
							:selectedId="formItem.taxCode"
							@input="onTaxInput"
							:readOnly="!editable"
							:resourceMetadata="{
								apiUrl:'/api/taxRules',
								dataPath:'taxRules', 
								idPath:'taxCode', 
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
					<b-form-group label="Nomor PO Customer" label-size="sm" horizontal label-text-align="right">
						<b-input type="text" size="sm" placeholder="Nomor PO Customer"
							:readOnly="!editable"
							v-model="formItem.customerOrderNumber">
						</b-input>
					</b-form-group>
					<b-form-group label="Tanggal PO Customer" label-size="sm" horizontal label-text-align="right">
						<b-form-input type="date" size="sm"
							v-model="formItem.customerOrderDate"
							:readOnly="!editable">
						</b-form-input>
					</b-form-group>
					<b-form-group label="Salesman" label-size="sm" horizontal label-text-align="right">
						<ResourceInput size="sm"
							v-model="formItem.salesmanId"
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
		</div>
		`,
		data: function(){
			return {
				vendor: null,
				receiver: null
			}
		},
		computed: {
			formItem:{
				get(){ 
					return this.item;
				},
				set(value){
					this.$emit("change", value);
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
				
				let arSetting = await AJAXPerformer.getAsJson(vendor._links.arSetting.href);				
				if (this.formItem.transactionCurrency == null){
					this.formItem.transactionCurrency = arSetting.currencyCodeTransaction;
				}
					
				if (this.formItem.paymentTermCode == null){
					this.formItem.paymentTermCode = arSetting.paymentTermCode;
				}
				
				if (this.formItem.taxCode == null){
					this.formItem.taxCode = arSetting.taxCode;
				}
				
			},
			onReceiverChange: function(receiverId, receiver){
				this.formItem.receiverId = receiverId;
				this.receiver = receiver;
			},
			onTaxInput: function(taxCode, taxRule){
				this.formItem.taxCode = taxCode;
				this.formItem.taxRate = taxRule == null ? 0 : taxRule.taxPercentage1 + 
					taxRule.taxPercentage2 + taxRule.taxPercentage3 + 
					taxRule.taxPercentage4 + taxRule.taxPercentage5;
				this.formItem.taxAllowance = taxRule == null ? false : taxRule.taxAllowance;
			}
		}
};

export default form;