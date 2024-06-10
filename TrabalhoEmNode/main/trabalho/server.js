const express = require('express');
const cors = require('cors'); // Importa o pacote CORS
const databaseUtil = require('./database/databaseUtil'); // Ajuste o caminho conforme necessário
const productController = require('./productController'); // Adicione o caminho correto para o controller

const app = express();
const PORT = 3000;

// Habilita CORS para todas as origens (em um ambiente de produção, você pode querer restringir isso)
app.use(cors());

// Middleware para interpretar JSON no corpo das requisições
app.use(express.json());

app.get('/', (req, res) => {
  res.send('Welcome to the Product Management App!');
});

// Middleware para logar a URL de cada requisição para a API
app.use('/api', (req, res, next) => {
    console.log(`Request URL: ${req.originalUrl}`);
    next();
  }, productController);

// Inicializa o banco de dados e a tabela
async function initializeDatabase() {
  try {
    await databaseUtil.createDatabase();
    await databaseUtil.createTableIfNotExists();
  } catch (error) {
    console.error('Failed to initialize database:', error);
  }
}

initializeDatabase();

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
