import RefreshButton from "baseComponents/buttons/RefreshButton.js";
import ViewButton from "baseComponents/buttons/ViewButton.js";
import EditButton from "baseComponents/buttons/EditButton.js";
import CreateButton from "baseComponents/buttons/CreateButton.js";
import ResourceTable from "baseComponents/tables/ResourceTable.js";

var table = {
	components: {
		CreateButton, RefreshButton, ViewButton, EditButton, ResourceTable
	},
	template:`
	<ResourceTable
		:apiUrl="apiUrl"
		resultPath="_embedded.salesOrders"
		:fields="fields"
		:primaryKeys="['companyId', 'salesOrderNumber', 'salesOrderType']"
		:itemEditable="true"
		:itemDeletable="false"
		@create-clicked="onCreate"
		@edit-clicked="onItemEdit"
		@view-clicked="onItemView"
		>
	
	</ResourceTable>
	`,
	props: {
		apiUrl: String,
		loadOnCreate: {
			type: Boolean,
			default: true
		}
	},
	data: function(){
		return {
			//table data
			fields: [
				{
					key: 'actions',
					label:" "
				},
				{
					key: 'companyId',
					label: 'Company'
				},{
					key: 'salesOrderNumber',
					label: 'No. Order',
					sortable: true
				}, {
					key: 'orderDate',
					label: 'Tgl. Order',
					sortable:true,
					formatter: (value) => new Date(value).toLocaleDateString('id-ID')
				},{
					key: 'businessUnitId',
					label: 'Unit Kerja'
				},{
					key:'customerId',
					label: 'Kode Customer'
				}, {
					key:'customerOrderDate',
					label: 'Tgl. PO Customer',
					sortable:true,
					formatter: (value) => new Date(value).toLocaleDateString('id-ID')
				},{
					key:'customerOrderNumber',
					label: 'No. PO Customer',
				},{
					key: 'description',
					label: 'Keterangan',
					formatter: (value) => value.length > 50 ? 
								value.substring(0, 47) + "..." : 
								value
				}, {
					key: 'lastUpdateUserId',
					label: 'User ID Ubah',
					sortable: true
				}, {
					key: 'lastUpdateDateTime',
					label: 'Waktu Ubah',
					sortable:true,
					formatter: (value) => new Date(value).toLocaleString()
				}
				],
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
				this.currentSize,
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
			if (["companyId", "salesOrderNumber"].includes(ctx.sortBy))
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
					this.items = result._embedded.salesOrders;
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

export default table;