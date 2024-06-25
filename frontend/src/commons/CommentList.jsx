import { Box, List, Typography } from "@mui/material";
import CommentItem from "./CommentItem";

export default function CommentList({ comments }) {
  return (
    <Box sx={{ mt: 10 }}>
      <Typography variant="h6" gutterBottom>
        댓글 ({comments.length})
      </Typography>
      <List>
        {comments.map((comment) => (
          <CommentItem key={comment.id} comment={comment} />
        ))}
      </List>
    </Box>
  );
}
