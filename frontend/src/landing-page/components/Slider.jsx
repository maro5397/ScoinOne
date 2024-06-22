import * as React from "react";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import Container from "@mui/material/Container";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import Carousel from "react-material-ui-carousel";

const items = [
  {
    name: "Adaptable performance",
    description:
      "Our product effortlessly adjusts to your needs, boosting efficiency and simplifying your tasks.",
  },
  {
    name: "Built to last",
    description:
      "Experience unmatched durability that goes above and beyond with lasting investment.",
  },
  {
    name: "Great user experience",
    description:
      "Integrate our product into your routine with an intuitive and easy-to-use interface.",
  },
];

function Item(props) {
  return (
    <Stack
      direction="column"
      color="inherit"
      component={Card}
      spacing={1}
      useFlexGap
      sx={{
        p: 3,
        height: "100%",
        border: "1px solid",
        borderColor: "grey.800",
        background: "transparent",
        backgroundColor: "grey.900",
        marginLeft: { xs: 0, md: 10 },
        marginRight: { xs: 0, md: 10 },
      }}
    >
      <Typography fontWeight="medium" gutterBottom>
        {props.item.name}
      </Typography>
      <Typography variant="body2" sx={{ color: "grey.400" }}>
        {props.item.description}
      </Typography>
    </Stack>
  );
}

export default function Slider() {
  return (
    <Box
      id="slider"
      sx={{
        pt: { xs: 4, sm: 12 },
        pb: { xs: 8, sm: 16 },
        color: "white",
        bgcolor: "#06090a",
      }}
    >
      <Container
        sx={{
          position: "relative",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          gap: { xs: 3, sm: 6 },
        }}
      >
        <Box
          sx={{
            width: { sm: "100%", md: "60%" },
            textAlign: { sm: "left", md: "center" },
          }}
        >
          <Typography component="h2" variant="h4">
            새로운 Scoin
          </Typography>
          <Typography variant="body1" sx={{ color: "grey.400" }}>
            지금 당장 새로 상장된 Scoin을 만나보세요
          </Typography>
        </Box>

        <Carousel
          autoPlay={true}
          animation="slide"
          indicators={true}
          swipe={true}
          cycleNavigation={true}
          fullHeightHover={true}
          navButtonsAlwaysVisible={true}
          navButtonsAlwaysInvisible={false}
          duration={500}
          sx={{
            width: "100%",
            "& .MuiCarousel-arrow": {
              zIndex: 2,
            },
            "& .MuiCarousel-root": {
              height: "100%",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            },
          }}
        >
          {items.map((item, i) => (
            <Item key={i} item={item} />
          ))}
        </Carousel>
      </Container>
    </Box>
  );
}
