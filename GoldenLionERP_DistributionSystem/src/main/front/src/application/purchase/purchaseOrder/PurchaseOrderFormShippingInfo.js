import DataCodeInput from "baseComponents/inputs/DataCodeInput.js";
import ResourceInput from "baseComponents/inputs/ResourceInput.js";

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
			<b-form-row>
				<b-col cols="6">
					<b-form-group label="Nomor P.I.B." horizontal label-size="sm" label-text-align="right">
						<b-form-input placeholder="Nomor P.I.B." type="text" size="sm"
							v-model="formItem.importDeclarationNumber" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Tanggal P.I.B." horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="date" size="sm"
							v-model="formItem.importDeclarationDate" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Port of Departure" horizontal label-size="sm" label-text-align="right">
						<ResourceInput size="sm"
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
					<b-form-group label="Port of Arrival" horizontal label-size="sm" label-text-align="right">
						<ResourceInput size="sm"
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
					<b-form-group label="Vehicle Registration Number" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="text" size="sm"
							v-model="formItem.vehicleRegistrationNumber" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Vehicle Type" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="text" size="sm"
							v-model="formItem.vehicleType" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Keterangan 1" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="text" size="sm"
							v-model="formItem.vehicleDescription" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
					<b-form-group label="Keterangan 2" horizontal label-size="sm" label-text-align="right">
						<b-form-input  type="text" size="sm"
							v-model="formItem.vehicleDescription2" 
							:readOnly="!editable"
							>
						</b-form-input>
					</b-form-group>
				</b-col>
				<b-col cols="6" class="border-left">
					<b-form-group label="Order Note" >
						<b-form-textarea rows="12"
							
							:readOnly="!editable"
							>
						</b-form-textarea>
					</b-form-group>
				</b-col>
			</b-form-row>
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