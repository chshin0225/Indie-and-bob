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
    changedPw: false,
    oriEmail: "",
    oriPassword: "",
    username: localStorage.getItem('username'),
    userInfo: null,

    // follow
    followerList: null,
    followingList: null,
    
    // community
    articleList: [],
    
    // project
    projectList: [],
    project: null,

    // like
    likedProjectList: [],

    // error
    errorDetail: null,

    searchResult: null,
  },

  getters: {
    headersConfig: state => ({
      headers: {
        "jwt-auth-token": state.jwtToken 
      }
    }),
    isLoggedIn: state => !!state.jwtToken,
    dataFetched: state => !!state.userInfo,
  },

  mutations: {
    // users
    setToken(state, val) {
      state.jwtToken = val
      cookies.set('user', val)
    },
    // SetLoggedIn(state) {
    //   if (cookies.isKey('user')) {
    //     state.token = cookies.get('user')
    //     state.isLoggedin = true
    //   } else {
    //     state.isLoggedin = false
    //   }
    // },

    setChangedPw(state, val) {
      state.changedPw = val;
      // console.log(state.changedPw)
    },
    setEmail(state, val) {
      state.oriEmail = val
      // console.log(state.oriEmail)
    },
    setPassword(state, val) {
      state.oriPassword = val
      // console.log(state.oriPassword)
    },
    setUsername(state, val) {
      state.username = val
      localStorage.setItem('username', val)
    },
    setUserInfo(state, val) {
      state.userInfo = val
      // console.log(state.userInfo)
    },
    setFollowerList(state, val) {
      state.followerList = val
    },
    setFollowingList(state, val) {
      state.followingList = val
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

    setLikedProjectList(state, val) {
      state.likedProjectList = val
    },

    // error
    setErrorDetail(state, val) {
      state.errorDetail = val
      console.log(state.errorDetail)
    },

    setSearchResult(state, val) {
      state.searchResult = val
    },
  },

  actions: {
    // user
    login({ commit }, loginData) {
      axios.post(SERVER.BASE + SERVER.LOGIN, loginData)
        .then(res => {
          // console.log(res.data.object)
          commit('setEmail', res.data.object.email)
          commit('setPassword', res.data.object.password)

          // 로그인한 유저의 닉네임 저장 
          commit('setUsername', res.data.object.nickname)

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

      // cookie에 있는 jwt 제거
      commit('setToken', null)
      cookies.remove('user')
      
      // local storage에 있는 username 정보 제거
      commit('setUsername', null)
      localStorage.removeItem('username')
      
      if (router.currentRoute.name !== 'FeedMain') {
        router.push({ name: 'FeedMain' })
      }
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
      commit('setUserInfo', null)
      axios.get(SERVER.BASE + SERVER.USERINFO + `/${username}`)
        .then(res => {
          // console.log('user',username)
          // console.log(res)
          // console.log(res.data)
          commit('setUserInfo', res.data.object)
        })
        .catch(err => console.error(err))
    },

    changeUserInfo({ getters, commit }, changedData) {
      axios.POST(SERVER.BASE + SERVER.USERINFO, changedData, getters.headersConfig)
        .then(res => {
          commit('setUser', res.data)
          alert('회원 정보가 변경되었습니다.')
        })
        .catch(err => console.error(err))
    },

    // follow 
    follow({ getters }, following) {
      console.log(following)
      axios.post(SERVER.BASE + SERVER.FOLLOWING, following, getters.headersConfig)
        .then(res => console.log(res.data))
        .catch(err => console.error(err))
    },

    fetchFollowers({ commit, getters }, username) {
      axios.get(SERVER.BASE + SERVER.FOLLOWER + `/${username}`, getters.headersConfig)
        .then(res => {
          // console.log(res.data.object)
          commit('setFollowerList', res.data.object)
        })
        .catch(err => console.error(err))
    },  

    fetchFollowings({ commit, getters }, username) {
      axios.get(SERVER.BASE + SERVER.FOLLOWING + `/${username}`, getters.headersConfig)
        .then(res => {
          // console.log(res.data.object)
          commit('setFollowingList', res.data.object)
        })
        .catch(err => console.error(err))
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

    
    fetchLikedProjects({ commit }, username) {
      axios.get(SERVER.BASE + SERVER.LIKEDPROJECT + `/${username}`)
        .then(res => {
          console.log(res.data.object)
          commit('setLikedProjectList', res.data.object)
        })
        .catch(err => console.error(err))
    },

    search({ commit }, searchKeyword) {
      axios.get(SERVER.BASE + SERVER.SEARCH + `${searchKeyword}`)
        .then(res => {
          console.log(res.data)
          commit('setSearchResult', res.data.object)
          router.push(`/search/${searchKeyword}`)
        })
        .catch(err => console.error(err))
    },  
  },
  modules: {
  }
})
