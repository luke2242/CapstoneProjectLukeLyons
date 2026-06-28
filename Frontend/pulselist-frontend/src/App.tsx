import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginForm from './pages/LoginForm';

function App() {

  const queryClient = new QueryClient();

  return (
    <>
        <QueryClientProvider client={queryClient}>
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<LoginForm />}></Route>
            </Routes>
          </BrowserRouter>
        </QueryClientProvider>
    </>
  )
}

export default App;
