import React, { useEffect, useState } from 'react';
import './App.css';

function App() {
  const [data, setData] = useState([]);

  useEffect(() => {
    fetch('http://backend-service:3000/items')  // Ensure this URL is correct
      .then(response => response.json())
      .then(data => setData(data))
      .catch(error => console.error('Error fetching data:', error));
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>Welcome to the Static Website</h1>
        <p>This website is served by an Nginx server running in a Kubernetes cluster.</p>
        <h2>Data from the Database:</h2>
        <ul>
          {data.map((item, index) => (
            <li key={index}>{item.name}</li>
          ))}
        </ul>
      </header>
    </div>
  );
}

export default App;