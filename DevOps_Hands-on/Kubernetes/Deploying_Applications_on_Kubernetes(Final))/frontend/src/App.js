import React, { useEffect, useState } from 'react';
import './App.css';

function App() {
  const [data, setData] = useState([]);

  useEffect(() => {
    console.log('Fetching data from backend...');
    fetch('http://aaf69902646cd4580929296cb27095f9-2099308469.ap-south-1.elb.amazonaws.com:3000/items')  // Ensure this URL is correct
      .then(response => {
        console.log('Received response:', response);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('Fetched data:', data);  // Log fetched data
        setData(data);
      })
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
