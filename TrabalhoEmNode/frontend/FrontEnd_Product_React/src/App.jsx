// src/App.jsx
import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [products, setProducts] = useState([]);
  const [currentProduct, setCurrentProduct] = useState({
    name: '',
    shortDescription: '',
    brand: '',
    category: '',
    listPrice: '',
    cost: ''
  });

  const [search, setSearch] = useState('');

  const fetchProducts = async () => {
    try {
      const response = await fetch('http://localhost:3000/api/products?page=1');
      const data = await response.json();
      console.log('Fetched Products:', data.products); // Adicione um log para verificar os dados
      setProducts(data.products);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCurrentProduct({ ...currentProduct, [name]: value });
  };

  const handleCreate = async () => {
    await fetch('http://localhost:3000/api/product', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(currentProduct)
    });
    fetchProducts();
    setCurrentProduct({ name: '', shortDescription: '', brand: '', category: '', listPrice: '', cost: '' });
  };

  const handleUpdate = async () => {
    await fetch(`http://localhost:3000/api/product/${currentProduct.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(currentProduct)
    });
    fetchProducts();
    setCurrentProduct({ name: '', shortDescription: '', brand: '', category: '', listPrice: '', cost: '' });
  };

  const handleDelete = async (id) => {
    await fetch(`http://localhost:3000/api/product/${id}`, {
      method: 'DELETE'
    });
    fetchProducts();
  };

  const handleSearch = () => {
    const filteredProducts = products.filter(product =>
      product.name.toLowerCase().includes(search.toLowerCase())
    );
    setProducts(filteredProducts);
  };

  const handleEdit = (product) => {
    setCurrentProduct(product);
  };

  return (
    <div className="container">
      <h1>Product Management!</h1>
      <div className="form-container">
        <div className="form-group">
          <label>Name</label>
          <input type="text" name="name" value={currentProduct.name} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Short Description</label>
          <input type="text" name="shortDescription" value={currentProduct.shortDescription} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Brand</label>
          <input type="text" name="brand" value={currentProduct.brand} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Category</label>
          <input type="text" name="category" value={currentProduct.category} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>List Price</label>
          <input type="text" name="listPrice" value={currentProduct.listPrice} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Cost</label>
          <input type="text" name="cost" value={currentProduct.cost} onChange={handleInputChange} />
        </div>
        <div className="button-group">
          <button onClick={handleCreate}>Create</button>
          <button onClick={handleUpdate}>Update</button>
          <button onClick={() => handleDelete(currentProduct.id)}>Delete</button>
        </div>
      </div>
      <div className="search-container">
        <input type="text" value={search} onChange={(e) => setSearch(e.target.value)} />
        <button onClick={handleSearch}>Search</button>
      </div>
      <table>
        <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Short Description</th>
            <th>Brand</th>
            <th>Category</th>
            <th>List Price</th>
            <th>Cost</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map(product => (
            <tr key={product.id}>
              <td>{product.id}</td>
              <td>{product.name}</td>
              <td>{product.shortDescription}</td>
              <td>{product.brand}</td>
              <td>{product.category}</td>
              <td>{product.listPrice}</td>
              <td>{product.cost}</td>
              <td>
                <button onClick={() => handleEdit(product)}>Edit</button>
                <button onClick={() => handleDelete(product.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="pagination">
        <button>{'<<'}</button>
        <span>Page 1 of 1</span>
        <button>{'>>'}</button>
      </div>
    </div>
  );
}

export default App;
