import { Divider } from "@mui/material";
import Introduction from "./components/Introduction";
import Slider from "./components/Slider";
import Announce from "./components/Announce";
import FAQ from "./components/FAQ";
import ArticleLayout from "./components/ArticleLayout";
import BaseLayout from "../commons/BaseLayout";

export default function LandingPage() {
  return (
    <BaseLayout marginTop={0}>
      <Introduction />
      <Divider />
      <Slider />
      <Divider />
      <ArticleLayout>
        <Announce />
        <FAQ />
      </ArticleLayout>
    </BaseLayout>
  );
}
