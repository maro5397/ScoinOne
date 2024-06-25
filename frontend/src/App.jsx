import { BrowserRouter, Routes, Route } from "react-router-dom";
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
        <Route path="/mypage" element={<MyPage />} />
        <Route
          path="/announce"
          element={<BoardPage path="announce" pageName="공지사항" />}
        />
        <Route
          path="/announce/:postId"
          element={<BoardDetail pathId={1} path="announce" />}
        />
        <Route
          path="/announce/:postId/edit"
          element={<BoardForm path="announce" />}
        />
        <Route
          path="/questions"
          element={<BoardPage path="questions" pageName="질의사항" />}
        />
        <Route
          path="/questions/:postId"
          element={<BoardDetail pathId={1} path="questions" />}
        />
        <Route
          path="/questions/:postId/edit"
          element={<BoardForm path="questions" />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
