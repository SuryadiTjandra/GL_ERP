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
		}
		
	}
}

export default PurchaseOrderPage;