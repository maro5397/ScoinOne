import Navigation from "./Navigation";
import Footer from "./Footer";

export default function BaseLayout({ children }) {
  return (
    <>
      <Navigation />
      {children}
      <Footer />
    </>
  );
}
