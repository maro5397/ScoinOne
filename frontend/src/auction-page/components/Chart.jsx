import React, { useState } from "react";
import { useEffect, useRef } from "react";
import { LineStyle, createChart } from "lightweight-charts";
import { Grid, Box, Typography, touchRippleClasses } from "@mui/material";

export default function Chart({ mode }) {
  const chartContainerRef = useRef();
  const [candlePrice, setCandlePrice] = useState(null);
  const [linePrice, setLinePrice] = useState(null);

  const lightThemeOptions = {
    layout: {
      background: { color: "#FFFFFF" },
      textColor: "#000000",
    },
    grid: {
      vertLines: { color: "#EEEEEE" },
      horzLines: { color: "#EEEEEE" },
    },
  };

  const darkThemeOptions = {
    layout: {
      background: { color: "#222222" },
      textColor: "#DDDDDD",
    },
    grid: {
      vertLines: { color: "#444444" },
      horzLines: { color: "#444444" },
    },
  };

  useEffect(() => {
    const candleData = [
      { open: 10, high: 10.63, low: 9.49, close: 9.55, time: 1642427876 },
      { open: 9.55, high: 10.3, low: 9.42, close: 9.94, time: 1642514276 },
      { open: 9.94, high: 10.17, low: 9.92, close: 9.78, time: 1642600676 },
      { open: 9.78, high: 10.59, low: 9.18, close: 9.51, time: 1642687076 },
      { open: 9.51, high: 10.46, low: 9.1, close: 10.17, time: 1642773476 },
      { open: 10.17, high: 10.96, low: 10.16, close: 10.47, time: 1642859876 },
      { open: 10.47, high: 11.39, low: 10.4, close: 10.81, time: 1642946276 },
      { open: 10.81, high: 11.6, low: 10.3, close: 10.75, time: 1643032676 },
      { open: 10.75, high: 11.6, low: 10.49, close: 10.93, time: 1643119076 },
      { open: 10.93, high: 11.53, low: 10.76, close: 10.96, time: 1643205476 },
    ];

    const lineData = candleData.map((item) => ({
      time: item.time,
      value: (item.close + item.high) / 2,
    }));

    const myPriceLine = {
      price: 12,
      color: "purple",
      lineWidth: 1,
      style: LineStyle.Solid,
      axisLabelVisible: true,
      title: "매매가",
    };

    const chart = createChart(chartContainerRef.current);
    const lineSeries = chart.addLineSeries();
    const candleStickSeries = chart.addCandlestickSeries();

    chart.applyOptions({
      width: chartContainerRef.current.clientWidth,
      height: 500,
      crosshair: {
        vertLine: {
          style: LineStyle.Solid,
          color: "#9B7DFF",
          labelBackgroundColor: "#9B7DFF",
        },
        horzLine: {
          style: LineStyle.Solid,
          color: "#9B7DFF",
          labelBackgroundColor: "#9B7DFF",
        },
      },
      localization: {
        locale: "ko-KR",
        priceFormatter: (price) => {
          const spPrice = price.toFixed(0) + " SP";
          return spPrice;
        },
      },
    });

    if (mode === "dark") {
      chart.applyOptions(darkThemeOptions);
    } else {
      chart.applyOptions(lightThemeOptions);
    }

    candleStickSeries.applyOptions({
      wickUpColor: "rgb(54, 116, 217)",
      upColor: "rgb(54, 116, 217)",
      wickDownColor: "rgb(225, 50, 85)",
      downColor: "rgb(225, 50, 85)",
      borderVisible: false,
    });

    lineSeries.applyOptions({
      lineWidth: 1,
      lastValueVisible: false,
      priceLineVisible: false,
      pointMarkersVisible: false,
      crosshairMarkerVisible: false,
    });

    chart.subscribeCrosshairMove((param) => {
      if (param.time) {
        const candleData = param.seriesData.get(candleStickSeries);
        const lineData = param.seriesData.get(lineSeries);
        setCandlePrice(candleData);
        setLinePrice(lineData);
      }
    });

    chart.priceScale("right").applyOptions({
      borderColor: "#71649C",
    });

    chart.timeScale().applyOptions({
      borderColor: "#71649C",
      fixLeftEdge: true,
      timeVisible: true,
      rightOffset: 10,
      minBarSpacing: 3,
    });

    chart.timeScale().fitContent();

    candleStickSeries.setData(candleData);
    lineSeries.setData(lineData);

    candleStickSeries.createPriceLine(myPriceLine);

    const handleResize = () => {
      chart.applyOptions({
        width: chartContainerRef.current.clientWidth,
      });
    };

    window.addEventListener("resize", handleResize);

    return () => {
      chart.remove();
      window.removeEventListener("resize", handleResize);
    };
  }, [mode]);

  return (
    <Grid container>
      <Grid item xs={12}>
        <Typography variant="subtitle1" fontWeight="bold">
          CoinName
        </Typography>
      </Grid>
      <Grid item xs={12}>
        <Box sx={{ display: "flex" }}>
          <Typography variant="h5" fontWeight="bold">
            CoinPrice{" "}
          </Typography>
          <Typography variant="subtitle2">+0.4% +30000SP</Typography>
        </Box>
      </Grid>
      <Grid item xs={12}>
        <Typography variant="body2">Description</Typography>
      </Grid>
      <Grid item xs={12}>
        <Box sx={{ position: "relative" }}>
          <div ref={chartContainerRef}>
            <Box
              sx={{
                position: "absolute",
                zIndex: 20,
                color: "gray",
                top: 15,
                left: 15,
              }}
            >
              <Typography>
                {candlePrice &&
                  `시가: ${candlePrice?.open} 고가: ${candlePrice?.high} 저가:
              ${candlePrice?.low} 종가: ${candlePrice?.close}`}
              </Typography>
              <Typography>
                {linePrice && `VAL: ${linePrice?.value.toFixed(2)}`}
              </Typography>
            </Box>
          </div>
        </Box>
      </Grid>
    </Grid>
  );
}
