var ViewButton = {
	template:`
		<b-button 
			variant="outline-secondary" 
			size="sm"
			@click="$emit('view-click', this)"
			style="padding:0; border-color:transparent;"
			title="View">
			<span class="oi oi-magnifying-glass" ></span>
		</b-button>
	`
}

export default ViewButton;