const express = require('express');
const router = express.Router();
const databaseUtil = require('./database/databaseUtil');  // Ajuste o caminho conforme necessário
const Product = require('./entities/Product'); // Ajuste o caminho conforme necessário

// Rota para listar produtos
router.get('/products', async (req, res) => {
  try {
    const page = parseInt(req.query.page, 10) || 1;
    const itemsPerPage = 10;
    const totalProducts = await databaseUtil.getTotalProductsCount();
    const totalPages = Math.ceil(totalProducts / itemsPerPage);
    const startIndex = (page - 1) * itemsPerPage;

    console.log('page:', page, 'startIndex:', startIndex, 'itemsPerPage:', itemsPerPage); // Adicionando log para depuração

    const products = await databaseUtil.getProductsByRange(startIndex, itemsPerPage);
    res.json({ products, currentPage: page, totalPages });
  } catch (error) {
    res.status(500).send(error.message);
  }
});


// Rota para criar novo produto
router.post('/product', async (req, res) => {
  console.log('POST /product called'); // Adicione este log
  console.log('Request Body:', req.body); // Adicione este log
  try {
    const { name, shortDescription, brand, category, listPrice, cost } = req.body;
    if (!name || !listPrice) {
      res.status(400).send("Name and List Price are required.");
      return;
    }

    const product = new Product(null, name, shortDescription, brand, category, listPrice, cost);
    await databaseUtil.createProduct(product);
    res.status(201).send("Product created successfully");
  } catch (error) {
    res.status(500).send("Failed to create product");
  }
});

// Rota para atualizar um produto
router.put('/product/:id', async (req, res) => {
  console.log(`PUT /product/${req.params.id} called`); // Adicione este log
  console.log('Request Body:', req.body); // Adicione este log
  try {
    const { name, shortDescription, brand, category, listPrice, cost } = req.body;
    if (!name || !listPrice) {
      res.status(400).send("Name and List Price are required.");
      return;
    }

    const product = new Product(req.params.id, name, shortDescription, brand, category, listPrice, cost);
    await databaseUtil.updateProduct(product);
    res.send("Product updated successfully");
  } catch (error) {
    res.status(500).send("Failed to update product");
  }
});

// Rota para deletar um produto
router.delete('/product/:id', async (req, res) => {
  console.log(`DELETE /product/${req.params.id} called`); // Adicione este log
  try {
    await databaseUtil.deleteProduct({ id: req.params.id });
    res.send("Product deleted successfully");
  } catch (error) {
    res.status(500).send("Failed to delete product");
  }
});

module.exports = router;
