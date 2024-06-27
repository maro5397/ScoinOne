import { useState } from "react";
import { Link as RouterLink } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import {
  Avatar,
  Button,
  CssBaseline,
  TextField,
  FormControlLabel,
  Checkbox,
  Link,
  Grid,
  Box,
  Typography,
  Container,
  Modal,
} from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Copyright from "../commons/Copyright";

// TODO remove, this demo shouldn't need to reset the theme.

const defaultTheme = createTheme();

export default function LoginPage() {
  const [isFinding, setIsFinding] = useState(false);

  const handleFindClick = () => {
    setIsFinding(true);
  };

  const handleClose = () => {
    setIsFinding(false);
  };

  const handleSendEmail = () => {
    setIsFinding(false);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
    });
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            로그인
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              name="email"
              label="이메일"
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              id="password"
              name="password"
              label="비밀번호"
              type="password"
              autoComplete="current-password"
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="로그인 상태 유지"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              로그인
            </Button>
            <Grid container>
              <Grid item xs>
                <Link
                  component={RouterLink}
                  variant="body2"
                  onClick={handleFindClick}
                >
                  비밀번호 찾기
                </Link>
              </Grid>
              <Grid item>
                <Link component={RouterLink} href="" variant="body2">
                  회원가입
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Typography
          variant="body2"
          color="text.secondary"
          align="center"
          sx={{ mt: 8, mb: 4 }}
        >
          <Copyright />
        </Typography>
        <Modal open={isFinding} onClose={handleClose}>
          <Box
            component="form"
            onSubmit={handleSendEmail}
            noValidate
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
              비밀번호 찾기
            </Typography>
            <TextField
              label="비밀번호를 받을 이메일"
              id="email"
              name="email"
              margin="normal"
              variant="outlined"
              required
              autoFocus
              fullWidth
              sx={{ mt: 2 }}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              보내기
            </Button>
          </Box>
        </Modal>
      </Container>
    </ThemeProvider>
  );
}
