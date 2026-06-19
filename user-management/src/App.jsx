import { BrowserRouter, Link, Route, Routes } from 'react-router-dom'
import UserList from './components/UserList'
import AddUser from './components/Adduser'


function App() {

  return (
      <Routes>
        <Route path="/users" element={<UserList />} />
        <Route path='/add-user' element={<AddUser />} />
      </Routes>
  
  )
}

export default App
