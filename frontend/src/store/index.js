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
    genres: ["액션", "슈팅", "RPG", "시뮬레이션", "어드벤쳐", "스포츠", "레이싱", "추리", "퍼즐", "리듬", "턴제", "캐주얼", "디펜스", "모바일", "PC", "콘솔"],
    genreToId: {
      "액션": 1, 
      "슈팅": 2, 
      "RPG": 3, 
      "시뮬레이션": 4, 
      "어드벤쳐": 5, 
      "스포츠": 6, 
      "레이싱": 7, 
      "추리": 8, 
      "퍼즐": 9, 
      "리듬": 10, 
      "턴제": 11, 
      "캐주얼": 12, 
      "디펜스": 13, 
      "모바일": 14, 
      "PC": 15, 
      "콘솔": 16
    },
    // idToGenre: {
    //   1: "액션", 
    //   2: "슈팅", 
    //   3: "RPG", 
    //   4: "시뮬레이션", 
    //   5: "어드벤쳐", 
    //   6: "스포츠", 
    //   7: "레이싱", 
    //   8: "추리", 
    //   9: "퍼즐", 
    //   10: "리듬", 
    //   11: "턴제", 
    //   12: "캐주얼", 
    //   13: "디펜스", 
    //   14: "모바일", 
    //   15: "PC", 
    //   16: "콘솔"
    // },

    // home
    mostLikedList: [],
    almostFinishedList: [],
    mostFundedList: [],
    highestPercentList: [],

    // recommedation
    genreRecommendationList: [],
    userRecommendationList: [],
              
    // follow
    followerList: null,
    followingList: null,
    isFollowing: false,
    
    // community
    articleList: [],
    article: null,
    articleCount: null,
    
    // project
    projectList: [],
    project: null,
    rewardData: null,

    // like
    likedUserList: [],

    // error
    errorDetail: null,

    // search
    searchUser: null,
    searchGame: null,
    searchCommunity: null,
  },

  getters: {
    // auth
    headersConfig: state => ({
      headers: {
        "jwt-auth-token": state.jwtToken 
      }
    }),
    isAdmin: state => state.username === 'admin',

    // user
    isLoggedIn: state => !!state.jwtToken,
    userDataFetched: state => !!state.userInfo,

    // project
    projectDataFetched: state => !!state.project,
    likedPeopleCount: state => state.likedUserList.length,
    rewardDataFetched: state => !!state.rewardData,

    // community
    articleDataFetched: state => !!state.article,
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

    // home
    setMostLikedList(state, val) {
      state.mostLikedList = val
    },
    setAlmostFinishedList(state, val) {
      state.almostFinishedList = val
    },
    setMostFundedList(state, val) {
      state.mostFundedList = val
    },
    setHighestPercentList(state, val) {
      state.highestPercentList = val
    },

    // recommendation
    setGenreRecommendationList(state, val) {
      state.genreRecommendationList = val
    },
    setUserRecommendationList(state, val) {
      state.userRecommendationList = val
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
    setArticle(state, val) {
      state.article = val
    },
    setArticleCount(state, val) {
      state.articleCount = val
    },

    // project
    setProjectList(state, val) {
      state.projectList = val
    },
    setProject(state, val) {
      state.project = val
    },
    setLikedUserList(state, val) {
      state.likedUserList = val
    },
    setContent(state, val) {
      state.project.content = val
    },
    setRewardData(state, val) {
      state.rewardData = val
    },

    // search
    setSearchUser(state, val) {
      state.searchUser = val
    },

    setSearchGame(state, val) {
      state.searchGame = val
    },

    setSearchCommunity(state, val) {
      state.searchCommunity = val
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
          if (res.data.status) {
          commit('setEmail', res.data.object.email)
          commit('setPassword', res.data.object.password)

          // 로그인한 유저의 닉네임 저장 
          commit('setUsername', res.data.object.nickname)

          // 쿠키에 저장
          commit('setToken', res.headers['jwt-auth-token'])
          
          router.push({ name: 'Home' })
        } else {
          alert(res.data.data)
        }
      })
        .catch(() => {})
    },

    SignUp({ commit, state }, signupData) {
      if (signupData.email.charAt(0) >= 'A' && signupData.email.charAt(0) <= 'Z') {
        signupData.email = signupData.email.substring(0, 1).toLowerCase() + signupData.email.substring(1)
      }
      if (signupData.profile !== null) {
        commit('setPicture', null)
        // console.log(signupData.profile)
        // console.log(signupData.profile.name)
        var extension = signupData.profile.name.split('.').reverse()[0];
        firebase.storage().ref(`user/${signupData.nickname}/${signupData.nickname}.${extension}`).put(signupData.profile)
        signupData.profile = `user/${signupData.nickname}/${signupData.nickname}.${extension}`
      }
      let genreArray = []
      signupData.genreId.forEach(item => {
        genreArray.push(state.genreToId[item])
      })
      signupData.genreId = genreArray
      axios.post(SERVER.BASE + SERVER.SIGNUP, signupData)
      .then(res => {
        console.log(res)
        if (res.data.status) {
          alert("회원가입이 완료되었습니다.")
          router.push({ name: "Login" });
        } else {
          alert(res.data.data)
        }
        })
        .catch(() => {})
    },

    logout({ commit}) {
      commit('setEmail', '')
      commit('setPassword', '')

      // cookie에 있는 jwt 제거
      commit('setToken', null)
      cookies.remove('user')

      // local storage에 있는 username 정보 제거
      commit('setUsername', null)
      localStorage.removeItem('username')
      
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
          // console.log("getUserInfo")
          commit('setUserInfo', res.data.object)
        })
        .catch(err => console.error(err))
    },

    changeUserInfo({ getters, state }, changedData) {
      if (changedData.profile !== null) {
        var extension = changedData.profile.name.split('.').reverse()[0];
        firebase.storage().ref(`user/${changedData.nickname}/${changedData.nickname}.${extension}`).put(changedData.profile)
        changedData.profile = `user/${changedData.nickname}/${changedData.nickname}.${extension}`
      } else {
        console.log(changedData.profile)
        changedData.profile = changedData.profileURL
      }
      if (changedData.genreId !== null) {
        let genreArray = []
        changedData.genreId.forEach(item => {
          genreArray.push(state.genreToId[item])
        })
        changedData.genreId = genreArray
      }
      axios.put(SERVER.BASE + SERVER.USERINFO, changedData, getters.headersConfig)
        .then(() => {
          alert('회원 정보가 변경되었습니다.')
          router.push({name: "MyPage", params: {username: changedData.nickname}})
        })
        .catch(err => console.error(err))
    },

    // home
    fetchHomeData({ commit, getters }) {
      const storageRef = firebase.storage().ref()

      axios.get(SERVER.BASE + SERVER.MOSTLIKED)
        .then(res => {
          res.data.object.forEach(item => {
            storageRef.child(item.thumbnail).getDownloadURL()
              .then(url => item.thumbnail = url)
              .catch(err => console.error(err))
            let genres = ''
            item.genreName.forEach(genre => {
              genres += genre + ' | '
            })
            item.genreName = genres.slice(0, genres.length-2)
          })
          commit('setMostLikedList', res.data.object)
        })
        .catch(err => console.error(err))
      
      axios.get(SERVER.BASE + SERVER.ALMOSTFINISHED)
        .then(res => {
          res.data.object.forEach(item => {
            storageRef.child(item.thumbnail).getDownloadURL()
              .then(url => item.thumbnail = url)
              .catch(err => console.error(err))
            let genres = ''
            item.genreName.forEach(genre => {
              genres += genre + ' | '
            })
            item.genreName = genres.slice(0, genres.length-2)
          })
          commit('setAlmostFinishedList', res.data.object)
        })
        .catch(err => console.error(err))

      axios.get(SERVER.BASE + SERVER.MOSTFUNDED)
        .then(res => {
          res.data.object.forEach(item => {
            storageRef.child(item.thumbnail).getDownloadURL()
              .then(url => item.thumbnail = url)
              .catch(err => console.error(err))
            let genres = ''
            item.genreName.forEach(genre => {
              genres += genre + ' | '
            })
            item.genreName = genres.slice(0, genres.length-2)
          })
          commit('setMostFundedList', res.data.object)
        })
        .catch(err => console.error(err))

      axios.get(SERVER.BASE + SERVER.HIGHESTPERCENT)
        .then(res => {
          res.data.object.forEach(item => {
            storageRef.child(item.thumbnail).getDownloadURL()
              .then(url => item.thumbnail = url)
              .catch(err => console.error(err))
            let genres = ''
            item.genreName.forEach(genre => {
              genres += genre + ' | '
            })
            item.genreName = genres.slice(0, genres.length-2)
          })
          commit('setHighestPercentList', res.data.object)
        })
        .catch(err => console.error(err))
      
      if (getters.isLoggedIn) {
        axios.get(SERVER.BASE + SERVER.GENREBASEDRECOMMENDATION, getters.headersConfig)
          .then(res => {
            res.data.object.forEach(item => {
              storageRef.child(item.thumbnail).getDownloadURL()
                .then(url => item.thumbnail = url)
                .catch(err => console.error(err))
              let genres = ''
              item.genreName.forEach(genre => {
                genres += genre + ' | '
              })
              item.genreName = genres.slice(0, genres.length-2)
            })
            commit('setGenreRecommendationList', res.data.object)
          })
          .catch(err => console.error(err))

        axios.get(SERVER.BASE + SERVER.USERBASEDRECOMMENDATION, getters.headersConfig)
          .then(res => {
            res.data.object.forEach(item => {
              storageRef.child(item.thumbnail).getDownloadURL()
                .then(url => item.thumbnail = url)
                .catch(err => console.error(err))
              let genres = ''
              item.genreName.forEach(genre => {
                genres += genre + ' | '
              })
              item.genreName = genres.slice(0, genres.length-2)
            })
            commit('setUserRecommendationList', res.data.object)
          })
          .catch(err => console.error(err))
      }
    },

    // follow 
    follow({ getters, dispatch }, following) {
      if (getters.isLoggedIn) {
      axios.post(SERVER.BASE + SERVER.FOLLOWING, following, getters.headersConfig)
        .then(() => {
          // console.log(res.data)
          dispatch('fetchFollowers', following.following)
          dispatch('checkFollowing', following.following)
        })
        .catch(err => console.error(err))
      } else {
        alert("로그인을 해야 팔로우를 할 수 있습니다.")
      }
    },

    unfollow({ getters, dispatch }, unfollow) {
      if (getters.isLoggedIn) {
      axios.delete(SERVER.BASE + SERVER.UNFOLLOW + unfollow, getters.headersConfig)
        .then(() => {
          // console.log(res.data)
          dispatch('fetchFollowers', unfollow)
          dispatch('checkFollowing', unfollow)
        })
        .catch(err => console.error(err))
      } else {
        alert("로그인을 해야 언팔로우를 할 수 있습니다.")
      }
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
          storageRef.child(res.data.object.content).getDownloadURL()
            .then(url => {
              var xhr = new XMLHttpRequest()
              if (xhr) {
                xhr.open('GET', url, false)
                xhr.send()
                var result = (xhr.response)
              }
              res.data.object.content = result
              storageRef.child(res.data.object.thumbnail).getDownloadURL()
                .then(url => {
                  res.data.object.thumbnail = url
                  commit('setProject', res.data.object)
                })
                .catch(() => res.data.object.thumbnail = null)
            })
            .catch(function(error) {
              console.log(error)
            })
        })
        .catch(err => console.error(err))
    },
    
    getReward({ commit }, rewardId) {
      commit('setRewardData', null)
      axios.get(SERVER.BASE + SERVER.REWARDDETAIL + rewardId, this.headersConfig)
        .then(res => {
          // console.log(res.data.object)
          commit('setRewardData', res.data.object)
        })
        .catch(err => console.error(err));
    },

    // like
    fetchLikedUsers({ commit }, gameId) {
      axios.get(SERVER.BASE + SERVER.LIKEBYGAME + gameId)
      .then((res) => {
        if (res.data.object.length > 0) {
          const storageRef = firebase.storage().ref()
          res.data.object.forEach(item => {
            if (item.profile !== null) {
              storageRef.child(item.profile).getDownloadURL()
                .then(url => item.profile = url)
                .catch(err => console.error(err))
            }
          })
        }
        commit('setLikedUserList', res.data.object)
      })
      .catch((err) => console.error(err));  
    },


    // community
    fetchArticles({ commit }, page) {
      axios.get(SERVER.BASE + SERVER.COMMUNITYLIST + page)
        .then(res =>{
          commit('setArticleList', res.data.object.list)
          commit('setArticleCount', res.data.object.count)
        })
        .catch(err => console.error(err))
    },

    getArticle({ commit }, communityId) {
      commit('setArticle', null)
      axios.get(SERVER.BASE + SERVER.COMMUNITY + `/${communityId}`)
        .then(res => {
          res.data.object.content = res.data.object.content.replace(/(?:\r\n|\r|\n)/g, '<br />')
          commit('setArticle', res.data.object)
        })
        .catch(err => console.error(err))
    },

    createArticle({ getters }, articleData) {
      axios.post(SERVER.BASE + SERVER.COMMUNITY, articleData, getters.headersConfig)
        .then(() => router.push({ name: 'CommunityMain' }))
        .catch(err => console.error(err))
    },

    editArticle(context, articleData) {
      axios.put(SERVER.BASE + SERVER.COMMUNITY, articleData)
        .then(() => router.push(`/community/${articleData.communityId}`))
        .catch(err => console.log(err))
    },

    deleteArticle(context, communityId) {
      axios.delete(SERVER.BASE + SERVER.COMMUNITY + `/${communityId}`)
        .then(() => router.push(`/community`))
        .catch(err => console.error(err))
    },


    // search
    search({ commit }, searchKeyword) {
      // user
      axios.get(SERVER.BASE + SERVER.SEARCH + `${searchKeyword}`)
      .then(res => {
        const storageRef = firebase.storage().ref()
        if (res.data.object.user.length > 0){
          res.data.object.user.forEach(item => {
            if (item.profile !== null) {
            storageRef.child(item.profile).getDownloadURL()
            .then(url => {
              item.profile = url
            })
            .catch(() => item.profile = null)
           }
          })
        }

        if (res.data.object.game.length > 0){
          res.data.object.game.forEach(item => {
            if (item.thumbnail !== null) {
            storageRef.child(item.thumbnail).getDownloadURL()
            .then(url => {
              item.thumbnail = url
            })
            .catch(() => item.thumbnail = null)
           }
           if (item.profile !== null) {
            storageRef.child(item.profile).getDownloadURL()
            .then(url => {
              item.profile = url
            })
            .catch(() => item.profile = null)
           }
           let genres = ''
           item.genreName.forEach(genre => {
             genres += genre + ' | '
           })
           item.genreName = genres.slice(0, genres.length-2)
          })
        }
        commit('setSearchUser', res.data.object.user)
        commit('setSearchGame', res.data.object.game)
        commit('setSearchCommunity', res.data.object.community)
        router.push(`/search/${searchKeyword}`).catch(()=>{})
      })
    },  
  },
  modules: {
  }
})
