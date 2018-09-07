
var BasicWireframe = {
		template:`
		<b-container fluid>
			<b-row>
				<b-col cols='2'>
					<img src="/images/ags.jpg" alt="AGS Logo" height="80" weight="80"/>
				</b-col>
				<b-col class="my-2">
					<b-row>
						<h4>LIONS ERP</h4>
					</b-row>
					<b-row>
						<b-col>
							<a href="/index">Home</a>
						</b-col>
					</b-row>
				</b-col>
			</b-row>
			<hr/>
			<slot></slot>
		</b-container>
		`
} 

export default BasicWireframe;