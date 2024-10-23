import { useState } from 'react'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return <div className="font-bold">{count}</div>
}

export default App
