const express = require('express');
const mysql = require('mysql');
const app = express();
const port = 3000;

app.get('/', (req, res) => {
  res.send('Hello, World!');
});

app.listen(port, () => {
  console.log(`Backend app listening at http://localhost:${port}`);
});

// const db = mysql.createConnection({
//   host: process.env.DB_HOST,
//   user: process.env.DB_USER,
//   password: process.env.DB_PASSWORD,
//   database: process.env.DB_NAME
// });

// db.connect(err => {
//   if (err) {
//     console.error('Error connecting to the database:', err);
//     return;
//   }
//   console.log('Connected to the database');
// });

// app.get('/data', (req, res) => {
//   db.query('SELECT * FROM items', (err, results) => {
//     if (err) {
//       res.status(500).send('Error fetching data');
//       return;
//     }
//     res.json(results);
//   });
// });

// app.listen(port, () => {
//   console.log(`Backend app listening at http://localhost:${port}`);
// });