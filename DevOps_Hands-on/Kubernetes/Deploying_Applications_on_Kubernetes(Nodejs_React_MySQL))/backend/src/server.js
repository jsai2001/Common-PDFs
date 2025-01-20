const express = require('express');
const mysql = require('mysql');
const cors = require('cors');
const app = express();
const port = 3000;

// Configure CORS to allow requests from your specific origin
const corsOptions = {
  origin: 'http://a0adad441938045918257721b7310b1b-2060406325.ap-south-1.elb.amazonaws.com',
  optionsSuccessStatus: 200 // some legacy browsers (IE11, various SmartTVs) choke on 204
}

// Middleware to log requests for debugging
app.use((req, res, next) => {
  console.log('Middleware hit:', req.method, req.path);
  next();
});

// Apply CORS middleware
app.use(cors(corsOptions));

// Handle preflight requests for all routes
app.options('*', cors(corsOptions));

const db = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER || 'admin',
  password: process.env.DB_PASSWORD || process.env.MYSQL_ROOT_PASSWORD,
  database: process.env.DB_NAME || 'mydatabase'
});

db.connect(err => {
  if (err) {
    console.error('Error connecting to the database:', err);
    return;
  }
  console.log('Connected to the database');
});

app.get('/items', cors(corsOptions), (req, res) => {
  // Manually setting CORS headers as a fallback
  res.header('Access-Control-Allow-Origin', corsOptions.origin);
  res.header('Access-Control-Allow-Methods', 'GET,HEAD,OPTIONS,POST,PUT');
  res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
  
  db.query('SELECT * FROM items', (err, results) => {
    if (err) {
      console.error('Error fetching items:', err);
      res.status(500).send('Error fetching items');
      return;
    }
    res.json(results);
  });
});

app.get('/', (req, res) => {
  res.send('Hello, World!');
});

// Handle errors globally
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).send('Something broke!');
});

app.listen(port, () => {
  console.log(`Backend app listening at http://localhost:${port}`);
});