var RefreshButton = {
	template:`
		<b-button variant="outline-secondary" @click="$emit('refresh-click')">
			<span class="oi oi-reload"></span>
			<span class="ml-2">Refresh</span>
		</b-button>
	`
}

export default RefreshButton;