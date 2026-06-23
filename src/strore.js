import { configureStore } from "@reduxjs/toolkit";
import { adminReducer } from "./store/reducer/adminReducer";

export const store = configureStore({
  reducer: {
    admin : adminReducer
  },
});