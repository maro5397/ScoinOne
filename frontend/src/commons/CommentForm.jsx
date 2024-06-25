import { useState } from "react";
import { Box, TextField, Button } from "@mui/material";

export default function CommentForm({ postId, setComments }) {
  const [content, setContent] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
    const newComment = await createComment(postId, content);
    setComments((prevComments) => [...prevComments, newComment]);
    setContent(""); // 댓글 작성 후 입력 필드 초기화
  };

  return (
    <Box component="form" onSubmit={handleSubmit} mt={2}>
      <TextField
        fullWidth
        label="댓글 작성"
        value={content}
        onChange={(e) => setContent(e.target.value)}
        multiline
        rows={4}
      />
      <Box display="flex" justifyContent="flex-end">
        <Button type="submit" variant="contained" color="primary">
          등록
        </Button>
      </Box>
    </Box>
  );
}
