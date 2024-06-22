import React, { useState } from "react";
import {
  Table,
  TableHead,
  TableBody,
  TableCell,
  TableContainer,
  TableRow,
  Box,
  Typography,
  LinearProgress,
} from "@mui/material";
import OrderBookLayout from "./OrderBookLayout";

export default function OrderBook() {
  function title() {
    return (
      <>
        <Typography fontWeight="bold" variant="h6" align="center">
          호가
        </Typography>
      </>
    );
  }

  function askPrice(orderBookData) {
    return (
      <TableContainer component={Box} sx={{ height: "100%", width: "100%" }}>
        <Table size="small" aria-label="order book">
          <TableBody>
            {orderBookData.map((order, index) => (
              <TableRow key={index}>
                <TableCell align="right" color="primary" sx={{ width: "70%" }}>
                  {order.volume}
                </TableCell>
                <TableCell align="center" color="error" sx={{ width: "30%" }}>
                  {order.price}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    );
  }

  function executionLog(transactions) {
    return (
      <Box sx={{ height: "100%", width: "100%" }}>
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            height: "5%",
          }}
        >
          <Typography variant="body1">체결강도</Typography>
          <Typography variant="body1">71.41%</Typography>
        </Box>

        <Box
          sx={{
            height: "5%",
            width: "90%",
            margin: "0 auto",
          }}
        >
          <LinearProgress
            variant="determinate"
            value={50}
            sx={{
              backgroundColor: "#d82c2c",
              "& .MuiLinearProgress-bar": {
                backgroundColor: "#1976d2",
              },
              height: 8,
              borderRadius: 5,
            }}
          />
        </Box>

        <TableContainer component={Box} sx={{ height: "90%", width: "100%" }}>
          <Table size="small" aria-label="transaction table">
            <TableHead>
              <TableRow>
                <TableCell align="left">시간</TableCell>
                <TableCell align="center">체결가</TableCell>
                <TableCell align="right">체결량</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {transactions.map((transaction, index) => (
                <TableRow key={index}>
                  <TableCell align="left">{transaction.time}</TableCell>
                  <TableCell align="center">
                    {transaction.price.toLocaleString()}
                  </TableCell>
                  <TableCell align="right">{transaction.amount}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>
    );
  }

  function differenceBefore() {
    return (
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          height: "100%",
          width: "100%",
          justifyContent: "flex-end",
        }}
      >
        <Box sx={{ display: "flex", justifyContent: "space-between" }}>
          <Typography variant="body2">전일종가</Typography>
          <Typography variant="body2">188,800</Typography>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "space-between" }}>
          <Typography variant="body2">당일 고가</Typography>
          <Typography variant="body2" color="#f04040">
            190,400 (+0.84%)
          </Typography>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "space-between" }}>
          <Typography variant="body2">당일 저가</Typography>
          <Typography variant="body2" color="#488af0">
            186,400 (-1.27%)
          </Typography>
        </Box>
      </Box>
    );
  }

  const [transactions, setTransactions] = useState([
    { time: "10:20:19", price: 90427000, amount: 0.0001 },
    { time: "10:19:34", price: 90391000, amount: 0.0037 },
    { time: "10:19:34", price: 90392000, amount: 0.0001 },
    // ... (나머지 체결 데이터)
  ]);

  const orderBookData = [
    { volume: 1.03, price: 199000 },
    { volume: 13.2469, price: 198000 },
    { volume: 25.9169, price: 197000 },
    { volume: 9.0532, price: 196000 },
    { volume: 270.0, price: 195000 },
    { volume: 486.2087, price: 194000 },
    { volume: 5.2798, price: 193000 },
    { volume: 25.9169, price: 192000 },
  ];

  return (
    <OrderBookLayout
      title={title()}
      askPrice={askPrice(orderBookData)}
      executionLog={executionLog(transactions)}
      differenceBefore={differenceBefore()}
    ></OrderBookLayout>
  );
}
