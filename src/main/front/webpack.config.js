const path = require('path');

module.exports = {
  mode: 'development',
  entry: {
	  purchaseOrder: './src/application/purchase/PurchaseOrderApp.js'
  },
  output: {
    filename: 'main.js',
    path: path.resolve(__dirname, 'dist')
  },
  resolve: {
	  modules: [path.resolve(__dirname, 'src'), 'node_modules'],
  }
};