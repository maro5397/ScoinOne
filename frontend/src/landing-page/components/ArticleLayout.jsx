import React from "react";
import { Grid, Paper } from "@mui/material";

export default function ArticleLayout({ children }) {
  return (
    <Grid container spacing={2}>
      {React.Children.map(children, (child, index) => (
        <Grid item xs={12} sm={6} key={index}>
          <Paper elevation={3}>{child}</Paper>
        </Grid>
      ))}
    </Grid>
  );
}
