import startCase from "lodash/startCase";
import SearchInput from "./SearchInput.js";

var ResourceSelectionModal = {
	components: {SearchInput},
	model:{
		prop:'visible',
		event:'change'
	},
	props:['visible','resourceMetadata'],
	template:`
	<b-modal :visible="visible" @change="$emit('change',$event)" size="lg" :hide-header="true"
		@ok="onOk"
		@show="onShow">
	
		<b-row>
			<b-col>
				<SearchInput class="mb-4"
					:searchValue.sync="searchValue"
					:searchBy.sync="searchBy"
					:searchByOptions="searchByOptions">
				</SearchInput>
			</b-col>
			<b-col>
				<b-pagination v-if="usePagination"
					align="right"
					:value="currentPagePlus"
					:total-rows="totalElements"
					:per-page="pageSize"
					@change="onPaginationChange">					
				</b-pagination>
			</b-col>
		</b-row>
		
		<b-table hover small
			:fields="fields"
			:busy="isBusy"
			:items="items"
			@row-clicked="onItemSelect"
			:filter="tableFilterFunction">
			
			<template slot="checkbox" slot-scope="data">
				<b-form-checkbox 
					:checked="selected == data.item"
					@change="onItemSelect(data.item)"
					state="null">
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
			searchBy: this.resourceMetadata.idPath,
			searchValue: null,
			
			usePagination: true,
			totalElements: 0,
			currentPage: 0,
			pageSize: 0,
			
			isBusy: false
		}
	},
	computed: {
		currentPagePlus: function(){ return this.currentPage + 1},
		searchByOptions: function(){
			//return []
			return [{
				value: this.resourceMetadata.idPath,
				text: startCase(this.resourceMetadata.idPath)
			},{
				value: this.resourceMetadata.descPath,
				text: startCase(this.resourceMetadata.descPath)
			}]
		}
	},
	methods:{
		onOk: function(){
			this.$emit('ok', this.selected);
		},
		onShow: function(){
			this.loadData({});
		},
		onItemSelect: function(item){
			this.selected = item;
		},
		onPaginationChange: function(page){
			this.loadData(this.createParamObject(
				page - 1, 
				this.pageSize, 
				this.sortBy, 
				this.sortDir
			));
		},

		loadData: function(param){
			let paramStr = Object.keys(param).map(key => key + '=' + param[key]).join('&');
			this.isBusy = true;
			
			fetch(this.apiUrl + "?" + paramStr)
				.then(result => result.json())
				.then (result => {
					this.items = result._embedded[this.dataPath];
					
					if (result.page){
						this.usePagination = true;
						this.currentPage = result.page.number;
						this.totalElements = result.page.totalElements;
						this.pageSize = result.page.size;
					}else{
						this.usePagination = false;
					}
				})
				.catch(error => alert(error))
				.finally(() => {
					this.isBusy = false
				})
		},
		createParamObject: function(page, size, sortBy, sortDir, searchObject){
			let paramObj = {
				page: page,
				size: size,
			};
			if (sortBy !== null){
				paramObj.sort = sortBy + "," + sortDir;
			}
			
			if (this.searchValue != null){
				paramObj[this.searchBy] = this.searchValue;
			}
			return paramObj;
		},
		
		tableFilterFunction: function(item){
			//if we use pagination, let the server handle the filtering
			if (this.usePagination)
				return true;
			
			//is search is empty, don't filter anything
			if (this.searchValue == null || this.searchValue.trim().length == 0)
				return true;
			
			return String(item[this.searchBy]).toUpperCase().includes(this.searchValue.toUpperCase());
		}
	}
};

export default ResourceSelectionModal;