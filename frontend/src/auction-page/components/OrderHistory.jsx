import React, { useState } from "react";
import {
  ToggleButtonGroup,
  ToggleButton,
  Box,
  Typography,
  List,
  ListItem,
  ListItemText,
} from "@mui/material";

export default function OrderHistory() {
  const [activeTab, setActiveTab] = useState("미체결 주문");
  const [orders, setOrders] = useState([
    {
      id: 1,
      type: "매도",
      date: "24-05-01 18:18:42",
      price: 171900,
      quantity: 0.13852553,
      amount: 23812,
    },
    {
      id: 2,
      type: "매수",
      date: "24-04-30 19:09:42",
      price: 186700,
      quantity: 0.13852553,
      amount: 25863,
    },
  ]);

  const handleTabChange = (event, newTab) => {
    setActiveTab(newTab);
  };

  return (
    <Box>
      <ToggleButtonGroup
        value={activeTab}
        exclusive
        onChange={handleTabChange}
        aria-label="order tabs"
        fullWidth
      >
        <ToggleButton value="미체결 주문">미체결 주문</ToggleButton>
        <ToggleButton value="체결 내역">체결 내역</ToggleButton>
      </ToggleButtonGroup>
      <List>
        {orders.map((order, index) => (
          <ListItem key={order.id} draggable sx={{ padding: "8px 16px" }}>
            <ListItemText
              primary={
                <>
                  <Typography
                    variant="body1"
                    color={order.type === "매도" ? "error" : "primary"}
                  >
                    SOL 지정가 {order.type} {order.date}
                  </Typography>
                  <Typography variant="body2" color="textSecondary">
                    체결가격: {order.price}
                  </Typography>
                  <Typography variant="body2" color="textSecondary">
                    체결수량: {order.quantity}
                  </Typography>
                  <Typography variant="body2" color="textSecondary">
                    체결금액: {order.amount}
                  </Typography>
                </>
              }
              sx={{
                "& .MuiTypography-root": {
                  marginBottom: "4px",
                },
              }}
            />
          </ListItem>
        ))}
      </List>
    </Box>
  );
}
