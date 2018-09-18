import ResourceInput from "/js/baseComponents/inputs/ResourceInput.js";

var Summary = {
	components: {
		ResourceInput
	},
	props: ['formItem'],
	model: {
		prop: 'formItem',
		event: 'change'
	},
	template:`
	<b-row>
		<b-col cols="3"></b-col>
		<b-col>
			<b-row class="my-3">
				<b-col class="font-weight-bold">
					Brutto
				</b-col>
				<b-col class="text-right">
					{{brutto}}
				</b-col>
			</b-row>
			<b-row class="my-3">
				<b-col cols="4" class="font-weight-bold">
					Discount Item
				</b-col>
				<b-col cols="4" class="text-right">
					{{itemDiscountRate}}%
				</b-col>
				<b-col cols="4" class="text-right">
					{{itemDiscount}}
				</b-col>
			</b-row>
			<b-row class="my-3">
				<b-col cols="3" class="font-weight-bold">
					Discount Nota
				</b-col>
				<b-col cols="3">
					<ResourceInput v-model="formItem.discountCode" size="sm" 
						:resourceMetadata="{
							apiUrl:'/api/discounts',
							dataPath:'discounts',
							idPath:'discountCode',
							descPath:'description'
						}"
					>
					
					</ResourceInput>
				</b-col>
				<b-col cols="2" class="text-right">
					{{orderDiscountRate}}%
				</b-col>
				<b-col cols="4" class="text-right">
					{{orderDiscount}}
				</b-col>
			</b-row>
			<b-row class="my-3">
				<b-col cols="6" class="font-weight-bold">
					D.P.P.
				</b-col>
				<b-col cols="6" class="text-right">
					{{taxable}}
				</b-col>
			</b-row>
			<b-row class="my-3">
				<b-col cols="6" class="font-weight-bold">
					PPN
				</b-col>
				<b-col cols="6" class="text-right">
					{{taxAmount}}
				</b-col>
			</b-row>
			<b-row class="my-3">
				<b-col cols="6" class="font-weight-bold">
					Total
				</b-col>
				<b-col cols="6" class="text-right">
					{{netto}}
				</b-col>
			</b-row>
		</b-col>
		<b-col cols="3"></b-col>
	</b-row>
	`,
	computed: {
		brutto: function(){
			if (this.formItem.details == null) return 0.00;
			
			return this.formItem.details
					.map(det => det.extendedCost)
					.reduce((a, b) => a + b, 0)
					.toFixed(2);
		},
		itemDiscount: function(){
			if (this.formItem.details == null) return 0.00;
			
			return this.formItem.details
					.map(det => det.unitDiscountRate/100 * det.extendedCost)
					.reduce((a, b) => a + b, 0)
					.toFixed(2);
		},
		itemDiscountRate: function(){
			if (this.formItem.details == null) return 0.00;
			if (this.brutto == 0) return 0.00;
			return (this.itemDiscount / this.brutto * 100).toFixed(2);
		},
		orderDiscount: function(){
			let afterUnitDiscount = this.brutto - this.itemDiscount;
			if (afterUnitDiscount == 0) return 0.00;
			return (this.formItem.discountRate/100 * afterUnitDiscount).toFixed(2);
		},
		orderDiscountRate: function(){
			if (this.formItem.discountRate == null) return 0.00;
			return this.formItem.discountRate.toFixed(2);
		},
		taxable: function(){
			return (this.brutto - this.itemDiscount - this.orderDiscount).toFixed(2);
		},
		taxAmount: function(){
			if (this.formItem.taxRate == null) return 0.00;
			return (this.formItem.taxRate/100 * this.taxable).toFixed(2);
		},
		netto: function(){
			return (this.taxable - this.taxAmount).toFixed(2);
		}
	}
}

export default Summary;