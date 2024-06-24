import React, { useState } from "react";
import {
  Box,
  Avatar,
  Typography,
  Button,
  IconButton,
  Modal,
  TextField,
} from "@mui/material";
import EditRoundedIcon from "@mui/icons-material/EditRounded";

export default function Profile({
  profileImage,
  nickname,
  email,
  onNicknameChange,
}) {
  const [isEditing, setIsEditing] = useState(false);
  const [newNickname, setNewNickname] = useState(nickname);

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleClose = () => {
    setIsEditing(false);
    setNewNickname(nickname);
  };

  const handleSave = () => {
    onNicknameChange(newNickname);
    setIsEditing(false);
  };

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        padding: 2,
      }}
    >
      <Avatar
        alt={nickname}
        src={profileImage}
        sx={{ width: 56, height: 56, mb: 2 }}
      />
      <Box sx={{ display: "flex", alignItems: "center" }}>
        <Typography variant="h6">{nickname}</Typography>
        <IconButton onClick={handleEditClick}>
          <EditRoundedIcon />
        </IconButton>
      </Box>
      <Typography variant="body2" color="textSecondary">
        {email}
      </Typography>

      <Modal open={isEditing} onClose={handleClose}>
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            bgcolor: "background.paper",
            boxShadow: 24,
            p: 4,
          }}
        >
          <Typography variant="h6" component="h2">
            닉네임 변경
          </Typography>
          <TextField
            label="새로운 닉네임"
            variant="outlined"
            value={newNickname}
            onChange={(e) => setNewNickname(e.target.value)}
            fullWidth
            sx={{ mt: 2 }}
          />
          <Button onClick={handleSave} variant="contained" sx={{ mt: 3 }}>
            저장
          </Button>
        </Box>
      </Modal>
    </Box>
  );
}
