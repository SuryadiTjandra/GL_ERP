import DataCodeInput from "/js/baseComponents/inputs/DataCodeInput.js";
import ResourceInput from "/js/baseComponents/inputs/ResourceInput.js";

var ShippingForm = {
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
			<b-form-row class="border-bottom mb-2">
				<b-col>
					<b-form-group label="Kondisi Pengiriman" horizontal label-size="sm" label-text-align="right">
						<DataCodeInput productCode="42" systemCode="SC" size="sm"
							:readOnly="!editable"
							v-model="formItem.shipmentCondition">
						</DataCodeInput>
					</b-form-group>
					<b-form-group label="Metode Pengiriman" horizontal label-size="sm" label-text-align="right">
						<DataCodeInput productCode="42" systemCode="MT" size="sm"
							:readOnly="!editable"
							v-model="formItem.shippingMethod">
						</DataCodeInput>
					</b-form-group>
				</b-col>
				<b-col>
					<b-form-group label="PPJK / EMKL" horizontal label-size="sm" label-text-align="right">
						<ResourceInput  style="display:inline" size="sm" 
							v-model="formItem.expeditionId"
							:readOnly="!editable"
							:resourceMetadata="{
								apiUrl:'/api/addresses',
								dataPath:'addresses', 
								idPath:'addressNumber', 
								descPath:'name'}"
							>
						</ResourceInput>
					</b-form-group>
					<b-form-group label="Perwakilan" horizontal label-size="sm" label-text-align="right">
						<ResourceInput  style="display:inline" size="sm" 
							v-model="formItem.partnerRepresentativeId"
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
			<b-form-row class="border-bottom mb-2">
				<b-col>
					<b-form-group label="Tanggal Muat" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.dateOfLoading" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Port of Departure" horizontal label-size="sm" label-text-align="right">
						<ResourceInput  style="display:inline" size="sm" 
							v-model="formItem.portOfDepartureId"
							:readOnly="!editable"
							:resourceMetadata="{
								apiUrl:'/api/addresses',
								dataPath:'addresses', 
								idPath:'addressNumber', 
								descPath:'name'}"
							>
						</ResourceInput>
					</b-form-group>
					<b-form-group label="Est.Tanggal Berangkat" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.estimatedTimeOfDeparture" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Tanggal Berangkat" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.actualTimeOfDeparture" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Tanggal Pengiriman" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.deliveryDate" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
				</b-col>
				<b-col>
					<b-form-group label="Tanggal Sandar" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.dateOfDocking" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Port of Arrival" horizontal label-size="sm" label-text-align="right">
						<ResourceInput  style="display:inline" size="sm" 
							v-model="formItem.portOfArrivalId"
							:readOnly="!editable"
							:resourceMetadata="{
								apiUrl:'/api/addresses',
								dataPath:'addresses', 
								idPath:'addressNumber', 
								descPath:'name'}"
							>
						</ResourceInput>
					</b-form-group>
					<b-form-group label="Est.Tanggal Kedatangan" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.estimatedTimeOfArrival" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Tanggal Kedatangan" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.actualTimeOfArrival" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Tanggal Bongkar" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.dateOfUnloading" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
				</b-col>
			</b-form-row>
			<b-form-row>
				<b-col>
					<b-form-group label="Nomor P.E.B." horizontal label-size="sm" label-text-align="right">
						<b-form-input placeholder="" type="text" size="sm"
							v-model="formItem.exportDeclarationNumber" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Tanggal P.E.B." horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.exportDeclarationDate" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Export Approval Number" horizontal label-size="sm" label-text-align="right">
						<b-form-input placeholder="" type="text" size="sm"
							v-model="formItem.exportApprovalNumber" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Export Approval Date" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.exportApprovalDate" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Freight Billing Number" horizontal label-size="sm" label-text-align="right">
						<b-form-input placeholder="" type="text" size="sm"
							v-model="formItem.freightBillingNumber" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Freight Billing Date" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.freightBillingDate" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
				</b-col>
				<b-col class="border-left">
					<b-form-group label="Order Note" >
						<b-form-textarea rows="10"
							
							:readOnly="!editable"
							>
						</b-form-textarea>
					</b-form-group>
				</b-col>
			</b-form-row>
			<!--
			<b-form-row>
				<b-col>
					<b-form-group label="Supp/Cust/Job No." horizontal label-size="sm" label-text-align="right">
						<b-form-input placeholder="" type="text" size="sm"
							v-model="formItem.jobNo" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Shipment ID" horizontal label-size="sm" label-text-align="right">
						<b-form-input placeholder="Nomor P.I.B." type="text" size="sm"
							v-model="formItem.shipmentId" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Packing List ID" horizontal label-size="sm" label-text-align="right">
						<b-form-input placeholder="Nomor P.I.B." type="text" size="sm"
							v-model="formItem.packingListId" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Nomor Surat Jalan" horizontal label-size="sm" label-text-align="right">
						<b-form-input placeholder="" type="text" size="sm"
							v-model="formItem.deliveryOrderNumber" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="User Reserved Date #1" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.userReservedDate1" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="User Reserved Date #2" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.userReservedDate2" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
				</b-col>
			</b-form-row>
			-->
		</div>
		`,
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
		
}

export default ShippingForm;