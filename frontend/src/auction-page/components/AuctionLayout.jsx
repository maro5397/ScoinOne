import React from "react";
import { Grid, Box } from "@mui/material";

export default function AuctionLayout({
  Chart,
  VirtualAssetList,
  OrderBook,
  OrderForm,
  OrderHistory,
}) {
  return (
    <Grid
      container
      spacing={1}
      component="main"
      sx={{ height: "100vh", mt: 10, flex: 1 }}
    >
      <Grid item xs={12} sm={8}>
        <Box
          sx={(theme) => ({
            p: 2,
            height: "100%",
            borderRadius: "8px",
            boxShadow:
              theme.palette.mode === "light"
                ? `0 0 1px rgba(85, 166, 246, 0.1), 1px 1.5px 2px -1px rgba(85, 166, 246, 0.15), 4px 4px 12px -2.5px rgba(85, 166, 246, 0.15)`
                : "0 0 1px rgba(2, 31, 59, 0.7), 1px 1.5px 2px -1px rgba(2, 31, 59, 0.65), 4px 4px 12px -2.5px rgba(2, 31, 59, 0.65)",
          })}
        >
          {Chart}
        </Box>
      </Grid>
      <Grid item xs={12} sm={4}>
        <Box
          sx={(theme) => ({
            p: 2,
            height: "100%",
            borderRadius: "8px",
            boxShadow:
              theme.palette.mode === "light"
                ? `0 0 1px rgba(85, 166, 246, 0.1), 1px 1.5px 2px -1px rgba(85, 166, 246, 0.15), 4px 4px 12px -2.5px rgba(85, 166, 246, 0.15)`
                : "0 0 1px rgba(2, 31, 59, 0.7), 1px 1.5px 2px -1px rgba(2, 31, 59, 0.65), 4px 4px 12px -2.5px rgba(2, 31, 59, 0.65)",
          })}
        >
          {VirtualAssetList}
        </Box>
      </Grid>
      <Grid item xs={6} sm={8}>
        <Box
          sx={(theme) => ({
            p: 2,
            height: "100%",
            borderRadius: "8px",
            boxShadow:
              theme.palette.mode === "light"
                ? `0 0 1px rgba(85, 166, 246, 0.1), 1px 1.5px 2px -1px rgba(85, 166, 246, 0.15), 4px 4px 12px -2.5px rgba(85, 166, 246, 0.15)`
                : "0 0 1px rgba(2, 31, 59, 0.7), 1px 1.5px 2px -1px rgba(2, 31, 59, 0.65), 4px 4px 12px -2.5px rgba(2, 31, 59, 0.65)",
          })}
        >
          {OrderBook}
        </Box>
      </Grid>
      <Grid item xs={12} sm={4}>
        <Grid container direction="column" spacing={1}>
          <Grid item>
            <Box
              sx={(theme) => ({
                p: 2,
                height: "100%",
                borderRadius: "8px",
                boxShadow:
                  theme.palette.mode === "light"
                    ? `0 0 1px rgba(85, 166, 246, 0.1), 1px 1.5px 2px -1px rgba(85, 166, 246, 0.15), 4px 4px 12px -2.5px rgba(85, 166, 246, 0.15)`
                    : "0 0 1px rgba(2, 31, 59, 0.7), 1px 1.5px 2px -1px rgba(2, 31, 59, 0.65), 4px 4px 12px -2.5px rgba(2, 31, 59, 0.65)",
              })}
            >
              {OrderForm}
            </Box>
          </Grid>
          <Grid item>
            <Box
              sx={(theme) => ({
                p: 2,
                height: "100%",
                borderRadius: "8px",
                boxShadow:
                  theme.palette.mode === "light"
                    ? `0 0 1px rgba(85, 166, 246, 0.1), 1px 1.5px 2px -1px rgba(85, 166, 246, 0.15), 4px 4px 12px -2.5px rgba(85, 166, 246, 0.15)`
                    : "0 0 1px rgba(2, 31, 59, 0.7), 1px 1.5px 2px -1px rgba(2, 31, 59, 0.65), 4px 4px 12px -2.5px rgba(2, 31, 59, 0.65)",
              })}
            >
              {OrderHistory}
            </Box>
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  );
}
