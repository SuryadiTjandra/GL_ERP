import ResourceInput from "/js/baseComponents/inputs/ResourceInput.js";
import DataCodeInput from "/js/baseComponents/inputs/DataCodeInput.js";
import AJAXPerformer from "/js/util/AJAXPerformer.js";

var itemList = {
		components: {
			ResourceInput, DataCodeInput
		},
		props: ['details'],
		model: {
			prop: 'details',
			event: 'update',
		},
		template:`
			<b-table :fields="fields" :items="formDetails" small fixed>
				<template slot="itemInfo" slot-scope="detail">
					<ResourceInput :selectedId="detail.item.itemCode" size="sm" 
						:resourceMetadata="{
							apiUrl:'/api/items',
							dataPath:'items',
							idPath:'itemCode',
							descPath:'itemCode'
						}"
						@input="onItemChange(detail.item, ...arguments)">
					</ResourceInput>
					<span>{{detail.item.description}}</span>
				</template>
				
				<template slot="quantityInfo" slot-scope="detail">
					<b-input type="number" size="sm"
						v-model="detail.item.quantity" 
						:formatter="num => formatNumber(num, 5)" lazy-formatter
						style="display:inline; width:70%; text-align:right"
						@change="onQuantityChange(detail.item, detail.index, ...arguments)">
					</b-input>
					<DataCodeInput productCode="00" systemCode="UM" size="sm" 
						v-model="detail.item.unitOfMeasure"
						style="display:inline; width:25%">
					</DataCodeInput>
				</template>
				
				<template slot="unitCostInfo" slot-scope="detail">
					<b-input-group size="sm">
						<b-input-group-prepend is-text>
							 <input type="radio" 
							 	:checked="priceModeByUnit[detail.index]" 
							 	@click="$set(priceModeByUnit, detail.index, true)">
							 </input>
						</b-input-group-prepend>
						
						<b-input type="number" :readonly="!priceModeByUnit[detail.index]"
							v-model="detail.item.unitCost" 
							:formatter="num => formatNumber(num, 5)" lazy-formatter
							style="text-align:right"
							@change="onUnitCostChange(detail.item, ...arguments)">
						</b-input>
					</b-input-group>
				</template>
				
				<template slot="totalCostInfo" slot-scope="detail">
					<b-input-group size="sm">
						<b-input-group-prepend is-text>
							 <input type="radio" 
							 	:checked="!priceModeByUnit[detail.index]"
							 	@click="$set(priceModeByUnit, detail.index, false)">
							 </input>
						</b-input-group-prepend>
						
						<b-input type="number" :readonly="priceModeByUnit[detail.index]"
							v-model="detail.item.extendedCost" 
							:formatter="num => formatNumber(num, 5)" lazy-formatter
							style="text-align:right"
							@change="onExtendedCostChange(detail.item, ...arguments)">
						</b-input>
					</b-input-group>
				</template>
			</b-table>
		`,
		data: function(){
			return {
				fields: [
					'itemInfo',
					'quantityInfo',
					'unitCostInfo',
					'totalCostInfo',
					'unitDiscountCode',
				],
				priceModeByUnit: []
				
			}
		},
		computed: {
			formDetails: {
				get: function(){
					return this.details;
				},
				set: function(newDetails){
					this.$emit('update', newDetails)
				}
		
			}
		},
		methods: {
			formatNumber: function(number, maxDecimalPlace){
				if (!number.toString().includes("."))
					return number;
				
				let decimal = number.toString().split(".")[1];
				if (decimal.length > maxDecimalPlace)
					return Number(number).toFixed(maxDecimalPlace);
				else
					return number;
			},
			
			onItemChange: async function(sequence, itemCode, item){
				
				detail.itemCode = itemCode;
				detail.description = item.description;
				detail.primaryUnitOfMeasure = item.unitsOfMeasure.primaryUnitOfMeasure;
				if (detail.unitOfMeasure == null || detail.unitOfMeasure === detail.primaryUnitOfMeasure)
					detail.unitOfMeasure = detail.primaryUnitOfMeasure;
				detail.secondaryUnitOfMeasure = item.unitsOfMeasure.secondaryUnitOfMeasure;
				detail.glClass = item.glClass;
				detail.lineType = item.transactionType;
				
				let locations = await AJAXPerformer.getAsJson(item._links.itemLocations.href);
				let primaryLocation = locations._embedded.itemLocations.find(loc => loc.primary);
				if (primaryLocation != null){
					detail.locationId = primaryLocation.locationId;
					detail.serialLotNo = primaryLocation.serialLotNo;
				}
			},
			onQuantityChange: function(detail, index, quantity){
				if (this.priceModeByUnit[index]){
					let extendedCost = Number(detail.unitCost) * Number(quantity);
					detail.extendedCost = this.formatNumber(extendedCost);
				}else{
					let unitCost = Number(detail.extendedCost) / Number(quantity);
					detail.unitCost = this.formatNumber(unitCost);
				}
			},
			onUnitCostChange: function(detail, unitCost){
				let extendedCost = Number(unitCost) * Number(detail.quantity);
				detail.extendedCost = this.formatNumber(extendedCost);
			},
			onExtendedCostChange: function(detail, extendedCost){
				let unitCost = Number(extendedCost) / Number(detail.quantity);
				detail.unitCost = this.formatNumber(unitCost);
			}
		},
		watch: {
			details: function(details){
				//details.forEach(det => det.priceModeByUnit = true);
				this.priceModeByUnit = details == null ? [] : details.map(det => true)
			}
		}
};

export default itemList;