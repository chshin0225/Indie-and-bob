import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'
import cookies from 'vue-cookies'
import SERVER from '../api/base'
import router from '../router'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // user
    jwtToken: cookies.get('user'),
    isUser: false,
    // isLoggedin: false,
    changedPw: false,
    oriEmail: "",
    oriPassword: "",
    userInfo: null,
    
    // community
    articleList: [],
    
    // project
    projectList: [],
    project: null,

    // error
    errorDetail: null,
  },

  getters: {
    headersConfig: state => ({
      headers: {
        headers: `Bearer ${state.jwtToken}` 
      }
    }),
    isLoggedIn: state => !!state.jwtToken,
  },

  mutations: {
    // users
    setToken(state, val) {
      state.jwtToken = val
      cookies.set('user', val)
    },
    SetLoggedIn(state) {
      if (cookies.isKey('user')) {
        state.token = cookies.get('user')
        state.isLoggedin = true
      } else {
        state.isLoggedin = false
      }
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
    setUserInfo(state, val) {
      state.userInfo = val
      console.log(state.userInfo)
    },

    // community
    setArticleList(state, val) {
      state.articleList = val
    },

    // project
    setProjectList(state, val) {
      state.projectList = val
    },
    setProject(state, val) {
      state.project = val
    },

    // error
    setErrorDetail(state, val) {
      state.errorDetail = val
      console.log(state.errorDetail)
    },
  },

  actions: {
    // user
    login({ commit }, loginData) {
      axios.post(SERVER.BASE + SERVER.LOGIN, loginData)
        .then(res => {
          console.log(res.headers['jwt-auth-token'])
          commit('setEmail', loginData.email)
          commit('setPassword', loginData.password)
          // commit('setLoggedIn', true)
          // 쿠키에 저장
          commit('setToken', res.headers['jwt-auth-token'])
          router.push('/feed/main')
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
      // console.log(signupData.email)
      // console.log(SERVER.BASE)
      // console.log(SERVER.SIGNUP)
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

    logout({ commit }) {
      commit('setEmail', '')
      commit('setPassword', '')
      commit('setToken', null)
      cookies.remove('user')
      router.push({ name: 'FeedMain' })
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

    getUserInfo({ commit }, username) {
      axios.get(SERVER.BASE + SERVER.USERINFO + `/${username}`)
        .then(res => {
          console.log(res.data)
          commit('setUserInfo', res.data)
        })
        .catch(err => console.error(err))
    },

    changeUserInfo(context, changedData) {
      axios.POST(SERVER.BASE + SERVER.USERINFO, changedData, getters.headersConfig)
        .then(res => {
          context.commit('setUser', res.data)
          alert('회원 정보가 변경되었습니다.')
        })
        .catch(err => console.error(err))
    },

    follow() {

    },

    // community
    // fetchArticles({ commit }) {
    //   axios.get(게시글들 가져오기)
    //     .then(res => {
    //       commit('setArticleList', res.data)
    //     })
    //     .catch(err => console.error(err))
    // },

    // createArticle(context, articleData) {
    //   axios.post(새 게시글 작성)
    //     .then(res => console.log(res.data))
    //     .catch(err => console.error(err))
    //   router.push({ name: 'CommunityMain' })
    // },

    // 기타
    goBack() {
      router.go('-1')
    },


    
    // project

    fetchProjects({ commit }) {
      axios.get(SERVER.BASE + SERVER.GAMELIST)
        .then(res => {
          console.log(res.data.object)
          commit('setProjectList', res.data.object)
        })
        .catch(err => console.error(err))
    },

    getProject({ commit }, gameId) {
      axios.get(SERVER.BASE + SERVER.GAME + `/${gameId}`)
      .then(res => {
        console.log(res.data.object[0])
        commit('setProject', res.data.object[0])
      })
      .catch(err => console.error(err))
    },
  },
  modules: {
  }
})
