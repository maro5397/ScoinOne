import { BrowserRouter, Navigate, Routes, Route } from "react-router-dom";
import { Provider } from "react-redux";
import { PersistGate } from "redux-persist/integration/react";
import { store, persistor } from "./store/store.jsx";
import LandingPage from "./landing-page/LandingPage.jsx";
import LoginPage from "./login-page/LoginPage.jsx";
import RegisterPage from "./register-page/RegisterPage.jsx";
import AuctionPage from "./auction-page/AuctionPage.jsx";
import MyPage from "./my-page/MyPage.jsx";
import BoardPage from "./board-page/BoardPage.jsx";
import BoardDetail from "./board-page/BoardDetail.jsx";
import BoardForm from "./board-page/BoardForm.jsx";
import ScrollToTop from "./commons/ScrollToTop.jsx";

function App() {
  return (
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <BrowserRouter>
          <ScrollToTop />
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
            <Route
              path="/board/:boardType/edit/:postId"
              element={<BoardForm />}
            />
            <Route path="/board/:boardType/:postId" element={<BoardDetail />} />
          </Routes>
        </BrowserRouter>
      </PersistGate>
    </Provider>
  );
}

export default App;
