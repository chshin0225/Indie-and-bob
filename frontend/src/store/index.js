import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'
import SERVER from '../api/base'
import router from '../router'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isUser: false,
    isLoggedin: false,
    changedPw: false,
    oriEmail: "",
    oriPassword: "",
    errorDetail: null,
  },

  mutations: {
    setLoggedIn(state, val) {
      state.isLoggedIn = val
      console.log(state.isLoggedIn)
    },
    setChangedPw(state, val) {
      state.changedPw = val;
      console.log(state.changedPw)
    },
    setEmail(state, val) {
      state.oriEmail = val
      console.log(state.oriEmail)
    },
    setPassword(state, val) {
      state.oriPassword = val
      console.log(state.oriPassword)
    },
    setErrorDetail(state, val) {
      state.errorDetail = val
      console.log(state.errorDetail)
    }
  },

  actions: {
    LogIn({ commit }, loginData) {
      commit('setEmail', loginData.email)
      commit('setPassword', loginData.password)
      axios.get(SERVER.BASE + "" + SERVER.LOGIN + "?email=" + loginData.email + "&password=" + loginData.password)
        .then(res => {
          console.log(res)
          commit('setLoggedIn', true)
          router.push("/feed/main");
        })
        .catch(err => {
          if (err.response.status === 404) {
            router.push({ name: "PageNotFound" })
          } else {
            console.error(err)
          }
        })
    },

    SignUp({ commit }, signupData) {
      if (signupData.email.charAt(0) >= 'A' && signupData.email.charAt(0) <= 'Z') {
        signupData.email = signupData.email.substring(0, 1).toLowerCase() + signupData.email.substring(1)
      }
      commit('setEmail', '')
      commit('setPassword', '')
      console.log(signupData.email)
      console.log(SERVER.BASE)
      console.log(SERVER.SIGNUP)
      axios.post(SERVER.BASE + SERVER.SIGNUP, signupData)
        .then(res => {
          console.log(res)
          if (res.data.status) {
            commit('setLoggedIn', false)
            alert("회원가입 인증 메일이 발송되었습니다. 이메일을 확인해주세요.")
            router.push({ name: "Login" });
          } else {
            console.log(res.data.status)
            commit('setErrorDetail', res.data.data)
            router.push({ name: "ErrorPage" })
          }
        })
        .catch(err => {
          console.log(err.response)

        })
    },

    changePassword(context, passwordData) {
      axios.get(SERVER.BASE + SERVER.PWCHANGE + "?oripw=" + passwordData.oripw + "&newpw=" + passwordData.newpw)
        .then(res => {
          if (res.data.status) {
            console.log(res)
            alert("비밀번호가 변경되었습니다.")
            context.commit('setChangedPw', true)
            router.push({ name: 'FeedMain' })
          } else {
            alert(`error : ${res.data.data}`)
          }
        })
        .catch(err => {
          console.log(err.response)
        })
    },

  },
  modules: {
  }
})
