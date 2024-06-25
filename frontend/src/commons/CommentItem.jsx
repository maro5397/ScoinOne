import { ListItem, ListItemText, Typography } from "@mui/material";

export default function CommentItem({ comment }) {
  return (
    <ListItem>
      <ListItemText
        primary={
          <>
            <Typography variant="subtitle1">{comment.author}</Typography>
            <Typography variant="body1">{comment.content}</Typography>
          </>
        }
        secondary={
          <Typography variant="body2" color="textSecondary">
            {new Date(comment.createdAt).toLocaleString()}
          </Typography>
        }
      />
    </ListItem>
  );
}
