import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'
import cookies from 'vue-cookies'
import SERVER from '../api/base'
import router from '../router'
import firebase from 'firebase'

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
    picture: null,
              
    // notification
    wsUri : "ws://localhost:8080/websocket",
    websocket : null,
    message: 0,
    items: [],

    // follow
    followerList: null,
    followingList: null,
    isFollowing: false,
    
    // community
    articleList: [],
    
    // project
    projectList: [],
    project: null,
    myProjectList: [],
    fundedProjectList: [],

    // like
    likedProjectList: [],
    likedUserList: [],

    // error
    errorDetail: null,

    // search
    searchResult: null,
  },

  getters: {
    // auth
    headersConfig: state => ({
      headers: {
        "jwt-auth-token": state.jwtToken 
      }
    }),

    // user
    isLoggedIn: state => !!state.jwtToken,
    userDataFetched: state => !!state.userInfo,

    // project
    projectDataFetched: state => !!state.project,
    likedPeopleCount: state => state.likedUserList.length,
  },

  mutations: {
    // user
    setToken(state, val) {
      state.jwtToken = val
      cookies.set('user', val)
    },
    setChangedPw(state, val) {
      state.changedPw = val;
    },
    setEmail(state, val) {
      state.oriEmail = val
    },
    setPassword(state, val) {
      state.oriPassword = val
    },
    setUsername(state, val) {
      state.username = val
      localStorage.setItem('username', val)
    },
    setUserInfo(state, val) {
      state.userInfo = val
    },
    setPicture(state, val) {
      state.picture = val
    },

    // connection
    setWebsocket(state, val) {
      console.log(val)
      state.websocket = val
      console.log(state.websocket)
    },

    setMessage(state) {
      state.message += 1
      console.log(state.message)
    },

    setItems(state, val) {
      var followArray = val.split(',')
      state.items.push(followArray[1] + '님이' + followArray[2] + '님을' + followArray[0] + '했습니다.')
      console.log('1', state.items)
    },

    // follow
    setFollowerList(state, val) {
      state.followerList = val
    },
    setFollowingList(state, val) {
      state.followingList = val
    },
    setIsFollowing(state, val) {
      state.isFollowing = val
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
    setLikedUserList(state, val) {
      state.likedUserList = val
    },
    setContent(state, val) {
      state.project.content = val
    },
    setMyProjectList(state, val) {
      state.myProjectList = val
    },
    setFundedProjectList(state, val) {
      state.fundedProjectList = val
    },

    // search
    setSearchResult(state, val) {
      state.searchResult = val
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
          commit('setEmail', res.data.object.email)
          commit('setPassword', res.data.object.password)

          // 로그인한 유저의 닉네임 저장 
          commit('setUsername', res.data.object.nickname)

          // 쿠키에 저장
          commit('setToken', res.headers['jwt-auth-token'])
          
          router.push('/home')
        })
        .catch(err => {
          if (err.response.status === 404) {
            router.push({ name: "PageNotFound" })
          } else {
            console.error(err)
          }
        })
    },

    socketConnect({commit, getters, state}) {
      console.log('socketconnect')
      console.log(getters.isLoggedIn)
      if (getters.isLoggedIn) {
        console.log('isLoggedin = true')
        if (state.websocket === null) {
          console.log('socket open')
          commit('setWebsocket', new WebSocket(state.wsUri))
          console.log(state.websocket)
          state.websocket.onopen = () => state.websocket.send('login,' + localStorage.getItem('username'));
        }
        if (state.websocket !== null) {
          state.websocket.onmessage = function(e){ 
            commit('setMessage')
            commit('setItems', e.data)
            console.log(e.data);
           }
        }
      }///
    },

    SignUp({ commit, }, signupData) {
      if (signupData.email.charAt(0) >= 'A' && signupData.email.charAt(0) <= 'Z') {
        signupData.email = signupData.email.substring(0, 1).toLowerCase() + signupData.email.substring(1)
      }
      if (signupData.profile !== null) {
        commit('setPicture', null)
        console.log(signupData.profile)
        console.log(signupData.profile.name)
        var extension = signupData.profile.name.split('.').reverse()[0];
        firebase.storage().ref(`user/${signupData.nickname}/${signupData.nickname}.${extension}`).put(signupData.profile)
        signupData.profile = `user/${signupData.nickname}/${signupData.nickname}.${extension}`
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
      }
      else {
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
      }
    },

    logout({ commit, state }) {
      commit('setEmail', '')
      commit('setPassword', '')

      // cookie에 있는 jwt 제거
      commit('setToken', null)
      cookies.remove('user')

      // local storage에 있는 username 정보 제거
      commit('setUsername', null)
      localStorage.removeItem('username')

      // 웹소켓 제거
      state.websocket.close()
      commit('setWebsocket', null)
      
      if (router.currentRoute.name !== 'Home') {
        router.push({ name: 'Home' })
      }
    },

    changePassword(context, passwordData) {
      axios.get(SERVER.BASE + SERVER.PWCHANGE + "?oripw=" + passwordData.oripw + "&newpw=" + passwordData.newpw)
        .then(res => {
          if (res.data.status) {
            console.log(res)
            alert("비밀번호가 변경되었습니다.")
            context.commit('setChangedPw', true)
            router.push({ name: 'Home' })
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
          console.log("getUserInfo")
          commit('setUserInfo', res.data.object)
        })
        .catch(err => console.error(err))
    },

    changeUserInfo({ getters, commit }, changedData) {
      var extension = changedData.profile.name.split('.').reverse()[0];
      firebase.storage().ref(`user/${changedData.nickname}/${changedData.nickname}.${extension}`).put(changedData.profile)
      changedData.profile = `user/${changedData.nickname}/${changedData.nickname}.${extension}`
      axios.POST(SERVER.BASE + SERVER.USERINFO, changedData, getters.headersConfig)
        .then(res => {
          commit('setUser', res.data)
          alert('회원 정보가 변경되었습니다.')
        })
        .catch(err => console.error(err))
    },


    // follow 
    follow({ getters, state, dispatch }, following) {
      axios.post(SERVER.BASE + SERVER.FOLLOWING, following, getters.headersConfig)
        .then(() => {
          // console.log(res.data)
          dispatch('fetchFollowers', following.following)
          dispatch('checkFollowing', following.following)
          state.websocket.send('follow,'+state.username+','+following.following)
        })
        .catch(err => console.error(err))
    },

    unfollow({ getters, dispatch }, unfollow) {
      axios.delete(SERVER.BASE + SERVER.UNFOLLOW + unfollow, getters.headersConfig)
        .then(() => {
          // console.log(res.data)
          dispatch('fetchFollowers', unfollow)
          dispatch('checkFollowing', unfollow)
        })
        .catch(err => console.error(err))

    },

    checkFollowing({ commit, getters }, following) {
      axios.get(SERVER.BASE + SERVER.ISFOLLOWING + following, getters.headersConfig)
        .then(res => {
          // console.log(res.data.object)
          commit('setIsFollowing', res.data.object)
        })
        .catch(err => console.error(err))
    },

    fetchFollowers({ commit, getters }, username) {
      axios.get(SERVER.BASE + SERVER.FOLLOWER + `/${username}`, getters.headersConfig)
        .then(res => commit('setFollowerList', res.data.object))
        .catch(err => console.error(err))
    },  

    fetchFollowings({ commit, getters }, username) {
      axios.get(SERVER.BASE + SERVER.FOLLOWING + `/${username}`, getters.headersConfig)
        .then(res => commit('setFollowingList', res.data.object))
        .catch(err => console.error(err))
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

    getProject({ commit, getters }, gameId) {
      commit('setProject', null)
      axios.get(SERVER.BASE + SERVER.GAME + `/${gameId}`, getters.headersConfig)
      .then(res => {
        const storageRef = firebase.storage().ref()
        storageRef.child(`game/15/content/15`).getDownloadURL().then(url => {
          var xhr = new XMLHttpRequest();
          // console.log(xhr)
          // xhr.responseType = 'blob';
          if (xhr) {
            // console.log('1')
            xhr.open('GET', url, false)
            xhr.send();
            var result = (xhr.response)
            // commit('setContent', 'aaa')
            // console.log(state.project.content)
          }
          // console.log('send', xhr)
          // console.log(res.data.object)
          res.data.object.content = result
          commit('setProject', res.data.object)
        }).catch(function(error) {
          console.log(error)
        })
      })
      .catch(err => console.error(err))
    },

    fetchMyProjects() {},

    fetchFundedProjects({ commit, getters }, username) {
      axios.get(SERVER.BASE + SERVER.FUNDEDPROJECT + username, getters.headersConfig)
        .then(res => {
          console.log(res.data)
          commit('setFundedProjectList', res.data.object)
        })
        .catch(err => console.log(err))
    },

    // like
    fetchLikedProjects({ commit }, username) {
      axios.get(SERVER.BASE + SERVER.LIKEDPROJECT + `/${username}`)
        .then(res => commit('setLikedProjectList', res.data.object))
        .catch(err => console.error(err))
    },

    fetchLikedUsers({ commit }, gameId) {
      axios.get(SERVER.BASE + SERVER.LIKEBYGAME + gameId)
      .then((res) => commit('setLikedUserList', res.data.object))
      .catch((err) => console.error(err));  
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


    // search
    search({ commit }, searchKeyword) {
      axios.get(SERVER.BASE + SERVER.SEARCH + `${searchKeyword}`)
      .then(res => {
        commit('setSearchResult', res.data.object)
        router.push(`/search/${searchKeyword}`)
      })
      .catch(err => console.error(err))
    },  
  },
  modules: {
  }
})
