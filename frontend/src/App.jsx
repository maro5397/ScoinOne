import { BrowserRouter, Navigate, Routes, Route } from "react-router-dom";
import LandingPage from "./landing-page/LandingPage.jsx";
import LoginPage from "./login-page/LoginPage.jsx";
import RegisterPage from "./register-page/RegisterPage.jsx";
import AuctionPage from "./auction-page/AuctionPage.jsx";
import MyPage from "./my-page/MyPage.jsx";
import BoardPage from "./board-page/BoardPage.jsx";
import BoardDetail from "./board-page/BoardDetail.jsx";
import BoardForm from "./board-page/BoardForm.jsx";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/auction" element={<AuctionPage />} />
        <Route
          path="/mypage"
          element={<Navigate to="/mypage/alarm" replace />}
        />
        <Route path="/mypage/:menu" element={<MyPage />} />
        <Route path="/board/:boardType" element={<BoardPage />} />
        <Route path="/board/:boardType/post" element={<BoardForm />} />
        <Route path="/board/:boardType/edit/:postId" element={<BoardForm />} />
        <Route path="/board/:boardType/:postId" element={<BoardDetail />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
