import React, { useState } from "react";

import CssBaseline from "@mui/material/CssBaseline";
import Divider from "@mui/material/Divider";
import { Box } from "@mui/material";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import AuctionLayout from "./components/AuctionLayout";
import Chart from "./components/Chart";
import VirtualAssetList from "./components/VirtualAssetList";
import OrderForm from "./components/OrderForm";
import OrderBook from "./components/OrderBook";
import OrderHistory from "./components/OrderHistory";
import Navigation from "../commons/Navigation";
import getTheme from "../commons/getTheme";
import Footer from "../commons/Footer";

export default function AuctionPage() {
  const [mode, setMode] = useState("light");
  const theme = createTheme(getTheme(mode));

  const toggleColorMode = () => {
    setMode((prev) => (prev === "dark" ? "light" : "dark"));
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Navigation mode={mode} toggleColorMode={toggleColorMode} />
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          minHeight: "100vh",
          bgcolor: "background.default",
          margin: "0 auto",
        }}
        maxWidth="lg"
      >
        <AuctionLayout
          Chart={<Chart mode={mode}></Chart>}
          VirtualAssetList={<VirtualAssetList></VirtualAssetList>}
          OrderBook={<OrderBook></OrderBook>}
          OrderForm={<OrderForm></OrderForm>}
          OrderHistory={<OrderHistory></OrderHistory>}
        ></AuctionLayout>
        <Divider />
        <Footer></Footer>
      </Box>
    </ThemeProvider>
  );
}
