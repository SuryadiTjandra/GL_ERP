import ResourceSelectionModal from "./ResourceSelectionModal.js";
import AJAXPerformer from "/js/util/AJAXPerformer.js"

var ResourceInput = {
	components: {ResourceSelectionModal},
	props: ['resourceMetadata', 'readOnly', 'selectedId', 'size'],
	model:{
		prop:'selectedId',
		event:'input'
	},
	template: `
	<div>
		<b-input-group :size="size">
			<b-form-input type="text" readonly
				:value="selected[descPath]">
			</b-form-input>
			
			<b-input-group-append v-if="!readOnly">
				<b-button variant="outline-secondary" @click="onRemoveSelected">
					<span class="oi oi-x"></span>
				</b-button>
				<b-button variant="outline-primary" @click="onPick">
					<span class="oi oi-chevron-right"></span>
				</b-button>
			</b-input-group-append>
		</b-input-group>
		
		<ResourceSelectionModal v-if="!readOnly"
			v-model="modalVisible"
			:resourceMetadata="resourceMetadata"
			@ok="onModalOk">
		
		</ResourceSelectionModal>

	</div>
	`,
	data: function(){
		return {
			selected: {},
			idPath: this.resourceMetadata.idPath,
			descPath: this.resourceMetadata.descPath,
			
			modalVisible: false
		}
	},
	methods: {
		onRemoveSelected: function(){
			this.selected = {};
			this.$emit('input', null, null);
			this.$emit('change', null, null);
		},
		onPick: function(){
			this.modalVisible = true;
		},
		onModalOk: function(selected){
			this.selected = selected;
			this.$emit('input', selected[this.idPath], selected)
			this.$emit('change', selected[this.idPath], selected)
		}
	},
	watch:{
		selectedId : function(newSelectedId, oldSelectedId){
			if (newSelectedId == oldSelectedId){
				return;
			}
			
			if (newSelectedId == null || 
				newSelectedId.trim().length === 0 ){
				
				this.selected = {};
				this.$emit('input', null, null);
				return;
			} 
				
			
			let url = this.resourceMetadata.apiUrl + "/" + newSelectedId;
			fetch(url)
				.then(res => res.json())
				.then(res => {
					this.selected = res;
					this.$emit('input', this.selected[this.idPath], this.selected)
				});
				
		}
	},
	created: function(){
		if (this.selectedId == null || this.selectedId.trim().length === 0)
			return;
		
		let url = this.resourceMetadata.apiUrl + "/" + this.selectedId;
		AJAXPerformer.getAsJson(url)
			.then(res => this.selected = res);
	}
};

export default ResourceInput;