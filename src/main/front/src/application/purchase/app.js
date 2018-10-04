import Vue from 'vue';
import BootstrapVue from 'bootstrap-vue'
import App from './PurchaseOrderApp.js';

Vue.use(BootstrapVue);

new Vue({
	  el: '#app',
	  render: h => h(App, {props: {defaultItem: defaultItem}})
	})