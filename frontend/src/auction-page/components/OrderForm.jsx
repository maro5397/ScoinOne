import { useState } from "react";
import {
  Box,
  ToggleButton,
  ToggleButtonGroup,
  TextField,
  Button,
  Typography,
} from "@mui/material";

function marketPrice(buySell) {
  const placeHolder = buySell === "buy" ? "총액" : "수량";

  return (
    <>
      <Box mt={2}>
        <TextField
          id="outlined-basic"
          size="small"
          variant="outlined"
          aria-label={`시장가로 매매할 ${placeHolder}`}
          placeholder={placeHolder}
          autoComplete="off"
          hiddenLabel
          fullWidth
        />
      </Box>
      <Box mt={2} display="flex" justifyContent="space-between">
        <Button variant="outlined">10%</Button>
        <Button variant="outlined">25%</Button>
        <Button variant="outlined">50%</Button>
        <Button variant="outlined">100%</Button>
      </Box>
    </>
  );
}

function limitPrice() {
  return (
    <>
      <Box mt={2}>
        <TextField
          id="outlined-basic"
          size="small"
          variant="outlined"
          aria-label="가격 입력 단위: SP"
          placeholder="가격 (SP)"
          autoComplete="off"
          hiddenLabel
          fullWidth
        />
      </Box>
      <Box mt={2}>
        <TextField
          id="outlined-basic"
          size="small"
          variant="outlined"
          aria-label="거래 수량"
          placeholder="수량"
          autoComplete="off"
          hiddenLabel
          fullWidth
        />
      </Box>
      <Box mt={2} display="flex" justifyContent="space-between">
        <Button variant="outlined">10%</Button>
        <Button variant="outlined">25%</Button>
        <Button variant="outlined">50%</Button>
        <Button variant="outlined">100%</Button>
      </Box>
      <Box mt={2}>
        <Typography variant="body2" color="textSecondary">
          총액
        </Typography>
        <Typography variant="h6">0 KRW</Typography>
      </Box>
    </>
  );
}

export default function OrderForm() {
  const [buySell, setBuySell] = useState("buy");
  const [orderType, setOrderType] = useState("market");

  const handleBuySellChange = (event, newBuySell) => {
    setBuySell(newBuySell);
  };

  const handleOrderTypeChange = (event, newOrderType) => {
    setOrderType(newOrderType);
  };

  return (
    <Box sx={{ width: "100%" }}>
      <ToggleButtonGroup
        color="primary"
        value={buySell}
        exclusive
        onChange={handleBuySellChange}
        aria-label="order type"
        fullWidth
      >
        <ToggleButton value="buy">매수</ToggleButton>
        <ToggleButton value="sell">매도</ToggleButton>
      </ToggleButtonGroup>

      <Box mt={2}>
        <Typography variant="body2" color="textSecondary">
          주문유형
        </Typography>
        <ToggleButtonGroup
          color="primary"
          value={orderType}
          exclusive
          onChange={handleOrderTypeChange}
          aria-label="text alignment"
          sx={{
            "& .MuiToggleButtonGroup-grouped": {
              height: 20,
              fontSize: "0.7rem",
            },
          }}
        >
          <ToggleButton value="market" aria-label="left aligned">
            시장가
          </ToggleButton>
          <ToggleButton value="limit" aria-label="centered">
            지정가
          </ToggleButton>
        </ToggleButtonGroup>
      </Box>

      <Box mt={2}>
        <Typography variant="body2" color="textSecondary">
          보유
        </Typography>
        <Typography variant="h6">0 KRW</Typography>
      </Box>

      <Box mt={2}>
        <Typography variant="body2" color="textSecondary">
          {buySell === "buy" ? "매수 가능" : "매도 가능"}
        </Typography>
        <Typography variant="h6">0 KRW</Typography>
      </Box>

      {orderType === "market" ? marketPrice(buySell) : limitPrice()}

      <Button variant="contained" color="primary" fullWidth sx={{ mt: 2 }}>
        {buySell === "buy" ? "매수" : "매도"}
      </Button>

      <Typography
        variant="caption"
        color="textSecondary"
        align="right"
        sx={{ mt: 1 }}
      >
        수수료율 0.1%
      </Typography>
    </Box>
  );
}
