import { useState } from "react";
import { CssBaseline, Box, Divider } from "@mui/material";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import Navigation from "./Navigation";
import getTheme from "./getTheme";
import Footer from "./Footer";

export default function BaseLayout({ children, marginTop }) {
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
          mt: { marginTop },
        }}
        maxWidth="lg"
      >
        {children}
        <Divider sx={{ mt: 10 }} />
        <Footer />
      </Box>
    </ThemeProvider>
  );
}
