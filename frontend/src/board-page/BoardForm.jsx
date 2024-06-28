import { useState } from "react";
import { useParams } from "react-router-dom";
import { TextField, Button, Box } from "@mui/material";
import BaseLayout from "../commons/BaseLayout";

export default function BoardForm() {
  const { boardType, postId } = useParams();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();
  };

  return (
    <BaseLayout marginTop={100}>
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
        <Box display="flex" justifyContent="flex-end">
          <Button type="submit" variant="contained" color="primary">
            {postId ? "수정" : "작성"}
          </Button>
        </Box>
      </Box>
    </BaseLayout>
  );
}
