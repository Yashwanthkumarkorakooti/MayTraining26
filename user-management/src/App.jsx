import { BrowserRouter, Link, Route, Routes } from 'react-router-dom'
import UserList from './components/UserList'
import AddUser from './components/Adduser'
import Pagination from './components/Pagination'


function App() {

  return (
      <Routes>
        <Route path="/users" element={<UserList />} />
        <Route path='/add-user' element={<AddUser />} />
        <Route path='/task' element={<Pagination />} />
      </Routes>
  
  )
}

export default App
