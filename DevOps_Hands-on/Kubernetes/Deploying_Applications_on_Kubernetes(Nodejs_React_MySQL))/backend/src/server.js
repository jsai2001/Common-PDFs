// const express = require('express');
// const mysql = require('mysql');
// const app = express();
// const port = 3000;

// app.get('/', (req, res) => {
//   res.send('Hello, World!');
// });

// app.listen(port, () => {
//   console.log(`Backend app listening at http://localhost:${port}`);
// });

const express = require('express');
const mysql = require('mysql');
const app = express();
const port = 3000;

// Create a connection to the database
const db = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER || 'admin',
  password: process.env.DB_PASSWORD || process.env.MYSQL_ROOT_PASSWORD,
  database: process.env.DB_NAME || 'mydatabase'
});

// Connect to the database
db.connect(err => {
  if (err) {
    console.error('Error connecting to the database:', err);
    return;
  }
  console.log('Connected to the database');
});

// Define a route to fetch items from the database
app.get('/items', (req, res) => {
  db.query('SELECT * FROM items', (err, results) => {
    if (err) {
      console.error('Error fetching items:', err);
      res.status(500).send('Error fetching items');
      return;
    }
    res.json(results);
  });
});

// Define a route to serve a simple message
app.get('/', (req, res) => {
  res.send('Hello, Jeevan!');
});

// Start the server
app.listen(port, () => {
  console.log(`Backend app listening at http://localhost:${port}`);
});