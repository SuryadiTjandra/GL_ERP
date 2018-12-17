var VoidButton = {
	template:`
		<b-button 
			variant="outline-danger" 
			size="sm"
			@click="$emit('void-click', this)"
			style="padding:0; border-color:transparent;"
			title="Void">
			<span class="oi oi-x" ></span>
		</b-button>
	`
}

export default VoidButton;