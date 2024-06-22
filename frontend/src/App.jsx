import { BrowserRouter, Routes, Route } from "react-router-dom";
import LandingPage from "./landing-page/LandingPage.jsx";
import LoginPage from "./login-page/LoginPage.jsx";
import RegisterPage from "./register-page/RegisterPage.jsx";
import AuctionPage from "./auction-page/AuctionPage.jsx";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/auction" element={<AuctionPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
