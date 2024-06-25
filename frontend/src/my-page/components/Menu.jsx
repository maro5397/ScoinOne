import { useState } from "react";
import { Paper, Tabs, Tab } from "@mui/material";
import NotificationsIcon from "@mui/icons-material/Notifications";
import AccountBalanceWalletIcon from "@mui/icons-material/AccountBalanceWallet";
import HistoryIcon from "@mui/icons-material/History";
import QuestionAnswerIcon from "@mui/icons-material/QuestionAnswer";
import NotificationsContent from "./NotificationsContent";
import VirtualAssetInfoContent from "./VirtualAssetInfoContent";
import OrderHistoryContent from "./OrderHistoryContent";
import MyQuestionsContent from "./MyQuestionsContent";

export default function Menu() {
  const [selectedTab, setSelectedTab] = useState(0); // 선택된 탭 인덱스

  const handleTabChange = (event, newValue) => {
    setSelectedTab(newValue);
  };

  return (
    <>
      <Paper sx={{ width: "100%", mt: 2 }}>
        <Tabs
          value={selectedTab}
          onChange={handleTabChange}
          centered
          sx={{
            "& .MuiTabs-flexContainer": {
              gap: "50px",
            },
          }}
        >
          <Tab icon={<NotificationsIcon />} label="알림" />
          <Tab icon={<AccountBalanceWalletIcon />} label="자산정보" />
          <Tab icon={<HistoryIcon />} label="체결/미체결" />
          <Tab icon={<QuestionAnswerIcon />} label="내 질문" />
        </Tabs>
      </Paper>
      {selectedTab === 0 && <NotificationsContent />}
      {selectedTab === 1 && <VirtualAssetInfoContent />}
      {selectedTab === 2 && <OrderHistoryContent />}
      {selectedTab === 3 && <MyQuestionsContent />}
    </>
  );
}
