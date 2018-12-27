import RefreshButton from "baseComponents/buttons/RefreshButton";
import ViewButton from "baseComponents/buttons/ViewButton";
import EditButton from "baseComponents/buttons/EditButton";
import CreateButton from "baseComponents/buttons/CreateButton";
import DeleteButton from "baseComponents/buttons/DeleteButton";
import {extractPath} from "util/PathExtractor.js";

var ResourceTable = {
		components: {
			CreateButton, RefreshButton, ViewButton, EditButton, DeleteButton
		},
		props: {
			apiUrl: String,
			resultPath: String,

			loadOnCreate: {
				type: Boolean,
				default: true
			},
			fields: Array,
			primaryKeys: Array,

			itemEditable: {type: Boolean, default:true},
			itemDeletable: {type: Boolean, default:true}
		},
		template:`
		<b-container fluid>
			<!-- pagination -->
			<b-row>
				<b-col>
					<CreateButton @create-click="onCreate"></CreateButton>
					<RefreshButton @refresh-click="onRefresh"></RefreshButton>
				</b-col>
				<b-col class="text-right align-bottom">
					<span
						v-if="totalElements > 0"
						class="text-muted font-weight-light font-italic mt-3">
						{{startPos}} - {{endPos}} (Total: {{totalElements}})
					</span></b-col>
				<b-col>
					<b-pagination
						align="right"
						:value="currentPagePlus"
						:total-rows="totalElements"
						:per-page="pageSize"
						@change="onPaginationChange"
					>
					</b-pagination>
				</b-col>
			</b-row>
			<!-- table -->
			<b-table striped hover show-empty small responsive no-local-sorting
				:items="items"
				:fields="fields"
				:per-page="pageSize"
				:busy="isBusy"
				@sort-changed="onSortChanged"
				style="white-space:nowrap">

				<template slot="actions" slot-scope="data">
					<ViewButton @view-click="onItemView(data.item, $event.target)">
					</ViewButton>
					<EditButton @edit-click="onItemEdit(data.item, $event.target)" v-if="itemEditable">
					</EditButton>
					<DeleteButton @delete-click="onItemDelete(data.item, $event.target)" v-if="itemDeletable">
					</DeleteButton>
				</template>
			</b-table>

		</b-container>
		`,
		data: function(){
			return {
				//table data
				items: [],

				//pagination
				currentPage: 1,
				pageSize: 20,
				totalElements: 0,
				sortBy: null,
				sortDesc: false,

				//etc
				isBusy: false
			}
		},
		computed: {
			currentPagePlus: function(){
				//this property is because b-pagination is 1-based, while the server page data is 0-based
				return this.currentPage + 1;
			},
			startPos: function(){
				return (this.pageSize * this.currentPage)  + 1;
			},
			endPos: function(){
				return Math.min(
						this.totalElements,
						this.pageSize * (this.currentPage + 1)
				);
			},
			sortDir: function(){
				return this.sortDesc ? "desc" : "asc";
			}
		},
		methods:{
			onRefresh: function(){
				this.loadData(this.createParamObject(
					this.currentPage,
					this.pageSize,
					this.sortBy,
					this.sortDir
				))
			},
			onPaginationChange: function(page){
				this.loadData(this.createParamObject(
					page - 1,
					this.pageSize,
					this.sortBy,
					this.sortDir
				));
			},
			onSortChanged: function(ctx){
				if (this.primaryKeys.includes(ctx.sortBy))
					this.sortBy = "pk." + ctx.sortBy;
				else
					this.sortBy = ctx.sortBy;
				this.sortDesc = !ctx.sortDesc;
				this.loadData(this.createParamObject(
					this.currentPage,
					this.pageSize,
					this.sortBy,
					this.sortDir
				))
			},
			onCreate: function(){
				this.$emit('create-clicked')
			},
			onItemView: function(item){
				this.$emit('view-clicked', item);
			},
			onItemEdit: function(item){
				this.$emit('edit-clicked', item);
			},

			//normal methods to use in other methods
			createParamObject: function(page, size, sortBy, sortDir){
				let paramObj = {
					page: page,
					size: size,
				};
				if (sortBy !== null){
					paramObj.sort = sortBy + "," + sortDir;
				}
				return paramObj;
			},
			loadData: function(param){
				let paramStr = Object.keys(param).map(key => key + '=' + param[key]).join('&');
				this.isBusy = true;

				fetch(this.apiUrl + "?" + paramStr)
					.then(result => result.json())
					.then (result => {
						this.items = extractPath(result, this.resultPath);//result._embedded.purchaseOrders;
						this.currentPage = result.page.number;
						this.totalElements = result.page.totalElements;
						this.pageSize = result.page.size;
					})
					.catch(error => alert(error))
					.finally(() => {
						this.isBusy = false
					})
			}


		},
		created: function(){
			if (!this.loadOnCreate)
				return;
			this.loadData({});
		}
}

export default ResourceTable;
