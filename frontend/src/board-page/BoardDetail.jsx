import { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import { Box, Button, Container, Divider, Typography } from "@mui/material";
import CommentList from "../commons/CommentList";
import CommentForm from "../commons/CommentForm";
import BaseLayout from "../commons/BaseLayout";

export default function BoardDetail() {
  const { boardType, postId } = useParams();

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
    // 게시글 상세 정보 및 댓글 목록 API 호출
  }, [postId]);

  return (
    <BaseLayout marginTop={100}>
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
            <Divider sx={{ mt: 5, mb: 10 }} />
            <Typography variant="body1" paragraph>
              {posts.content}
            </Typography>
            <Divider sx={{ mt: 10, mb: 5 }} />
            <Box display="flex" justifyContent="flex-end">
              <Button
                component={Link}
                to={`/board/${boardType}/edit/${postId}`}
                variant="outlined"
              >
                수정
              </Button>
            </Box>
            <CommentList comments={comments} />
            <CommentForm setComments={setComments} />
          </>
        )}
      </Container>
    </BaseLayout>
  );
}
