import { useState } from "react";
import { Link as RouterLink } from "react-router-dom";
import PropTypes from "prop-types";
import {
  Box,
  AppBar,
  Toolbar,
  Button,
  Container,
  Divider,
  Typography,
  MenuItem,
  Drawer,
  TextField,
  Badge,
  IconButton,
  Link,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import NotificationsIcon from "@mui/icons-material/Notifications";
import ToggleColorMode from "./ToggleColorMode";

const logoStyle = {
  width: "140px",
  height: "auto",
  cursor: "pointer",
  marginRight: "10px",
  marginLeft: "10px",
};

export default function Navigation({ mode, toggleColorMode }) {
  const [open, setOpen] = useState(false);

  const toggleDrawer = (newOpen) => () => {
    setOpen(newOpen);
  };

  return (
    <div>
      <AppBar
        position="fixed"
        sx={{
          boxShadow: 0,
          bgcolor: "transparent",
          backgroundImage: "none",
          mt: 2,
        }}
      >
        <Container maxWidth="lg">
          <Toolbar
            variant="regular"
            sx={(theme) => ({
              display: "flex",
              alignItems: "center",
              justifyContent: "space-between",
              flexShrink: 0,
              borderRadius: "999px",
              bgcolor:
                theme.palette.mode === "light"
                  ? "rgba(255, 255, 255, 0.4)"
                  : "rgba(0, 0, 0, 0.4)",
              backdropFilter: "blur(24px)",
              maxHeight: 40,
              border: "1px solid",
              borderColor: "divider",
              boxShadow:
                theme.palette.mode === "light"
                  ? `0 0 1px rgba(85, 166, 246, 0.1), 1px 1.5px 2px -1px rgba(85, 166, 246, 0.15), 4px 4px 12px -2.5px rgba(85, 166, 246, 0.15)`
                  : "0 0 1px rgba(2, 31, 59, 0.7), 1px 1.5px 2px -1px rgba(2, 31, 59, 0.65), 4px 4px 12px -2.5px rgba(2, 31, 59, 0.65)",
            })}
          >
            <Box
              sx={{
                flexGrow: 1,
                display: "flex",
                alignItems: "center",
                ml: "-18px",
                px: 0,
              }}
            >
              <Link component={RouterLink} to="/">
                <img src={"/logo.png"} style={logoStyle} alt="ScoinOne" />
              </Link>
              <Box sx={{ display: { xs: "none", md: "flex" } }}>
                <TextField
                  id="outlined-basic"
                  hiddenLabel
                  size="small"
                  variant="outlined"
                  aria-label="검색하고 싶은 가상자산 입력"
                  placeholder="가상자산 검색"
                  autoComplete="off"
                />
                <MenuItem sx={{ py: "6px", px: "12px" }}>
                  <Link component={RouterLink} to="/auction">
                    <Typography variant="body2" color="text.primary">
                      거래소
                    </Typography>
                  </Link>
                </MenuItem>
                <MenuItem sx={{ py: "6px", px: "12px" }}>
                  <Link component={RouterLink} to="/mypage">
                    <Typography variant="body2" color="text.primary">
                      내 자산
                    </Typography>
                  </Link>
                </MenuItem>
              </Box>
            </Box>
            <Box
              sx={{
                display: { xs: "none", md: "flex" },
                gap: 0.5,
                alignItems: "center",
              }}
            >
              <ToggleColorMode mode={mode} toggleColorMode={toggleColorMode} />
              <IconButton color="primary">
                <Badge badgeContent={1} color="secondary">
                  <NotificationsIcon />
                </Badge>
              </IconButton>
              <Button
                color="primary"
                variant="text"
                size="small"
                component={RouterLink}
                to="/login"
              >
                로그인
              </Button>
              <Button
                color="primary"
                variant="contained"
                size="small"
                component={RouterLink}
                to="/register"
              >
                회원가입
              </Button>
            </Box>
            <Box sx={{ display: { sm: "", md: "none" } }}>
              <Button
                variant="text"
                color="primary"
                aria-label="menu"
                onClick={toggleDrawer(true)}
                sx={{ minWidth: "30px", p: "4px" }}
              >
                <MenuIcon />
              </Button>
              <Drawer anchor="right" open={open} onClose={toggleDrawer(false)}>
                <Box
                  sx={{
                    minWidth: "60dvw",
                    p: 2,
                    backgroundColor: "background.paper",
                    flexGrow: 1,
                  }}
                >
                  <Box
                    sx={{
                      display: "flex",
                      flexDirection: "column",
                      alignItems: "end",
                      flexGrow: 1,
                    }}
                  >
                    <ToggleColorMode
                      mode={mode}
                      toggleColorMode={toggleColorMode}
                    />
                  </Box>
                  <MenuItem>
                    <Link component={RouterLink} to="/auction">
                      거래소
                    </Link>
                  </MenuItem>
                  <MenuItem>
                    <Link component={RouterLink} to="/mypage">
                      내 자산
                    </Link>
                  </MenuItem>
                  <MenuItem>
                    <Link component={RouterLink} to="/board/announce">
                      공지사항
                    </Link>
                  </MenuItem>
                  <MenuItem>
                    <Link component={RouterLink} to="/board/questions">
                      질의사항
                    </Link>
                  </MenuItem>
                  <Divider />
                  <MenuItem>
                    <Button
                      color="primary"
                      variant="contained"
                      component={RouterLink}
                      to="/register"
                      sx={{ width: "100%" }}
                    >
                      회원가입
                    </Button>
                  </MenuItem>
                  <MenuItem>
                    <Button
                      color="primary"
                      variant="outlined"
                      component={RouterLink}
                      to="/login"
                      sx={{ width: "100%" }}
                    >
                      로그인
                    </Button>
                  </MenuItem>
                </Box>
              </Drawer>
            </Box>
          </Toolbar>
        </Container>
      </AppBar>
    </div>
  );
}

Navigation.propTypes = {
  mode: PropTypes.oneOf(["dark", "light"]).isRequired,
  toggleColorMode: PropTypes.func.isRequired,
};
