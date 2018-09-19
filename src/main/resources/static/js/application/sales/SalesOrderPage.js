import CreateButton from "../../baseComponents/buttons/CreateButton.js";
//import VuetablePaginationBootstrap from "../../baseComponents/table/VuetablePaginationBootstrap.js";
import SalesOrderTable from "./SalesOrderTable.js";
import SalesOrderForm from "./SalesOrderForm.js";

var SalesOrderPage = {
	props: ['defaultItem'],
	data: function(){
		return {
			apiUrl: "/api/salesOrders",
			
			formVisible: false,
			formMode: "add",
			formItem: {}
		}
	},
	components:{
		CreateButton, SalesOrderTable, SalesOrderForm
	},
	template: `
		<b-container fluid>
			<transition name="fade">
				<div v-show="!formVisible">
					<SalesOrderTable 
						:apiUrl="apiUrl" 
						:loadOnCreate="true"
						@create-clicked="onCreateItem"
						@view-clicked="onViewItem"
						@edit-clicked="onEditItem">
					</SalesOrderTable>
				</div>
			</transition>
			<transition name="fade">
				<b-container fluid v-show="formVisible">
					<SalesOrderForm
						:mode="formMode"
						:item="formItem"
						@cancel="onFormCancel"
						@save="onFormSave">
					
					</SalesOrderForm>
				</b-container>
			</transition>
		</b-container>
	`,
	methods: {
		onCreateItem: function(event){
			this.formItem = this.defaultItem;
			this.formMode = "add";
			this.formVisible = true;
		},
		onViewItem: function(item){
			this.formItem = item;
			this.formMode = "view";
			this.formVisible = true;
		},
		onEditItem: function(item){
			this.formItem = item;
			this.formMode = "edit";
			this.formVisible = true;
		},
		onModalOk: function(itemLink, formItem){
			const method = this.modalMode == "add" ? "POST" : "PATCH";
			const link = this.modalMode == "add" ? this.apiUrl : itemLink;
			
			const csrfHeader = document.getElementsByName("_csrf_header")[0].getAttribute("content");
			const csrfToken = document.getElementsByName("_csrf")[0].getAttribute("content");				
			let headers = new Headers();
			headers.append(csrfHeader, csrfToken);
			headers.append('Content-Type', 'application/json');
			
			fetch(link, {
				method: method,
				body: JSON.stringify(formItem),
				headers: headers
			})
			.then(res => res.json())
			.then(res => alert(res));
		},
		onFormCancel: function(){
			this.formVisible = false;
			this.formItem = {};
		},
		onFormSave: function(formItem){
			
			const method = this.formMode == "add" ? "POST" : "PATCH";
			const link = this.formMode == "add" ? this.apiUrl : formItem._links.self.href;
			
			const csrfHeader = document.getElementsByName("_csrf_header")[0].getAttribute("content");
			const csrfToken = document.getElementsByName("_csrf")[0].getAttribute("content");				
			let headers = new Headers();
			headers.append(csrfHeader, csrfToken);
			headers.append('Content-Type', 'application/json');
			
			fetch(link, {
				method: method,
				body: JSON.stringify(formItem),
				headers: headers
			})
			.then(res => res.ok ? 
						res.json() : 
						Promise.reject("Error: " + res.status + " " + res.statusText))
			.then(res => {
				alert("Sales Order created");
				this.formVisible = false;
				this.formItem = {};
			})
			.catch(err => alert(err));
		}
		
	}
}

export default SalesOrderPage;