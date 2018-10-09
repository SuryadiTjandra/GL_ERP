const path = require('path');
const CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
  mode: 'development',
  entry: {
	  purchaseOrder: './src/application/purchase/app.js',
	  salesOrder: './src/application/sales/app.js',
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
  },
  plugins: [
	  new CleanWebpackPlugin('dist')
  ]
	  
};