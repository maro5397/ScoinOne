import { useState } from "react";
import { CssBaseline, Box } from "@mui/material";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import getTheme from "../commons/getTheme";
import Navigation from "../commons/Navigation";
import Footer from "../commons/Footer";
import BoardList from "../commons/BoardList";

export default function BoardPage({ path, pageName }) {
  const [mode, setMode] = useState("light");
  const theme = createTheme(getTheme(mode));

  const toggleColorMode = () => {
    setMode((prev) => (prev === "dark" ? "light" : "dark"));
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
        <BoardList path={path} pageName={pageName}></BoardList>
        <Footer />
      </Box>
    </ThemeProvider>
  );
}
