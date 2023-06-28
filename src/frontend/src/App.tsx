import React, {ChangeEvent, FormEvent, useEffect, useState} from 'react';
import logo from './logo.svg';
import './App.css';

function App() {
  const [value, setValue] = useState("");
  const [enable, setEnable] = useState(true)
  const [lastHelloId, setLastHelloId] = useState("")

  const onClick = () => {
    setEnable(false)
    fetch("/api/hello", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        data: value
      })
    })
      .then(response => response.json())
      .then(data => {
        setLastHelloId(data.helloId)
        setEnable(true)
      })
  }

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setValue(e.target.value)
  }

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          <input type="text" value={value}  onChange={onChange} />
          <button onClick={onClick} disabled={!enable} >
            post
          </button>
        </p>
        <p>
          Last Hello Id: {lastHelloId}
        </p>
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
