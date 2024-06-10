const mysql = require('mysql2/promise');

const config = {
    host: 'localhost',
    user: 'admin',
    password: 'admin123',
    database: 'products'
};

async function createDatabase() {
    try {
        const connection = await mysql.createConnection({ host: config.host, user: config.user, password: config.password });
        await connection.execute(`CREATE DATABASE IF NOT EXISTS ${config.database}`);
        console.log("Database created successfully...");
        await connection.end();
    } catch (error) {
        console.error('Error creating database:', error);
    }
}

async function getConnection() {
    return mysql.createConnection(config);
}

async function createTableIfNotExists() {
    try {
        const conn = await getConnection();
        const sql = `CREATE TABLE IF NOT EXISTS product (
            id INT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            shortDescription VARCHAR(255),
            brand VARCHAR(255),
            category VARCHAR(255),
            listPrice DOUBLE NOT NULL,
            cost DOUBLE
        );`;
        await conn.execute(sql);
        console.log("Table created successfully...");
        await conn.end();
    } catch (error) {
        console.error('Error creating table:', error);
    }
}

async function createProduct(product) {
    try {
        const conn = await getConnection();
        const sql = `INSERT INTO product (name, shortDescription, brand, category, listPrice, cost) VALUES (?, ?, ?, ?, ?, ?)`;
        await conn.execute(sql, [product.name, product.shortDescription, product.brand, product.category, product.listPrice, product.cost]);
        console.log("Product inserted successfully...");
        await conn.end();
    } catch (error) {
        console.error('Error inserting product:', error);
    }
}

async function updateProduct(product) {
    try {
        const conn = await getConnection();
        const sql = `UPDATE product SET name = ?, shortDescription = ?, brand = ?, category = ?, listPrice = ?, cost = ? WHERE id = ?`;
        await conn.execute(sql, [product.name, product.shortDescription, product.brand, product.category, product.listPrice, product.cost, product.id]);
        console.log("Product updated successfully...");
        await conn.end();
    } catch (error) {
        console.error('Error updating product:', error);
    }
}

async function deleteProduct(product) {
    try {
        const conn = await getConnection();
        const sql = `DELETE FROM product WHERE id = ?`;
        await conn.execute(sql, [product.id]);
        console.log("Product deleted successfully...");
        await conn.end();
    } catch (error) {
        console.error('Error deleting product:', error);
    }
}

async function getTotalProductsCount() {
    try {
        const conn = await getConnection();
        const [rows] = await conn.execute('SELECT COUNT(*) as count FROM product');
        await conn.end();
        return rows[0].count;
    } catch (error) {
        console.error('Error fetching total products count:', error);
        throw error;
    }
}

async function getProductsByRange(startIndex, endIndex) {
    try {
        const conn = await getConnection();
        const [rows] = await conn.execute('SELECT * FROM product LIMIT ?, ?', [startIndex, endIndex - startIndex]);
        await conn.end();
        return rows;
    } catch (error) {
        console.error('Error fetching products by range:', error);
        throw error;
    }
}

module.exports = {
    createDatabase,
    createTableIfNotExists,
    createProduct,
    updateProduct,
    deleteProduct,
    getTotalProductsCount,
    getProductsByRange
};
