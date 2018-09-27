import ResourceInput from "/js/baseComponents/inputs/ResourceInput.js";
import DiscountInput from "/js/baseComponents/inputs/DiscountInput.js";
import DataCodeInput from "/js/baseComponents/inputs/DataCodeInput.js";
import VoidButton from "/js/baseComponents/buttons/VoidButton.js";
import AJAXPerformer from "/js/util/AJAXPerformer.js";

var itemList = {
		components: {
			ResourceInput, DataCodeInput, VoidButton, DiscountInput
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
					<ResourceInput :selectedId="detail.item.itemCode" size="sm" 
						:readOnly="!editable || detail.item.voided"
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
				
				<template slot="unitPriceInfo" slot-scope="detail">
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
								v-model="detail.item.unitPrice" 
								:formatter="num => formatNumber(num, 5)" lazy-formatter
								style="text-align:right"
								@change="onUnitPriceChange(detail.item, ...arguments)">
							</b-input>
						</b-input-group>
					</template>
				</template>
				
				<template slot="totalPriceInfo" slot-scope="detail">
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
								v-model="detail.item.extendedPrice" 
								:formatter="num => formatNumber(num, 5)" lazy-formatter
								style="text-align:right"
								@change="onExtendedPriceChange(detail.item, ...arguments)">
							</b-input>
						</b-input-group>
						</template>
				</template>
				
				<template slot="unitDiscountInfo" slot-scope="detail">
					<template v-if="detail.item.itemCode != null">
						<DiscountInput size="sm" showDetail 
							:readOnly="!editable || detail.item.voided"
							:discountCode="detail.item.unitDiscountCode"
							@change="onUnitDiscountChange(detail.item, ...arguments)"
							:amount="detail.item.unitPrice"
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
						<template v-else-if="field.key === 'totalPriceInfo'">
							<p class="text-right mr-2">{{totalExtendedPrice > 0 ? totalExtendedPrice : ''}}</p>
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
						key:'unitPriceInfo', label:'Unit Price', thStyle:{width:'16.667%'}
					},
					{
						key:'totalPriceInfo', label: 'Total Price', thStyle:{width:'16.667%'}
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
			totalExtendedPrice: function(){
				let res = this.formDetails ?
							this.formDetails
								.filter(det => !det.voided)
								.map(det => Number(det.extendedPrice))
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
			
			onItemChange: async function(detail, itemCode, item){
				if (item == null)
					item = { unitsOfMeasure: {}};
				
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
					let extendedPrice = Number(detail.unitPrice) * Number(quantity);
					detail.extendedPrice = this.formatNumber(extendedPrice);
				}else{
					let unitPrice = Number(detail.extendedPrice) / Number(quantity);
					detail.unitPrice = this.formatNumber(unitPrice);
				}
			},
			onUnitPriceChange: function(detail, unitPrice){
				let extendedPrice = Number(unitPrice) * Number(detail.quantity);
				detail.extendedPrice = this.formatNumber(extendedPrice);
			},
			onExtendedPriceChange: function(detail, extendedPrice){
				let unitPrice = Number(extendedPrice) / Number(detail.quantity);
				detail.unitPrice = this.formatNumber(unitPrice);
			},
			onUnitDiscountChange: function(detail, discCode, disc, discCalc){
				detail.unitDiscountCode = discCode;
				detail.unitDiscountRate = discCalc != null ? discCalc.discountRate : 0;
			},
			
			onAddNewRow: function(){
				this.formDetails.push(this.defaultDetail());
			},
			onVoidDetail: function(detail, index){
				if (detail.salesOrderNumber == null || detail.salesOrderNumber == 0){
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
					unitPrice: 0,
					extendedPrice: 0,
					unitDiscountCode: null,
					unitDiscountRate: 0
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