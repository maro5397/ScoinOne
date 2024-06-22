import * as React from "react";

import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";
import Divider from "@mui/material/Divider";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import Introduction from "./components/Introduction";
import Slider from "./components/Slider";
import Announce from "./components/Announce";
import FAQ from "./components/FAQ";
import ArticleLayout from "./components/ArticleLayout";
import getTheme from "../commons/getTheme";
import Footer from "../commons/Footer";
import Navigation from "../commons/Navigation";

export default function LandingPage() {
  const [mode, setMode] = React.useState("light");
  const theme = createTheme(getTheme(mode));

  const toggleColorMode = () => {
    setMode((prev) => (prev === "dark" ? "light" : "dark"));
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Navigation mode={mode} toggleColorMode={toggleColorMode} />
      <Introduction />
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          minHeight: "100vh",
          bgcolor: "background.default",
          margin: "0 auto",
        }}
        maxWidth="lg"
      >
        <Divider />
        <Slider />
        <Divider />
        <ArticleLayout>
          <Announce />
          <FAQ />
        </ArticleLayout>
        <Divider />
        <Footer />
      </Box>
    </ThemeProvider>
  );
}
