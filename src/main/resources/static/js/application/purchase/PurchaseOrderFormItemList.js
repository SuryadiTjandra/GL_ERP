import ResourceInput from "/js/baseComponents/inputs/ResourceInput.js";

var itemList = {
		components: {
			ResourceInput
		},
		props: ['items'],
		model: {
			prop: 'items',
			event: 'update',
		},
		template:`
			<b-table :fields="fields" :items="formItems" small>
				<template slot="itemInfo" slot-scope="data">
					<ResourceInput :selectedId="data.item.itemCode" size="sm" 
						:resourceMetadata="{
							apiUrl:'/api/items',
							dataPath:'items',
							idPath:'itemCode',
							descPath:'itemCode'
						}"
						@input="onItemChange(data.item.purchaseOrderSequence, ...arguments)">
					</ResourceInput>
					<span>{{data.item.description}}</span>
				</template>
			</b-table>
		`,
		data: function(){
			return {
				fields: [
					'itemInfo',
					'quantity',
					'unitCost',
					'extendedCost',
					'unitDiscountCode',
				],
				item: {}
			}
		},
		computed: {
			formItems: {
				get: function(){
					return this.items;
				},
				set: function(newItems){
					this.$emit('update', newItems)
				}
		
			}
		},
		methods: {
			onItemChange: function(sequence, newItemId, newItem){
				let oldItemIdx = this.formItems.findIndex( item => item.purchaseDetailSequence === sequence );
				
				this.formItems.splice(oldItemIdx, 1, newItem);
			}
		}
};

export default itemList;