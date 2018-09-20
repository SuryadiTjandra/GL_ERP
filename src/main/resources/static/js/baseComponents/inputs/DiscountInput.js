import ResourceInput from "./ResourceInput.js";

var DiscountInput = {
		components: { ResourceInput },
		props: {
			discountCode : String,
			amount: Number,
			showDetail: Boolean
		},
		model: {
			prop:'discountCode',
			event:'change'
		},
		template:`
		<div>
			<ResourceInput size="sm" 
				:selectedId="discountCode"
				:resourceMetadata="{
					apiUrl:'/api/discounts',
					dataPath:'discounts',
					idPath:'discountCode',
					descPath:'description'
				}"
				@input="onInput">
			</ResourceInput>
			<template v-if="showDetail == true && discountCode != null && discountCode.length > 0">
				<span>{{discountRate}} %</span></br>
				<span>{{discountAmount}}</span>
			</template>
		</div>
		`,
		data: function(){
			return {
				discountRate: 0.00,
				discountAmount: 0.00,
				calculateLink : ""
			}
		},
		methods: {
			onInput: function(discCode, disc){
				if (disc == null){
					this.setData({});
					this.$emit('change', discCode, disc, null);
					return;
				}
				
				this.calculateLink = disc._links.calculate.href;
				this.fetchCalculation(this.calculateLink, this.amount)
					.then(res => {
						this.setData(res);
						this.$emit('change', discCode, disc, res);
					});
			},
			
			fetchCalculation: function(link, amount){
				return fetch(link + "?amount=" + amount)
					.then(res => res.json())
			},
			setData: function(obj){
				let amt = Number(obj.discountAmount);
				this.discountAmount = Number.isNaN(amt) ? 0.00 : amt.toFixed(2);

				let rate = Number(obj.discountRate);
				this.discountRate = Number.isNaN(rate) ? 0.00 : rate.toFixed(2);
			}
		},
		watch: {
			amount: function(amount){
				if (this.calculateLink == null || this.calculateLink.length == 0)
					return;
				
				this.fetchCalculation(this.calculateLink, amount)
					.then(res => this.setData(res));
			}
		}
}

export default DiscountInput;