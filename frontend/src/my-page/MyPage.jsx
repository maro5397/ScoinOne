import { useState } from "react";
import Profile from "./components/Profile";
import Menu from "./components/Menu";
import BaseLayout from "../commons/BaseLayout";

export default function MyPage() {
  const [nickname, setNickname] = useState("admin");

  const handleNicknameChange = (newNickname) => {
    setNickname(newNickname);
  };

  return (
    <BaseLayout marginTop={100}>
      <Profile
        profileImage=""
        nickname={nickname}
        email="admin@gmail.com"
        onNicknameChange={handleNicknameChange}
      />
      <Menu />
    </BaseLayout>
  );
}
