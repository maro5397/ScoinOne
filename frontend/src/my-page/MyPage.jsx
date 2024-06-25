import { useState } from "react";
import { Box, CssBaseline, alpha } from "@mui/material";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import Profile from "./components/Profile";
import Menu from "./components/Menu";
import Navigation from "../commons/Navigation";
import getTheme from "../commons/getTheme";
import Footer from "../commons/Footer";

export default function MyPage() {
  const [mode, setMode] = useState("light");
  const theme = createTheme(getTheme(mode));

  const toggleColorMode = () => {
    setMode((prev) => (prev === "dark" ? "light" : "dark"));
  };

  const [nickname, setNickname] = useState("admin");

  const handleNicknameChange = (newNickname) => {
    setNickname(newNickname);
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Navigation mode={mode} toggleColorMode={toggleColorMode} />
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          minHeight: "100vh",
          bgcolor: "background.default",
          margin: "0 auto",
          mt: 15,
        }}
        maxWidth="lg"
      >
        <Profile
          profileImage=""
          nickname={nickname}
          email="admin@gmail.com"
          onNicknameChange={handleNicknameChange}
        />
        <Menu />
        <Footer />
      </Box>
    </ThemeProvider>
  );
}
