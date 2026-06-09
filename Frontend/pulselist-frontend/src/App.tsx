import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';

function App() {

  const queryClient = new QueryClient();

  return (
    <>
        <QueryClientProvider client={queryClient}>
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Home />}></Route>
            </Routes>
          </BrowserRouter>
        </QueryClientProvider>
    </>
  )
}

export default App;
