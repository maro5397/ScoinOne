import { useState } from "react";
import {
  TextField,
  List,
  ListItem,
  ListItemText,
  Box,
  Divider,
  Typography,
  Grid,
} from "@mui/material";

export default function VirtualAssetList() {
  const [searchTerm, setSearchTerm] = useState("");
  const [virtualAssets] = useState([
    {
      name: "test1",
      price: 10000,
      fluctuationRate: -100,
      transactionAmount: 10000,
    },
    {
      name: "test2",
      price: 10000,
      fluctuationRate: 50,
      transactionAmount: 10000,
    },
  ]);

  const filteredAssets = virtualAssets.filter((asset) =>
    asset.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <Box>
      <TextField
        id="outlined-basic"
        size="small"
        variant="outlined"
        aria-label="가상자산 입력 시 자동 정렬"
        placeholder="가상자산 검색"
        hiddenLabel
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        fullWidth
      />
      <List>
        <ListItem>
          <Grid container spacing={2} justifyContent="space-around">
            <Grid item xs={3}>
              <ListItemText
                primary={<Typography variant="body2">이름</Typography>}
                sx={{ textAlign: "left" }}
              />
            </Grid>
            <Grid item xs={3}>
              <ListItemText
                primary={<Typography variant="body2">가격</Typography>}
                sx={{ textAlign: "center" }}
              />
            </Grid>
            <Grid item xs={3}>
              <ListItemText
                primary={<Typography variant="body2">등락률</Typography>}
                sx={{ textAlign: "center" }}
              />
            </Grid>
            <Grid item xs={3}>
              <ListItemText
                primary={<Typography variant="body2">거래대금</Typography>}
                sx={{ textAlign: "right" }}
              />
            </Grid>
          </Grid>
        </ListItem>
        <Divider />
        {filteredAssets.map((asset) => (
          <Box>
            <ListItem key={asset.name}>
              <Grid container spacing={2} justifyContent="space-around">
                <Grid item xs={3}>
                  <ListItemText
                    primary={
                      <Typography variant="body2">{asset.name}</Typography>
                    }
                    sx={{ textAlign: "left" }}
                  />
                </Grid>
                <Grid item xs={3}>
                  <ListItemText
                    primary={
                      <Typography
                        variant="body2"
                        color={asset.fluctuationRate > 0 ? "error" : "primary"}
                      >
                        {asset.price}
                      </Typography>
                    }
                    sx={{ textAlign: "center" }}
                  />
                </Grid>
                <Grid item xs={3}>
                  <ListItemText
                    primary={
                      <Typography
                        variant="body2"
                        color={asset.fluctuationRate > 0 ? "error" : "primary"}
                      >
                        {asset.fluctuationRate} %
                      </Typography>
                    }
                    sx={{ textAlign: "center" }}
                  />
                </Grid>
                <Grid item xs={3}>
                  <ListItemText
                    primary={
                      <Typography variant="body2">
                        {asset.transactionAmount}
                      </Typography>
                    }
                    sx={{ textAlign: "right" }}
                  />
                </Grid>
              </Grid>
            </ListItem>
            <Divider />
          </Box>
        ))}
      </List>
    </Box>
  );
}
