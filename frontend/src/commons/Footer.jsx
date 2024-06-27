import { Link as RouterLink } from "react-router-dom";
import { Box, Container, Link, Stack, Typography } from "@mui/material";
import Copyright from "./Copyright";

const logoStyle = {
  width: "140px",
  height: "auto",
  marginLeft: "10px",
  marginBottom: "10px",
};

export default function Footer() {
  return (
    <Container
      component="footer"
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        gap: { xs: 4, sm: 8 },
        py: { xs: 8, sm: 10 },
        textAlign: { sm: "center", md: "left" },
      }}
    >
      <Box
        sx={{
          display: "flex",
          flexDirection: { xs: "column", sm: "row" },
          width: "100%",
          justifyContent: "space-between",
        }}
      >
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            gap: 4,
            minWidth: { xs: "100%", sm: "60%" },
          }}
        >
          <Box sx={{ width: { xs: "100%", sm: "60%" } }}>
            <Box sx={{ ml: "-15px" }}>
              <img src={"/logo.png"} style={logoStyle} alt="ScoinOne" />
            </Box>
            <Typography variant="body2" color="text.secondary" mb={2}>
              ScoinOne | reindeer002 | lsj000424@gmail.com
            </Typography>
            <Typography variant="body2" color="text.secondary" mt={1}>
              <Copyright />
            </Typography>
            <Stack direction="row" spacing={1} useFlexGap></Stack>
          </Box>
        </Box>
        <Box
          sx={{
            display: { xs: "none", sm: "flex" },
            flexDirection: "column",
            gap: 1,
          }}
        >
          <Typography variant="body2" fontWeight={600}>
            Service
          </Typography>
          <Link component={RouterLink} color="text.secondary" to="/auction">
            거래소
          </Link>
          <Link
            component={RouterLink}
            color="text.secondary"
            to="/mypage/assetinfo"
          >
            내 자산
          </Link>
          <Link
            component={RouterLink}
            color="text.secondary"
            to="/board/announce"
          >
            공지사항
          </Link>
          <Link
            component={RouterLink}
            color="text.secondary"
            to="/board/questions"
          >
            질의사항
          </Link>
        </Box>
        <Box
          sx={{
            display: { xs: "none", sm: "flex" },
            flexDirection: "column",
            gap: 1,
          }}
        >
          <Typography variant="body2" fontWeight={600}>
            Developer
          </Typography>
          <Link component={RouterLink} color="text.secondary" to="#">
            About
          </Link>
          <Link component={RouterLink} color="text.secondary" to="#">
            Careers
          </Link>
          <Link component={RouterLink} color="text.secondary" to="#">
            Contact
          </Link>
        </Box>
      </Box>
    </Container>
  );
}
