<template>
  <v-app>
    <div>
      <!-- navbar -->
      <v-app-bar color="primary" dense flat>
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

        <!-- back btn -->
        <v-btn @click="goBack" color="secondary" class="black--text" depressed>back</v-btn>
      </v-app-bar>

      <v-navigation-drawer v-model="drawer" absolute temporary>
        <template v-slot:prepend>
          <div class="pa-2 d-flex">
            <v-avatar color="secondary">
              <v-icon dark>mdi-account-circle</v-icon>
            </v-avatar>
            <h3 class="ml-4 align-self-center">로그인해주세요</h3>
          </div>
        </template>
        <v-list nav dense>
          <v-list-item-group v-model="group" active-class="primary--text">
            <v-list-item class="px-3" to="/feed/main">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-home fa-lg grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">Home</v-col>
              </v-row>
            </v-list-item>

            <v-list-item class="px-3" to="/user/mypage">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-user fa-lg grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">My Page</v-col>
              </v-row>
            </v-list-item>

            <v-list-item class="px-3" to="/project/all">
              <v-row>
                <v-col cols="3">
                  <i class="fas fa-gamepad fa-lg grey--text text--darken-2"></i>
                </v-col>
                <v-col class="font-weight-regular">Browse Project</v-col>
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

        <!-- logout btn -->
        <template v-slot:append>
          <div class="pa-2">
            <v-btn block color="secondary black--text">Logout</v-btn>
          </div>
        </template>
      </v-navigation-drawer>
    </div>

    <!-- router view -->
    <router-view></router-view>
  </v-app>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "app",

  data() {
    return {
      drawer: false
    };
  },

  methods: {
    ...mapActions(["goBack"])
  }
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
</style>
