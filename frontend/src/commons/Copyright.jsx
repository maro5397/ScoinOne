import { Link as RouterLink } from "react-router-dom";
import { Link } from "@mui/material";

export default function Copyright() {
  return (
    <>
      {"Copyright © "}
      <Link component={RouterLink} to="/">
        SCoinOne
      </Link>
      {", All rights reserved by reindeer002."}
    </>
  );
}
