var EditButton = {
	template:`
		<b-button 
			variant="outline-secondary" 
			size="sm"
			@click="$emit('edit-click', this)"
			style="padding:0; border-color:transparent;"
			title="Edit">
			<span class="oi oi-pencil" ></span>
		</b-button>
	`
}

export default EditButton;