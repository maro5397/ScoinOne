import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { CssBaseline, Box, Button, Container, Typography } from "@mui/material";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import Navigation from "../commons/Navigation";
import CommentList from "../commons/CommentList";
import CommentForm from "../commons/CommentForm";
import getTheme from "../commons/getTheme";
import Footer from "../commons/Footer";

export default function BoardDetail({ postId, path }) {
  const [mode, setMode] = useState("light");
  const theme = createTheme(getTheme(mode));

  const toggleColorMode = () => {
    setMode((prev) => (prev === "dark" ? "light" : "dark"));
  };

  const [posts, setPosts] = useState({
    id: 1,
    title: "React 게시판 개발 시작!",
    author: "John Doe",
    content:
      "React와 MUI를 사용하여 게시판을 만들고 있습니다. 많은 조언 부탁드립니다.",
    createdAt: "2024-06-24T10:30:00",
  });

  const [comments, setComments] = useState([
    {
      id: 1,
      author: "Alice Kim",
      content: "정말 유용한 정보 감사합니다!",
      createdAt: "2024-06-24T12:45:00",
    },
    {
      id: 2,
      author: "Bob Lee",
      content: "저도 React 게시판 개발 중인데 많은 도움이 되었어요.",
      createdAt: "2024-06-24T15:30:00",
    },
    {
      id: 3,
      author: "John Doe",
      content: "MUI 컴포넌트 사용법을 자세히 알려주세요!",
      createdAt: "2024-06-23T18:20:00",
    },
    {
      id: 4,
      author: "Jane Smith",
      content: "댓글 기능 덕분에 게시판이 더욱 활성화될 것 같아요.",
      createdAt: "2024-06-22T14:05:00",
    },
  ]);

  useEffect(() => {
    console.log(path);
    // 게시글 상세 정보 및 댓글 목록 API 호출
  }, [postId]);

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
        <Container maxWidth="md">
          {posts && (
            <>
              <Typography variant="h5" component="h2" gutterBottom>
                {posts.title}
              </Typography>
              <Typography variant="body2" paragraph>
                {posts.author}
              </Typography>
              <Typography variant="body2" paragraph>
                {posts.createdAt}
              </Typography>
              <Typography variant="body1" paragraph>
                {posts.content}
              </Typography>
              <Box display="flex" justifyContent="flex-end">
                <Button
                  component={Link}
                  to={`/${path}/${postId}/edit`}
                  variant="outlined"
                >
                  수정
                </Button>
              </Box>
              <CommentList comments={comments} />
              <CommentForm postId={postId} setComments={setComments} />
            </>
          )}
        </Container>
        <Footer></Footer>
      </Box>
    </ThemeProvider>
  );
}
