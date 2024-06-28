import { Grid, Box, Divider } from "@mui/material";

export default function OrderBookLayout({
  title,
  askPrice,
  executionLog,
  differenceBefore,
}) {
  return (
    <Grid container spacing={1} sx={{ height: "100%" }}>
      <Grid item xs={12} sx={{ height: "5%" }}>
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "100%",
          }}
        >
          {title}
        </Box>
      </Grid>
      <Grid item xs={12} sx={{ height: "95%" }}>
        <Grid container spacing={1} sx={{ height: "100%" }}>
          <Grid item xs={6}>
            <Box sx={{ height: "100%" }}>{executionLog}</Box>
          </Grid>
          <Grid item xs={6}>
            <Box sx={{ height: "80%" }}>{askPrice}</Box>
            <Divider />
            <Box sx={{ height: "20%" }}>{differenceBefore}</Box>
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  );
}
