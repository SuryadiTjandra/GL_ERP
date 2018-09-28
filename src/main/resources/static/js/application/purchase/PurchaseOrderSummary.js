import ResourceInput from "/js/baseComponents/inputs/ResourceInput.js";
import DiscountInput from "/js/baseComponents/inputs/DiscountInput.js";

var Summary = {
	components: {
		ResourceInput, DiscountInput
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
					{{brutto.toFixed(2)}}
				</b-col>
			</b-row>
			<b-row class="my-3">
				<b-col cols="4" class="font-weight-bold">
					Discount Item
				</b-col>
				<b-col cols="4" class="text-right">
					{{itemDiscountRate.toFixed(2)}}%
				</b-col>
				<b-col cols="4" class="text-right">
					{{itemDiscount.toFixed(2)}}
				</b-col>
			</b-row>
			<b-row class="my-3">
				<b-col cols="3" class="font-weight-bold">
					Discount Nota
				</b-col>
				<b-col cols="3">
					<DiscountInput size="sm" 
						:amount="brutto - itemDiscount"
						v-model="formItem.discountCode"
						@update:calculation="onDiscountCalcFinish"
					>
					
					</DiscountInput>
				</b-col>
				<b-col cols="2" class="text-right">
					{{orderDiscountRate.toFixed(2)}}%
				</b-col>
				<b-col cols="4" class="text-right">
					{{orderDiscount.toFixed(2)}}
				</b-col>
			</b-row>
			<b-row class="my-3">
				<b-col cols="6" class="font-weight-bold">
					D.P.P.
				</b-col>
				<b-col cols="6" class="text-right">
					{{taxable.toFixed(2)}}
				</b-col>
			</b-row>
			<b-row class="my-3">
				<b-col cols="6" class="font-weight-bold">
					PPN
				</b-col>
				<b-col cols="6" class="text-right">
					{{taxAmount.toFixed(2)}}
				</b-col>
			</b-row>
			<b-row class="my-3">
				<b-col cols="6" class="font-weight-bold">
					Total
				</b-col>
				<b-col cols="6" class="text-right">
					{{netto.toFixed(2)}}
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
					.reduce((a, b) => Number(a) + Number(b), 0);
		},
		itemDiscount: function(){
			if (this.formItem.details == null) return 0.00;
			
			return this.formItem.details
					.map(det => det.unitDiscountRate/100 * det.extendedCost)
					.reduce((a, b) => Number(a) + Number(b), 0);
		},
		itemDiscountRate: function(){
			if (this.formItem.details == null) return 0.00;
			if (this.brutto == 0) return 0.00;
			return this.itemDiscount / this.brutto * 100;
		},
		orderDiscount: function(){
			let afterUnitDiscount = this.brutto - this.itemDiscount;
			if (afterUnitDiscount == 0) return 0.00;
			return this.formItem.discountRate/100 * afterUnitDiscount;
		},
		orderDiscountRate: function(){
			if (this.formItem.discountRate == null) return 0.00;
			return this.formItem.discountRate;
		},
		taxAmount: function(){
			if (this.formItem.taxRate == null) return 0.00;
			return this.formItem.taxRate/100 * this.taxable;
		},
		taxable: function(){
			return this.formItem.taxAllowance == true ?
					this.netto * 100/(100 + this.formItem.taxRate) :
					this.brutto - this.itemDiscount - this.orderDiscount;
		},
		netto: function(){
			return this.formItem.taxAllowance == true ?
					this.brutto - this.itemDiscount - this.orderDiscount :
					this.taxable + this.taxAmount;
		}
	},
	methods: {
		onDiscountCalcFinish: function(discCalc){
			this.formItem.discountRate = discCalc != null ? discCalc.discountRate : 0.00;
		}
	}
}

export default Summary;