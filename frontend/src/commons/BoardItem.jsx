import { Link } from "react-router-dom";
import { ListItem, ListItemText, Typography } from "@mui/material";

export default function BoardItem({ post, path }) {
  return (
    <ListItem component={Link} to={`/${path}/${post.id}`}>
      <ListItemText
        primary={
          <Typography variant="h6" color="primary">
            {post.title}
          </Typography>
        }
        secondary={
          <>
            <Typography variant="body2" color="textSecondary">
              {post.author}
            </Typography>
            <Typography variant="body2" color="textSecondary">
              {new Date(post.createdAt).toLocaleDateString()}
            </Typography>
          </>
        }
      />
    </ListItem>
  );
}
