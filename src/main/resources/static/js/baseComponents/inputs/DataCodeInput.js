var DataCodeInput = {
	props: ['productCode', 'systemCode', 'required', 'readOnly', 'selected'],
	model:{
		prop:"selected",
		event:"change"
	},
	template: `
		<b-form-select 	:required="required"
			v-model="selected" 
			:readOnly="readOnly"
			:options="options" 
			@change="$emit('change', $event)" >
		</b-form-select>
	`,
	data: function(){
		return {
			options: []
		}
	},
	created: function(){
		let url = "/api/dataCodes/" + this.productCode + "/" + this.systemCode
		
		const csrfHeader = document.getElementsByName("_csrf_header")[0].getAttribute("content");
		const csrfToken = document.getElementsByName("_csrf")[0].getAttribute("content");				
		let headers = new Headers();
		headers.append(csrfHeader, csrfToken);
		headers.append('Content-Type', 'application/json');
		
		fetch(url, {
			method: "GET",
			headers: headers
		})
		.then(res => res.json())
		.then(res => {
			this.options = res.map(r => ({
				text: r.dataCode + " (" + r.description + ")",
				value: r.dataCode
			}));
			this.options;
		});
		
	}
}

export default DataCodeInput;