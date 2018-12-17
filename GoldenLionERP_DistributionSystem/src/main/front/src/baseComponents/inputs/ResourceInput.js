import ResourceSelectionModal from "./ResourceSelectionModal.js";
import AJAXPerformer from "util/AJAXPerformer.js"

var ResourceInput = {
	components: {ResourceSelectionModal},
	props: ['resourceMetadata', 'readOnly', 'selectedId', 'size', 'required'],
	model:{
		prop:'selectedId',
		event:'change'
	},
	template: `
	<div>
		<b-input-group :size="size">
			<b-form-input type="text" :required="required" :readOnly="readOnly"
				@focus.native.stop.prevent="$event.target.blur()"
				:value="selected[displayPath]">
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
		let idPath = this.resourceMetadata.idPath;
		let descPath = this.resourceMetadata.descPath || idPath;
		let displayPath = this.resourceMetadata.displayPath || descPath;
		
		return {
			selected: {},
			idPath: idPath,
			descPath: descPath,
			displayPath: displayPath,
			
			modalVisible: false
		}
	},
	methods: {
		onRemoveSelected: function(){
			this.selected = {};
			this.$emit('update:item', null);
			this.$emit('change', null);
		},
		onPick: function(){
			this.modalVisible = true;
		},
		onModalOk: function(selected){
			this.selected = selected;
			this.$emit('update:item', selected)
			this.$emit('change', selected[this.idPath])
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
				this.$emit('update:item', null);
				return;
			} 
				
			
			let url = this.resourceMetadata.apiUrl + "/" + newSelectedId;
			fetch(url)
				.then(res => res.json())
				.then(res => {
					this.selected = res;
					this.$emit('update:item', this.selected);
				});
				
		}
	},
	created: function(){
		if (this.selectedId == null || this.selectedId.trim().length === 0)
			return;
		
		let url = this.resourceMetadata.apiUrl + "/" + this.selectedId;
		AJAXPerformer.getAsJson(url)
			.then(res => {
				this.selected = res;
				this.$emit('update:item', this.selected);
			})
	}
};

export default ResourceInput;