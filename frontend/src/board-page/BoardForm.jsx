import { useState } from "react";
import { CssBaseline, TextField, Button, Box } from "@mui/material";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import Navigation from "../commons/Navigation";
import getTheme from "../commons/getTheme";
import Footer from "../commons/Footer";

export default function BoardForm({ initialPost = false, path }) {
  const [mode, setMode] = useState("light");
  const theme = createTheme(getTheme(mode));

  const toggleColorMode = () => {
    setMode((prev) => (prev === "dark" ? "light" : "dark"));
  };

  const [title, setTitle] = useState(initialPost?.title || "");
  const [content, setContent] = useState(initialPost?.content || "");

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(path);
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
        <Box component="form" onSubmit={handleSubmit}>
          <TextField
            id="outlined-basic"
            hiddenLabel
            aria-label="제목"
            placeholder="제목"
            autoComplete="off"
            fullWidth
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            margin="normal"
          />
          <TextField
            id="outlined-basic"
            hiddenLabel
            aria-label="내용"
            placeholder="내용"
            autoComplete="off"
            fullWidth
            value={content}
            onChange={(e) => setContent(e.target.value)}
            multiline
            rows={30}
            margin="normal"
          />
          <Button type="submit" variant="contained" color="primary">
            {initialPost ? "수정" : "작성"}
          </Button>
        </Box>
        <Footer></Footer>
      </Box>
    </ThemeProvider>
  );
}
