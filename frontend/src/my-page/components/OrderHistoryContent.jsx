import { useNavigate } from "react-router-dom";
import {
  List,
  ListItemButton,
  ListItemText,
  Typography,
  Container,
  Grid,
  Paper,
} from "@mui/material";

export default function OrderHistoryContent() {
  const navigate = useNavigate();

  const nonExecution = [
    {
      id: 1,
      assetName: "SOL",
      tradingType: "지정가",
      type: "매도",
      date: "24-05-01 18:18:42",
      price: 171900,
      quantity: 0.13852553,
      amount: 23812,
    },
    {
      id: 2,
      assetName: "SOL",
      tradingType: "지정가",
      type: "매수",
      date: "24-04-30 19:09:42",
      price: 186700,
      quantity: 0.13852553,
      amount: 25863,
    },
  ];

  const execution = [
    {
      id: 1,
      assetName: "SOL",
      tradingType: "지정가",
      type: "매도",
      date: "24-05-01 18:18:42",
      price: 171900,
      quantity: 0.13852553,
      amount: 23812,
    },
    {
      id: 2,
      assetName: "SOL",
      tradingType: "지정가",
      type: "매수",
      date: "24-04-30 19:09:42",
      price: 186700,
      quantity: 0.13852553,
      amount: 25863,
    },
  ];

  const handleHistoryClick = (assetId) => {
    navigate(`/assets/${assetId}`);
  };

  function executionStatusComponent(executionData) {
    return (
      <List>
        {executionData.map((executionData) => (
          <ListItemButton
            key={executionData.id}
            onClick={() =>
              handleHistoryClick(executionData.assetName.toLowerCase())
            }
          >
            <ListItemText
              secondaryTypographyProps={{ component: "div" }}
              primary={
                <Typography
                  variant="body1"
                  color={executionData.type === "매수" ? "error" : "primary"}
                  fontWeight="bold"
                >
                  {executionData.assetName} {executionData.tradingType}{" "}
                  {executionData.type}
                </Typography>
              }
              secondary={
                <>
                  <Typography variant="body2" color="textSecondary">
                    체결가격: {executionData.price}
                  </Typography>
                  <Typography variant="body2" color="textSecondary">
                    체결수량: {executionData.quantity}
                  </Typography>
                  <Typography variant="body2" color="textSecondary">
                    체결금액: {executionData.amount}
                  </Typography>
                  <Typography variant="body2" color="textSecondary">
                    체결날짜: {executionData.date}
                  </Typography>
                </>
              }
            />
          </ListItemButton>
        ))}
      </List>
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
      <Grid container spacing={2} justifyContent="space-between">
        <Grid item xs={6}>
          <Typography
            variant="h6"
            color="textSecondary"
            fontWeight="bold"
            align="center"
          >
            체결내역
          </Typography>
          <Paper sx={{ mt: 3 }}>{executionStatusComponent(execution)}</Paper>
        </Grid>

        <Grid item xs={6} sx={{ width: "100%", alignItems: "center" }}>
          <Typography
            variant="h6"
            color="textSecondary"
            fontWeight="bold"
            align="center"
          >
            미체결내역
          </Typography>
          <Paper sx={{ mt: 3 }}>{executionStatusComponent(nonExecution)}</Paper>
        </Grid>
      </Grid>
    </Container>
  );
}
