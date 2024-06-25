import { useState } from "react";
import AuctionLayout from "./components/AuctionLayout";
import Chart from "./components/Chart";
import VirtualAssetList from "./components/VirtualAssetList";
import OrderForm from "./components/OrderForm";
import OrderBook from "./components/OrderBook";
import OrderHistory from "./components/OrderHistory";
import BaseLayout from "../commons/BaseLayout";

export default function AuctionPage() {
  const [mode, setMode] = useState("light");

  return (
    <BaseLayout marginTop={100}>
      <AuctionLayout
        Chart={<Chart mode={mode}></Chart>}
        VirtualAssetList={<VirtualAssetList></VirtualAssetList>}
        OrderBook={<OrderBook></OrderBook>}
        OrderForm={<OrderForm></OrderForm>}
        OrderHistory={<OrderHistory></OrderHistory>}
      ></AuctionLayout>
    </BaseLayout>
  );
}
