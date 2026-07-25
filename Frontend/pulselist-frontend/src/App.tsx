import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginForm from './pages/LoginForm';
import SignUpForm from './pages/SignUpForm';
import AccountPage from './pages/AccountPage';
import Landing from './pages/Landing';
import Navbar from './components/Navbar';
import ProtectedRoutes from './ProtectedRoutes';
import TrendingPage from './pages/TrendingPage';
import Home from "./pages/Home";
import SearchPage from './pages/SearchPage';

function App() {

  const queryClient = new QueryClient();

  return (
    <>
      <QueryClientProvider client={queryClient}>
        <BrowserRouter>
          <Navbar />

          <Routes>
            <Route element={<ProtectedRoutes />}>
              <Route path="/account" element={<AccountPage />}></Route>
<Route path="/accountPage" element={<AccountPage />}></Route>
              <Route path='/trending' element={<TrendingPage />}></Route>
              <Route path='/search' element={<SearchPage/>}></Route>
            </Route>

            <Route path='/' element={<Landing />}></Route>
            <Route path='/home' element={<Home/>}></Route>
            <Route path="/login" element={<LoginForm />}></Route>
            <Route path="/signup" element={<SignUpForm />}></Route>
            
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
    </>
  )
}

export default App;
