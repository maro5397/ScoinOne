import React from "react";
import { Grid, Typography, Box, Container } from "@mui/material";
import { Gauge } from "@mui/x-charts/Gauge";
import VirtualAssetCard from "./VirtualAssetCard";

export default function VirtualAssetInfoContent() {
  const virtualAssetData = [
    {
      name: "SOL",
      percentage: 88,
      price: 48988553,
      quantity: 105,
    },
    {
      name: "MATIC",
      percentage: 4,
      price: 75580135,
      quantity: 508,
    },
    {
      name: "LINK",
      percentage: 73,
      price: 17178501,
      quantity: 851,
    },
    {
      name: "ETH",
      percentage: 28,
      price: 31993146,
      quantity: 396,
    },
    {
      name: "BTC",
      percentage: 45,
      price: 52398577,
      quantity: 789,
    },
    {
      name: "ARP",
      percentage: 41,
      price: 22875539,
      quantity: 124,
    },
    {
      name: "DOT",
      percentage: 55,
      price: 76885608,
      quantity: 976,
    },
    {
      name: "SPO",
      percentage: 67,
      price: 39435649,
      quantity: 137,
    },
    {
      name: "XRP",
      percentage: 25,
      price: 5481214,
      quantity: 614,
    },
    {
      name: "CIN",
      percentage: 64,
      price: 80980210,
      quantity: 889,
    },
  ];

  function spCardAndBalance(sp, balance, percent) {
    return (
      <Grid container spacing={0} justifyContent="center" sx={{ mt: 2, mb: 3 }}>
        <Grid item sx={{ width: "200px" }}>
          <Gauge
            value={percent}
            aria-label="SP 비율"
            height={150}
            text={({ value }) => `${value} %`}
          />
        </Grid>
        <Grid item>
          <Box sx={{ height: "100%" }}>
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "flex-end",
                height: "100%",
              }}
            >
              <Typography variant="h5" color="text.secondary" align="left">
                총 자산 가치: {balance}SP
              </Typography>
              <Typography variant="h4" color="text.secondary" align="left">
                보유 SP: {sp}SP
              </Typography>
            </Box>
          </Box>
        </Grid>
      </Grid>
    );
  }

  return (
    <Container
      id="execution-status"
      sx={(theme) => ({
        pt: 3,
        pb: 3,
        position: "relative",
        display: "flex",
        flexDirection: "column",
        gap: { xs: 2, sm: 2 },
        boxShadow:
          theme.palette.mode === "light"
            ? `0 0 1px rgba(85, 166, 246, 0.1), 1px 1.5px 2px -1px rgba(85, 166, 246, 0.15), 4px 4px 12px -2.5px rgba(85, 166, 246, 0.15)`
            : "0 0 1px rgba(2, 31, 59, 0.7), 1px 1.5px 2px -1px rgba(2, 31, 59, 0.65), 4px 4px 12px -2.5px rgba(2, 31, 59, 0.65)",
      })}
    >
      {spCardAndBalance(50000, 500000000, 70)}
      <Grid container spacing={2} columns={{ xs: 1, sm: 2, md: 3 }}>
        {virtualAssetData.map((virtualAsset, index) => (
          <Grid item xs={1} sm={1} md={1} key={index}>
            <VirtualAssetCard
              name={virtualAsset.name}
              percentage={virtualAsset.percentage}
              price={virtualAsset.price}
              quantity={virtualAsset.quantity}
            ></VirtualAssetCard>
          </Grid>
        ))}
      </Grid>
    </Container>
  );
}
