var ResourceSelectionModal = {
	model:{
		prop:'visible',
		event:'change'
	},
	props:['visible','resourceMetadata'],
	template:`
	<b-modal :visible="visible" @change="$emit('change',$event)" size="lg"
		@ok="onOk"
		@show="onShow">
	
		<b-table hover small
			:fields="fields"
			:busy="isBusy"
			:items="items"
			@row-clicked="onItemSelect">
			
			<template slot="checkbox" slot-scope="data">
				<b-form-checkbox 
					:checked="selected == data.item"
					@change="onItemSelect(data.item)">
				</b-form-checkbox>
			</template>
		</b-table>
	</b-modal>
	`,
	data: function(){
		return {
			items:[],
			selected: null,
			
			idPath: this.resourceMetadata.idPath,
			descPath: this.resourceMetadata.descPath,
			dataPath: this.resourceMetadata.dataPath, 
			apiUrl: this.resourceMetadata.apiUrl,
			
			fields: ["checkbox", this.resourceMetadata.idPath, this.resourceMetadata.descPath],
			
			totalElements: 0,
			currentPage: 1,
			pageSize: 0,
			
			isBusy: false
		}
	},
	computed: {
		currentPagePlus: function(){ return this.currentPage + 1}
	},
	methods:{
		onOk: function(){
			this.$emit('ok', this.selected);
		},
		onShow: function(){
			this.loadData();
		},
		onItemSelect: function(item){
			this.selected = item;
		},

		loadData: function(){
			fetch(this.apiUrl + "?size=1000")
			.then(result => result.json())
			.then (result => {
				this.items = result._embedded[this.dataPath];
				//this.currentPage = result.page.number;
				//this.totalElements = result.page.totalElements;
				//this.pageSize = result.page.size;
			})
			.catch(error => alert(error))
			.finally(() => {
				this.isBusy = false
			})
		}
	}
};

export default ResourceSelectionModal;