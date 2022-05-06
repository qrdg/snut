import { createStore } from 'vuex';
import axios from 'axios';

export const store = createStore({
  state() {
    return {
      message: 'store 실행',
      storedAxios: axios.create({
        baseURL: 'http://localhost:8080/api',
        headers: {
          token: sessionStorage.getItem('token'), // header의 속성
        },
      }),
      imbbKey: "8749b3a6ce349d7a69b2f7ad2994ee28",
      imageBaseURL: 'http://localhost:8080/get/img?fileName=',
      sendToCuration: [],
      searchWord: {},
      storedData: {}
    }
  },
  getters: {
    getMessage(state) {
      return state.message;
    },
    getImageBaseURL(state) {
      return state.ImageBaseURL;
    },
    getSendToCuration(state) {
      return state.sendToCuration;
    },
  }, 
  mutations: {
    setMessage(state, text) {
      state.message = text;
    },
    setSendToCuration(state, arr) {
      console.log("arr", arr);
      state.sendToCuration = arr;
    },
    setSearchWord(state, word) {
      state.searchWord = word;
    },
    setStoredData(state, data) {
      state.storedData = data;
    }
  }, 
  actions: {

  }
});