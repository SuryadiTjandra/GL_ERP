import ResourceInput from "baseComponents/inputs/ResourceInput.js";
import ResourceInputUrl from "baseComponents/inputs/ResourceInputUrl.js";
import DiscountInput from "baseComponents/inputs/DiscountInput.js";
import DataCodeInput from "baseComponents/inputs/DataCodeInput.js";
import VoidButton from "baseComponents/buttons/VoidButton.js";
import AJAXPerformer from "util/AJAXPerformer.js";

var itemList = {
		components: {
			ResourceInput, ResourceInputUrl, DataCodeInput, VoidButton, DiscountInput
		},
		props: ['details', 'editable'],
		model: {
			prop: 'details',
			event: 'update',
		},
		template:`
		<div>
			<b-table :fields="fields" :items="itemData" small responsive showEmpty>
				<template slot="action" slot-scope="detail">
					<VoidButton v-if="editable && !detail.item.voided"
						@void-click="onVoidDetail(detail.item, detail.index)">
					</VoidButton>
				</template>
				
				<template slot="itemInfo" slot-scope="detail">
					<ResourceInputUrl v-model="detail.item._links.item.href" size="sm" 
						:readOnly="!editable || detail.item.voided"
						:resourceMetadata="{
							apiUrl:'/api/items',
							dataPath:'items',
							idPath:'itemCode',
							descPath:'description',
							displayPath: 'itemCode'
						}"
						@update:item="onItemChange(detail.item, ...arguments)">
					</ResourceInputUrl>
					<span>{{detail.item.description}}</span>
				</template>
				
				<template slot="quantityInfo" slot-scope="detail">
					<template v-if="detail.item.itemCode != null">
						<b-input type="number" size="sm" 
							:readOnly="!editable || detail.item.voided"
							v-model="detail.item.quantity" 
							:formatter="num => formatNumber(num, 5)" lazy-formatter
							style="display:inline; width:50%; text-align:right;"
							@change="onQuantityChange(detail.item, detail.index, ...arguments)">
						</b-input>
						<DataCodeInput productCode="00" systemCode="UM" size="sm" 
							:readOnly="!editable || detail.item.voided"
							v-model="detail.item.unitOfMeasure"
							style="display:inline; width:45%">
						</DataCodeInput>
					</template>
				</template>
				
				<template slot="unitCostInfo" slot-scope="detail">
					<template v-if="detail.item.itemCode != null">
						<b-input-group size="sm" >
							<b-input-group-prepend is-text v-if="editable && !detail.item.voided">
								 <input type="radio" 
								 	:checked="priceModeByUnit[detail.index]" 
								 	@click="$set(priceModeByUnit, detail.index, true)">
								 </input>
							</b-input-group-prepend>
							
							<b-input type="number" 
								:readonly="!priceModeByUnit[detail.index] || !editable || detail.item.voided"
								v-model="detail.item.unitCost" 
								:formatter="num => formatNumber(num, 5)" lazy-formatter
								style="text-align:right"
								@change="onUnitCostChange(detail.item, ...arguments)">
							</b-input>
						</b-input-group>
					</template>
				</template>
				
				<template slot="totalCostInfo" slot-scope="detail">
					<template v-if="detail.item.itemCode != null">
						<b-input-group size="sm">
							<b-input-group-prepend is-text v-if="editable && !detail.item.voided">
								 <input type="radio" 
								 	:checked="!priceModeByUnit[detail.index]"
								 	@click="$set(priceModeByUnit, detail.index, false)">
								 </input>
							</b-input-group-prepend>
							
							<b-input type="number" 
								:readonly="priceModeByUnit[detail.index] || !editable || detail.item.voided"
								v-model="detail.item.extendedCost" 
								:formatter="num => formatNumber(num, 5)" lazy-formatter
								style="text-align:right"
								@change="onExtendedCostChange(detail.item, ...arguments)">
							</b-input>
						</b-input-group>
						</template>
				</template>
				
				<template slot="unitDiscountInfo" slot-scope="detail">
					<template v-if="detail.item.itemCode != null">
						<DiscountInput size="sm" showDetail 
							:readOnly="!editable || detail.item.voided"
							v-model="detail.item.unitDiscountCode"
							@update:calculation="onUnitDiscountCalcFinish(detail.item, ...arguments)"
							:amount="detail.item.unitCost"
							:quantity="detail.item.quantity">
						</DiscountInput>
					</template>
				</template>
				
				<template slot="bottom-row" slot-scope="row">
					<td v-for="field in row.fields">
						<template v-if="field.key === 'action'">
							<b-button variant="outline-success" @click="onAddNewRow" size="sm"
								v-if="editable"
								style="padding:0; border-color:transparent;">
								<span class="oi oi-plus"></span>
							</b-button>
						</template>
						<template v-else-if="field.key === 'totalCostInfo'">
							<p class="text-right mr-2">{{totalExtendedCost > 0 ? totalExtendedCost : ''}}</p>
						</template>
					</td>
					
				</template>
			</b-table>
		</div>
		`,
		data: function(){
			return {
				fields: [
					{
						key:'action', label:'', thStyle:{width:'3%'}
					},
					{
						key:'itemInfo', label: 'Item', thStyle:{width:'32%'}
					},
					{
						key:'quantityInfo', label: 'Qty', thStyle:{width:'15%'}
					},
					{
						key:'unitCostInfo', label:'Unit Cost', thStyle:{width:'16.667%'}
					},
					{
						key:'totalCostInfo', label: 'Total Cost', thStyle:{width:'16.667%'}
					},
					{
						key:'unitDiscountInfo', label: 'Discount', thStyle:{width:'16.667%'}
					}
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
		
			},
			itemData: function(){
				if (this.formDetails == null) return [];
				return this.formDetails.map(det => {
					let variant = det.voided ? 'danger': '';
					return Object.assign(det, { _rowVariant: variant});
				})
			},
			totalExtendedCost: function(){
				let res = this.formDetails ?
							this.formDetails
								.filter(det => !det.voided)
								.map(det => Number(det.extendedCost))
								.reduce( ((a,b) => a + b), 0):
							0;
				return Number.isNaN(Number(res)) ? 0 : res;
			}
		},
		methods: {
			formatNumber: function(number, maxDecimalPlace=5){
				if (number == null)
					return 0;
				
				if (!number.toString().includes("."))
					return number;
				
				let decimal = number.toString().split(".")[1];
				if (decimal.length > maxDecimalPlace)
					return Number(number).toFixed(maxDecimalPlace);
				else
					return number;
			},
			
			onItemChange: async function(detail, item){
				if (item == null)
					item = { unitsOfMeasure: {}};
				
				detail.itemCode = item.itemCode;
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
			},
			onUnitDiscountCalcFinish: function(detail, discCalc){
				detail.unitDiscountRate = discCalc != null ? discCalc.discountRate : 0;
			},			
			onAddNewRow: function(){
				this.formDetails.push(this.defaultDetail());
			},
			onVoidDetail: function(detail, index){
				if (detail.purchaseOrderNumber == null || detail.purchaseOrderNumber == 0){
					this.formDetails.splice(index, 1);
				} else {
					if(confirm("Batalkan item order ini?")){
						this.$emit('void', detail);
					}
				}
			},
			
			defaultDetail: function(){
				return {
					itemCode: null,
					quantity: 1,
					unitCost: 0,
					extendedCost: 0,
					unitDiscountCode: null,
					unitDiscountRate: 0,
					_links: {
						item: {
							href: null
						}
					}
				}
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