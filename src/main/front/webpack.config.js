const path = require('path');

module.exports = {
  mode: 'development',
  entry: {
	  purchaseOrder: './src/application/purchase/app.js',
	  salesOrder: './src/application/sales/SalesOrderApp.js',
  },
  output: {
    filename: '[name].bundle.js',
    path: path.resolve(__dirname, 'dist')
  },
  resolve: {
	  modules: [path.resolve(__dirname, 'src'), 'node_modules'],
	  alias: {
		  'vue': 'vue/dist/vue.esm.js'
	  }
  },
  module: {
	  rules: [
		  {
			  test: /\.css$/,
			  use: ['style-loader', 'css-loader']
		  }
	  ]
  }
};