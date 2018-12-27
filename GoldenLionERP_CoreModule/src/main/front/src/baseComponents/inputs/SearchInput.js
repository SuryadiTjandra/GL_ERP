import startCase from "lodash/startCase";
import isString from "lodash/isString";
import has from "lodash/has";

var SearchInput = {
	props: {
		searchValue: {
			type: String
		},
		searchBy: {
			type: String
		},
		searchByOptions: {
			type: Array,
			required: true,
			validator: function(array){
				return array.every((value) => isString(value) || has(value, "value"));
			}
		},
		size: {
			type: String,
			default: "sm"
		},
		useSearchByPlaceholder: {
			type: Boolean,
			default: true
		}
	},
	template:`
	<b-input-group :size="size">
		<b-input type="text" 
			:value="searchValueInternal"
			@change="searchValueInternal = $event"
			placeholder="Search">
		</b-input>
		<b-input-group-append>
			<b-select v-model="searchByInternal" :options="searchOptionsInternal" size="sm">
				<template v-if="useSearchByPlaceholder" slot="first">
					<option :value="null" disabled>--Search By--</option> 
				</template>
			</b-select>
		</b-input-group-append>
	</b-input-group>
	`,
	computed: {
		searchValueInternal: {
			get: function(){
				return this.searchValue;
			},
			set: function(newSv){
				this.$emit("update:searchValue", newSv);
				this.$emit("update", newSv, this.searchBy);
			}
		},
		searchByInternal: {
			get: function(){
				return this.searchBy;
			},
			set: function(newSb){
				this.$emit("update:searchBy", newSb);
				this.$emit("update", this.searchValue, newSb);
			}
		},
		searchOptionsInternal: function(){
			return this.searchByOptions.map(function(option){
				if (isString(option)){
					return {
						value: option,
						text: startCase(option)
					};
				} else if (has(option, "value") && has(option, "text")){
					return option;
				} else {
					return {
						value: option.value,
						text: startCase(option.value)
					}
				} 
			});
		}
	}
}

export default SearchInput;