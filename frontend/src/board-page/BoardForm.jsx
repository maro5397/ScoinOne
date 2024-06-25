import { useState } from "react";
import { TextField, Button, Box, Divider } from "@mui/material";
import BaseLayout from "../commons/BaseLayout";

export default function BoardForm({ initialPost = false, path }) {
  const [title, setTitle] = useState(initialPost?.title || "");
  const [content, setContent] = useState(initialPost?.content || "");

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(path);
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
            {initialPost ? "수정" : "작성"}
          </Button>
        </Box>
      </Box>
    </BaseLayout>
  );
}
