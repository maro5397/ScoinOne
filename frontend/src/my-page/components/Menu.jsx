import { useState } from "react";
import { Link as RouterLink, useParams } from "react-router-dom";
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
  const { menu } = useParams();
  const [selectedTab, setSelectedTab] = useState(menu || "alarm"); // 선택된 탭 인덱스

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
          <Tab
            value="alarm"
            component={RouterLink}
            to="/mypage/alarm"
            icon={<NotificationsIcon />}
            label="알림"
          />
          <Tab
            value="assetinfo"
            component={RouterLink}
            to="/mypage/assetinfo"
            icon={<AccountBalanceWalletIcon />}
            label="자산정보"
          />
          <Tab
            value="execution"
            component={RouterLink}
            to="/mypage/execution"
            icon={<HistoryIcon />}
            label="체결/미체결"
          />
          <Tab
            value="questions"
            component={RouterLink}
            to="/mypage/questions"
            icon={<QuestionAnswerIcon />}
            label="내 질문"
          />
        </Tabs>
      </Paper>
      {selectedTab === "alarm" && <NotificationsContent />}
      {selectedTab === "assetinfo" && <VirtualAssetInfoContent />}
      {selectedTab === "execution" && <OrderHistoryContent />}
      {selectedTab === "questions" && <MyQuestionsContent />}
    </>
  );
}
