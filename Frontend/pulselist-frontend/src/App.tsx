import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginForm from './pages/LoginForm';
import SignUpForm from './pages/SignUpForm';
import AccountPage from './pages/AccountPage';
import Landing from './pages/Landing';
import Navbar from './components/Navbar';

function App() {

  const queryClient = new QueryClient();

  return (
    <>
        <QueryClientProvider client={queryClient}>
          <BrowserRouter>
          <Navbar/>
            <Routes>
              <Route path='/' element={<Landing/>}></Route>
              <Route path="/accountPage" element={<AccountPage />}></Route>
              <Route path="/login" element={<LoginForm />}></Route>
              <Route path="/signup" element={<SignUpForm />}></Route>
            </Routes>
          </BrowserRouter>
        </QueryClientProvider>
    </>
  )
}

export default App;
