import SalesOrderPage from './SalesOrderPage';
import BasicWireframe from 'baseComponents/wireframes/BasicWireframe';

let SalesOrderApp = {
	props: ['defaultItem'],
	components: { SalesOrderPage, BasicWireframe},
	template: `
		<BasicWireframe>
			<h4>SalesOrder</h4>
			<SalesOrderPage :default-item="defaultItem" />
		</BasicWireframe>
	`
}

export default SalesOrderApp;