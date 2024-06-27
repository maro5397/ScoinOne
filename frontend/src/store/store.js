import { configureStore } from "@reduxjs/toolkit";
import themeReducer from "./slices/themeSlice"; // 생성한 slice

const store = configureStore({
  reducer: {
    theme: themeReducer,
  },
});

export default store;
