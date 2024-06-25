import { Gauge } from "@mui/x-charts/Gauge";
import { Card, CardContent, Typography, Box } from "@mui/material";

export default function VirtualAssetCard({
  name,
  percentage,
  price,
  quantity,
}) {
  const totalValue = price * quantity;

  return (
    <Card>
      <CardContent>
        <Typography variant="h6" component="div" align="center">
          {name}
        </Typography>
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: 150,
          }}
        >
          <Gauge
            value={parseInt(percentage)}
            aria-label={`${name} 비율`}
            height={150}
            text={({ value }) => `${value} %`}
          />
        </Box>
        <Typography variant="body1" color="text.secondary" align="center">
          수량: {parseInt(quantity)}개
        </Typography>
        <Typography variant="body1" color="text.secondary" align="center">
          현재가: {parseInt(price).toLocaleString()}원
        </Typography>
        <Typography variant="body1" color="text.secondary" align="center">
          총 가치: {totalValue.toLocaleString()}원
        </Typography>
      </CardContent>
    </Card>
  );
}
