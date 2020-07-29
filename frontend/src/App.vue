<template>
  <v-app>
    <div>
      <!-- navbar -->
      <v-app-bar color="primary" dense flat fixed>
        <!-- 모바일 화면에서만 햄버거 메뉴 사용 가능-->
        <v-app-bar-nav-icon class="white--text" @click="drawer = true"></v-app-bar-nav-icon>

        <!-- 로고 -->
        <v-toolbar-title>
          <router-link to="/feed/main" class="white--text text-decoration-none">Indie and Bob</router-link>
        </v-toolbar-title>

        <v-spacer></v-spacer>

        <!-- search bar -->
        <v-text-field dark color="white" class="mt-4 search-input" placeholder="Search"></v-text-field>
        <v-btn icon>
          <i class="fas fa-search white--text"></i>
        </v-btn>

        <!-- notifications(login했을 때만) -->
        <v-menu v-if="isLoggedIn" transition="slide-y-transition" :close-on-click="closeOnClick" nudge-bottom=50 bottom left>
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon v-bind="attrs" v-on="on"><i class="fas fa-bell white--text"></i></v-btn>
          </template>
          <v-list>
            <v-list-item v-for="(item, i) in items" :key="i">
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>

        <!-- back btn -->
        <v-btn @click="goBack" color="secondary" class="black--text" depressed>back</v-btn>
      </v-app-bar>

      <!-- nav drawer -->
      <v-navigation-drawer v-model="drawer" absolute temporary class="nav-drawer">
        <!-- 현 유저 표시 -->
        <template v-slot:prepend>
          <!-- 로그인 안했을 때 -->
          <router-link to="/" class="text-decoration-none black--text" v-if="!isLoggedIn">
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
              <v-avatar color="secondary">
                <v-icon dark>mdi-account-circle</v-icon>
              </v-avatar>
              <h3 class="ml-4 align-self-center">{{ userInfo.nickname }}</h3>
            </div>
          </router-link>
        </template>

        <!-- 네비게이션 -->
        <v-list nav dense>
          <v-list-item-group active-class="primary--text">
            <v-list-item class="px-3" to="/feed/main">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-home fa-lg grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">Home</v-col>
              </v-row>
            </v-list-item>

            <v-list-item class="px-3" :to="`/user/mypage/${ userInfo.nickname }`" v-if="isLoggedIn && dataFetched">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-user fa-lg grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">My Page</v-col>
              </v-row>
            </v-list-item>

            <v-list-item class="px-3" to="/projects">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-gamepad fa-lg grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">Browse Projects</v-col>
              </v-row>
            </v-list-item>

            <v-list-item class="px-3" to="/community">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-comment-alt fa-lg grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">Community</v-col>
              </v-row>
            </v-list-item>

          </v-list-item-group>
        </v-list>

        <!-- logout/login btn -->
        <template v-slot:append>
          <!-- 로그인 했을 때 -->
          <div class="pa-2" v-if="isLoggedIn">
            <v-btn block color="secondary black--text" @click="logout">Logout</v-btn>
          </div>
          <!-- 로그인 안했을 때 -->
          <div class="pa-2" v-if="!isLoggedIn">
            <v-btn block color="accent white--text" to="/">Login</v-btn>
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
import { mapActions, mapGetters } from "vuex"
import axios from 'axios'
import SERVER from './api/base'

export default {
  name: "app",

  data() {
    return {
      userInfo: null,
      drawer: false,
      items: [
        { title: 'Notification1' },
        { title: 'Notification2' },
        { title: 'Notification3' },
        { title: 'Notification4' },
      ],
      closeOnClick: true,
      currentUser: localStorage.getItem('username'),
    };
  },

  methods: {
    ...mapActions(['goBack', 'logout',]),

    getUserInfo() {
      this.userInfo = null
      if (this.isLoggedIn) {
        let username = localStorage.getItem('username')
        axios.get(SERVER.BASE + SERVER.USERINFO + `/${username}`)
          .then(res => {
            // console.log('user',username)
            // console.log(res.data)
            this.userInfo = res.data.object
          })
          .catch(err => console.error(err))
      }
    }
  },

  created() {
    this.getUserInfo()
  },

  watch: {
    $route: function() {
      // console.log('log', this.isLoggedIn)
      this.getUserInfo()
    },
  },

  computed: {
    ...mapGetters(['isLoggedIn',]),

    dataFetched: function() {
      return !!this.userInfo
    },
  },


};
</script>

<style scoped>
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
