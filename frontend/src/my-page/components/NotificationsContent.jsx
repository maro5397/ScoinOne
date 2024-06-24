import React, { useState } from "react";
import Accordion from "@mui/material/Accordion";
import AccordionDetails from "@mui/material/AccordionDetails";
import AccordionSummary from "@mui/material/AccordionSummary";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const alarm = [
  {
    question: "새로운 마이페이지 알람창이 생성되었습니다!",
    answer: "새로운 마이페이지 알람창이 생성되었습니다",
  },
  {
    question: "다음에는 가산정보창을 생성할 차례입니다.",
    answer: "다음에는 가산정보창을 생성할 차례입니다",
  },
];

export default function NotificationsContent() {
  const [expanded, setExpanded] = React.useState(false);

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  return (
    <>
      <Container
        id="alarm"
        sx={(theme) => ({
          pt: 3,
          pb: 3,
          position: "relative",
          display: "flex",
          flexDirection: "column",
          gap: { xs: 2, sm: 2 },
          boxShadow:
            theme.palette.mode === "light"
              ? `0 0 1px rgba(85, 166, 246, 0.1), 1px 1.5px 2px -1px rgba(85, 166, 246, 0.15), 4px 4px 12px -2.5px rgba(85, 166, 246, 0.15)`
              : "0 0 1px rgba(2, 31, 59, 0.7), 1px 1.5px 2px -1px rgba(2, 31, 59, 0.65), 4px 4px 12px -2.5px rgba(2, 31, 59, 0.65)",
        })}
      >
        <Box sx={{ width: "100%", alignItems: "center" }}>
          {alarm.map((alarm, index) => (
            <Accordion
              expanded={expanded === index.toString()}
              onChange={handleChange(index.toString())}
            >
              <AccordionSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel1d-content"
                id="panel1d-header"
              >
                <Typography component="h3" variant="subtitle2">
                  {alarm.question}
                </Typography>
              </AccordionSummary>
              <AccordionDetails>
                <Typography
                  variant="body2"
                  gutterBottom
                  sx={{ maxWidth: { sm: "100%", md: "70%" } }}
                >
                  {alarm.answer}
                </Typography>
              </AccordionDetails>
            </Accordion>
          ))}
        </Box>
        <Typography variant="caption" color="text.primary">
          알림은 최근 30일까지만 확인 가능합니다.
        </Typography>
      </Container>
    </>
  );
}
