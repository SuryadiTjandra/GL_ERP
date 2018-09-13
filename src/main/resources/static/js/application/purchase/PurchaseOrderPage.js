import CreateButton from "../../baseComponents/buttons/CreateButton.js";
//import VuetablePaginationBootstrap from "../../baseComponents/table/VuetablePaginationBootstrap.js";
import PurchaseOrderTable from "./PurchaseOrderTable.js";
import PurchaseOrderModal from "./PurchaseOrderModal.js";
import PurchaseOrderForm from "./PurchaseOrderForm.js";

var PurchaseOrderPage = {
	props: ['defaultItem'],
	data: function(){
		return {
			apiUrl: "/api/purchaseOrders",
			
			modalVisible: false,
			modalMode: "add",
			modalItem: null,
			
			formVisible: false,
			formMode: "add",
			formItem: {}
		}
	},
	components:{
		CreateButton, PurchaseOrderTable, PurchaseOrderModal, PurchaseOrderForm
	},
	template: `
		<b-container fluid>
			<transition name="fade">
				<div v-show="!formVisible">
					<PurchaseOrderTable 
						:apiUrl="apiUrl" 
						:loadOnCreate="true"
						@create-clicked="onCreateItem"
						@view-clicked="onViewItem"
						@edit-clicked="onEditItem">
					</PurchaseOrderTable>
				</div>
			</transition>
			<PurchaseOrderModal 
				v-model="modalVisible" 
				:mode="modalMode"
				:item="modalItem"
				@ok="onModalOk"
				>
			</PurchaseOrderModal>
			<transition name="fade">
				<b-container fluid v-show="formVisible">
					<PurchaseOrderForm
						:mode="formMode"
						:item="formItem"
						@cancel="onFormCancel"
						@save="onFormSave">
					
					</PurchaseOrderForm>
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
			this.formVisible = false
		},
		onFormSave: function(){
			this.formVisible = false
		}
		
	}
}

export default PurchaseOrderPage;