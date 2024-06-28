import { useNavigate } from "react-router-dom";
import {
  List,
  ListItemButton,
  ListItemText,
  Typography,
  Container,
} from "@mui/material";

export default function MyQuestionsContent() {
  const navigate = useNavigate();

  const questions = [
    {
      id: 1,
      title: "새로운 가상 자산은 어떻게 확인하나요?",
      date: "2024-06-22",
    },
    { id: 2, title: "잉코인 관련 질문입니다.", date: "2024-06-20" },
    { id: 3, title: "토토 시스템과 관련한 질문", date: "2024-06-18" },
  ];

  const handleQuestionClick = (questionId) => {
    navigate(`/questions/${questionId}`);
  };

  return (
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
      <List>
        {questions.map((question) => (
          <ListItemButton
            key={question.id}
            onClick={() => handleQuestionClick(question.id)}
          >
            <ListItemText
              secondaryTypographyProps={{ component: "div" }}
              primary={
                <Typography variant="body1" color="textPrimary">
                  {question.title}
                </Typography>
              }
              secondary={
                <Typography variant="body2" color="textSecondary">
                  {question.date}
                </Typography>
              }
            />
          </ListItemButton>
        ))}
      </List>
    </Container>
  );
}
