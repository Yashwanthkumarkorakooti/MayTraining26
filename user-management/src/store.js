import { configureStore } from "@reduxjs/toolkit";
import { CharacterReducer } from "./store/reducer/CharacterReducer";

export const store = configureStore({
    reducer: {
        characters : CharacterReducer
    }
})


/*
1. CommonJs Module
2. ES Module : Ecma Script -- Object Oriented Programming like Jave

Component : jsx - Es activated
.js file : Commonjs is activated, change to ES Module

*/