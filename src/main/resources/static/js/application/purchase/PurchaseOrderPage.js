import CreateButton from "../../baseComponents/buttons/CreateButton.js";
//import VuetablePaginationBootstrap from "../../baseComponents/table/VuetablePaginationBootstrap.js";
import PurchaseOrderTable from "./PurchaseOrderTable.js"

var PurchaseOrderPage = {
	data: function(){
		return {
			apiUrl: "/api/purchaseOrders"
		}
	},
	components:{
		CreateButton,
		PurchaseOrderTable
	},
	template: `
		<b-container fluid>
			<b-row><b-col>
				<CreateButton @create-click="createButtonClicked">
				</CreateButton>
			</b-col></b-row>
			<b-row>
				<PurchaseOrderTable :apiUrl="apiUrl" :loadOnCreate="true">
				</PurchaseOrderTable>
			</b-row>
		</b-container>
	`,
	methods: {
		createButtonClicked: function(event){
			alert("Create Button cliked!");
		}
		
	}
}

export default PurchaseOrderPage;