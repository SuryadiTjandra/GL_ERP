import CreateButton from "../../baseComponents/buttons/CreateButton.js";
//import VuetablePaginationBootstrap from "../../baseComponents/table/VuetablePaginationBootstrap.js";
import PurchaseOrderTable from "./PurchaseOrderTable.js";
import PurchaseOrderModal from "./PurchaseOrderModal.js"

var PurchaseOrderPage = {
	data: function(){
		return {
			apiUrl: "/api/purchaseOrders",
			
			modalVisible: false,
			modalMode: "add",
			modalItem: null
		}
	},
	components:{
		CreateButton,
		PurchaseOrderTable,
		PurchaseOrderModal
	},
	template: `
		<div>
			<b-container fluid>
				<b-row><b-col>
					<CreateButton @create-click="createButtonClicked">
					</CreateButton>
				</b-col></b-row>
				<b-row>
					<PurchaseOrderTable 
						:apiUrl="apiUrl" 
						:loadOnCreate="true"
						@view-item="onViewItem"
						@edit-item="onEditItem">
					</PurchaseOrderTable>
				</b-row>
			</b-container>
			<PurchaseOrderModal 
				v-model="modalVisible" 
				:mode="modalMode"
				:item="modalItem"
				@ok="onModalOk"
				>
			</PurchaseOrderModal>
		</div>
	`,
	methods: {
		createButtonClicked: function(event){
			//alert("Create Button cliked!");
			this.modalItem = null;
			this.modalMode = "add";
			this.modalVisible = true;
		},
		onViewItem: function(item){
			this.modalItem = item;
			this.modalMode = "view";
			this.modalVisible = true;
		},
		onEditItem: function(item){
			this.modalItem = item;
			this.modalMode = "edit";
			this.modalVisible = true;
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
		}
		
	}
}

export default PurchaseOrderPage;