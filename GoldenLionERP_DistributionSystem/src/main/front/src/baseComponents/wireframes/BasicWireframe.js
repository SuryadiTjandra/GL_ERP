
var BasicWireframe = {
		template:`
		<b-container fluid>
			<b-row>
				<b-col cols='1'>
					<img src="/images/ags.jpg" alt="AGS Logo" height="70" weight="70"/>
				</b-col>
				<b-col>
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