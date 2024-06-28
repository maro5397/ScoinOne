import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import {
  Container,
  Typography,
  List,
  Pagination,
  Divider,
} from "@mui/material";
import BoardItem from "../commons/BoardItem";

export default function BoardList() {
  const { boardType } = useParams();
  const [postData, setPostData] = useState({
    totalPageCount: 1,
    post: [
      {
        id: 1,
        title: "React 게시판 개발 시작!",
        author: "John Doe",
        content:
          "React와 MUI를 사용하여 게시판을 만들고 있습니다. 많은 조언 부탁드립니다.",
        createdAt: "2024-06-24T10:30:00",
      },
      {
        id: 2,
        title: "MUI 컴포넌트 활용 팁",
        author: "Alice Kim",
        content: "MUI의 다양한 컴포넌트를 활용하여 멋진 UI를 만들어보세요!",
        createdAt: "2024-06-23T15:45:00",
      },
      {
        id: 3,
        title: "댓글 기능 추가 완료",
        author: "Bob Lee",
        content:
          "게시글에 댓글을 달 수 있는 기능을 구현했습니다. 많은 의견 남겨주세요.",
        createdAt: "2024-06-22T12:15:00",
      },
    ],
  });
  const [page, setPage] = useState(1);

  useEffect(() => {
    // 게시글 목록 API 호출
  }, [page]);

  const handlePageChange = (event, value) => {
    setPage(value);
  };

  return (
    <Container maxWidth="md">
      <Typography variant="h4" gutterBottom>
        {boardType === "announce" ? "공지사항" : "질의사항"}
      </Typography>
      <Divider />
      <List
        sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}
      >
        {postData.post.map((post) => (
          <BoardItem key={post.id} post={post} />
        ))}
      </List>
      <Pagination
        count={postData.totalPageCount} // 전체 페이지 수
        page={page}
        onChange={handlePageChange}
        sx={{ display: "flex", justifyContent: "center" }}
      />
    </Container>
  );
}
