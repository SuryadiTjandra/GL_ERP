var CreateButton = {
		template: `
			<b-button variant="primary" @click="$emit('create-click')">
				<span class="oi oi-plus"></span>
				<span class="ml-2">Baru</span>
			</b-button>
		`
}

export default CreateButton;