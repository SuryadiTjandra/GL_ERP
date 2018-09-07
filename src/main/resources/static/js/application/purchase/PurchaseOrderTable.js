var table = {
	template:`
	<div>
		<!-- pagination -->
		<b-row>
			<b-col><span class="align-bottom text-muted font-weight-light font-italic"> 
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
		<b-table striped hover show-empty small responsive
			:items="items" 
			:fields="fields"
			:per-page="pageSize"
			:busy="isBusy">
		</b-table>
		
	</div>
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
			fields: [{
					key: 'companyId',
					label: 'Company'
				},{
					key: 'purchaseOrderNumber',
					label: 'Number'
				}, {
					key: 'purchaseOrderType',
					label: 'Type'
				}],
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
		
		onPaginationChange: function(page){
			let paramObj = {
				page: page - 1,
				size: this.pageSize,
			};
			if (this.sortBy !== null){
				paramObj.sort = this.sortBy + "," + this.sortDir;
			}
			
			this.loadData(paramObj)
		},
		
		loadData: function(param){
			let paramStr = Object.keys(param).map(key => key + '=' + param[key]).join('&');
			this.isBusy = true;
			
			fetch(this.apiUrl + "?" + paramStr)
				.then(result => result.json())
				.then (result => {
					this.items = result._embedded.purchaseOrders;
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