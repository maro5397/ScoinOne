import { useState } from "react";
import AuctionLayout from "./components/AuctionLayout";
import Chart from "./components/Chart";
import VirtualAssetList from "./components/VirtualAssetList";
import OrderForm from "./components/OrderForm";
import OrderBook from "./components/OrderBook";
import OrderHistory from "./components/OrderHistory";
import BaseLayout from "../commons/BaseLayout";

export default function AuctionPage() {
  return (
    <BaseLayout marginTop={100}>
      <AuctionLayout
        Chart={<Chart></Chart>}
        VirtualAssetList={<VirtualAssetList></VirtualAssetList>}
        OrderBook={<OrderBook></OrderBook>}
        OrderForm={<OrderForm></OrderForm>}
        OrderHistory={<OrderHistory></OrderHistory>}
      ></AuctionLayout>
    </BaseLayout>
  );
}
