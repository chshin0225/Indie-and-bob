<template>
  <v-app class="App">
    <div>
      <!-- navbar -->
      <v-app-bar color="primary" dense flat fixed>
        <!-- 햄버거 메뉴 -->
        <v-app-bar-nav-icon class="white--text" @click="drawer = true"></v-app-bar-nav-icon>

        <!-- 로고 -->
        <v-toolbar-title class="pl-1 pt-4">
          <router-link :to="{ name: 'Home' }" class="white--text text-decoration-none">
            <v-img src="./assets/logo_horizontal2.png" max-width="170px"></v-img>
          </router-link>
        </v-toolbar-title>

        <v-spacer></v-spacer>

        <!-- search bar -->
        <v-text-field
          dark 
          color="white" 
          class="mt-4 search-input d-none d-sm-flex" 
          placeholder="검색" 
          v-model="searchKeyword" 
          @keypress.enter="sendSearch(searchKeyword)"
        ></v-text-field>
        <v-btn icon @click="sendSearch(searchKeyword)" class="d-none d-sm-flex">
          <i class="fas fa-search white--text"></i>
        </v-btn>

        <!-- <v-btn icon @click="sendSearch(searchKeyword)" class="d-flex d-sm-none">
          <i class="fas fa-search white--text"></i>
        </v-btn> -->

        <v-menu offset-y nudge-bottom="5px" :close-on-content-click="false" v-model="searchMenu">
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon class="d-flex d-sm-none" v-bind="attrs" v-on="on">
              <i class="fas fa-search white--text"></i>
            </v-btn>
          </template>
          <v-card width="200px">
            <v-card-text class="d-flex pl-3 pr-1 pt-0">
              <v-text-field
                label="검색"
                v-model="searchKeyword"
                hide-details="true"
                @keypress.enter="sendSearch(searchKeyword)"
              ></v-text-field>
              <v-btn icon @click="sendSearch(searchKeyword)" class="align-self-end pt-2">
                <i class="fas fa-search black--text"></i>
              </v-btn>
            </v-card-text>
          </v-card>
        </v-menu>







        <!-- notifications(login했을 때만) -->
        <!-- <v-menu v-if="isLoggedIn" transition="slide-y-transition" :close-on-click="closeOnClick" nudge-bottom=50 bottom left>
          <template v-slot:activator="{ on, attrs }">
            <v-btn v-if="message===0" icon v-bind="attrs" v-on="on"><i class="fas fa-bell white--text"></i></v-btn>
            <v-btn v-else icon v-bind="attrs" v-on="on"><i class="fas fa-bell red--text"></i></v-btn>
          </template>
          <v-list>
            <v-list-item v-for="(item, i) in items" :key="i">
              <v-list-item-title>{{ item }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu> -->
      </v-app-bar>

      <!-- nav drawer -->
      <v-navigation-drawer v-model="drawer" absolute temporary class="nav-drawer">
        <!-- 현 유저 표시 -->
        <template v-slot:prepend>
          <!-- 로그인 안했을 때 -->
          <router-link :to="{ name: 'Login' }" class="text-decoration-none black--text" v-if="!isLoggedIn">
            <div class="pa-2 d-flex">
              <v-avatar color="secondary">
                <v-icon dark>mdi-account-circle</v-icon>
              </v-avatar>
              <h3 class="ml-4 align-self-center">로그인해주세요</h3>
            </div>
          </router-link>

          <!-- 로그인 했을 때 -->
          <router-link :to="`/user/mypage/${ userInfo.nickname }`" class="text-decoration-none black--text" v-if="isLoggedIn && dataFetched">
            <div class="pa-2 d-flex">
              <v-avatar>
                <v-img v-if="userInfo.profile" :src="userInfo.profile" :alt="userInfo.nickname"></v-img>
                <v-img v-else src="./assets/default_profile.png" :alt="userInfo.nickname"></v-img>
              </v-avatar>
              <h3 class="ml-4 align-self-center">{{ userInfo.nickname }}</h3>
            </div>
          </router-link>
        </template>

        <!-- 네비게이션 -->
        <v-list nav dense>
          <v-list-item-group active-class="primary--text">
            <v-list-item class="px-3" to="/">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-home fa-lg grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">홈</v-col>
              </v-row>
            </v-list-item>

            <v-list-item class="px-3" :to="`/user/mypage/${ userInfo.nickname }`" v-if="isLoggedIn && dataFetched">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-user fa-lg grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">마이 페이지</v-col>
              </v-row>
            </v-list-item>

            <v-list-item class="px-3" to="/projects">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-gamepad fa-lg grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">모든 프로젝트 보기</v-col>
              </v-row>
            </v-list-item>

            <v-list-item class="px-3" to="/community">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-comment-alt fa-lg grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">커뮤니티</v-col>
              </v-row>
            </v-list-item>

            <v-list-item class="px-3" to="/newrequest" v-if="isLoggedIn && isAdmin">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-clipboard-check fa-lg ml-1 grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">프로젝트 심사</v-col>
              </v-row>
            </v-list-item>

          </v-list-item-group>
        </v-list>

        <!-- logout/login btn -->
        <template v-slot:append>
          <!-- 로그인 했을 때 -->
          <div class="pa-2" v-if="isLoggedIn">
            <v-btn block color="secondary black--text" @click="logout">로그아웃</v-btn>
          </div>
          <!-- 로그인 안했을 때 -->
          <div class="pa-2" v-if="!isLoggedIn">
            <v-btn block color="accent white--text" :to="{ name: 'Login' }">로그인</v-btn>
          </div>
        </template>
      </v-navigation-drawer>
    </div>

    <!-- router view -->
    <div class="main-content">
      <router-view></router-view>
    </div>
  </v-app>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from "vuex"
import axios from 'axios'
import SERVER from './api/base'
import firebase from 'firebase'
import router from './router'
import cookies from 'vue-cookies'

export default {
  name: "app",

  data() {
    return {
      userInfo: null,
      drawer: false,
      searchKeyword: '',
      closeOnClick: true,
      currentUser: cookies.get('username'),
      searchMenu: false,
    };
  },
  
  methods: {
    ...mapActions(['goBack', 'logout', 'search']),
    ...mapMutations(['setIsDeveloper']),

    getUserInfo() {
      this.userInfo = null
      if (this.isLoggedIn) {
        let username = cookies.get('username')
        axios.get(SERVER.BASE + SERVER.USERINFO + `/${username}`)
          .then(res => {
            this.userInfo = res.data.object
            this.setIsDeveloper(this.userInfo.developer)
            if (this.userInfo !== null) {
              const storageRef = firebase.storage().ref()
              if (this.userInfo.profile !== null) {
                storageRef.child(this.userInfo.profile).getDownloadURL()
                  .then(url => {
                    this.userInfo.profile = url
                  })
                  .catch(err => console.error(err))
              }
            }
          })
          .catch(err => console.error(err))
      }
    },

    sendSearch(searchKeyword) {
      if (this.searchKeyword.trim().length > 0) {
        this.searchMenu = false
        router.push({name:"SearchResult", params: {keyword: searchKeyword}})
        this.searchKeyword = ''
      } 
    },
  },

  created() {
    this.getUserInfo()
  },

  watch: {
    $route: function() {
      this.getUserInfo()
    },  
  },

  computed: {
    ...mapGetters(['isLoggedIn', 'isAdmin',]),
    dataFetched: function() {
      return !!this.userInfo
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: 'IBMPlexSansKR-Regular';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/IBMPlexSansKR-Regular.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'PFStardust';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/PFStardust.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
            
.App {
  /* font-family: 'Nanum Gothic', sans-serif; */
  font-family: 'IBMPlexSansKR-Regular';
}

.search-input {
  max-width: 300px;
}

.fa-user {
  margin-left: 3px;
}

.fa-comment-alt {
  margin-left: 2px;
}

.main-content {
  margin-top: 48px;
}

.nav-drawer {
  position: fixed;
  height: 100vh;
}
</style>
