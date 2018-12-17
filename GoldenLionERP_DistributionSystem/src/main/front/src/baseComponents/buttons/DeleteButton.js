var DeleteButton = {
	template:`
		<b-button 
			variant="outline-secondary" 
			size="sm"
			@click="$emit('delete-click', this)"
			style="padding:0; border-color:transparent;"
			title="Hapus">
			<span class="oi oi-trash" ></span>
		</b-button>
	`
}

export default DeleteButton;