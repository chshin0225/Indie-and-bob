<template>
  <v-app>
    <div>
      <v-app-bar color="secondary" dense flat>
        <!-- 모바일 화면에서만 햄버거 메뉴 사용 가능-->
        <v-app-bar-nav-icon class="d-md-none white--text"></v-app-bar-nav-icon>

        <!-- 로고 -->
        <v-toolbar-title>
          <router-link to="/feed/main" class="white--text text-decoration-none">Indie and Bob</router-link>
        </v-toolbar-title>

        <v-spacer></v-spacer>

        <!-- search bar -->
        <v-text-field
          dark
          color="white"
          class="mt-4"
          placeholder="Search"
        ></v-text-field>
        <v-btn icon>
          <i class="fas fa-search white--text"></i>
        </v-btn>

        <!-- <v-menu left bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon v-bind="attrs" v-on="on">
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>

          <v-list>
            <v-list-item v-for="n in 5" :key="n" @click="() => {}">
              <v-list-item-title>Option {{ n }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu> -->

        <v-btn @click="goBack">
          go back
        </v-btn>

      </v-app-bar>
    </div>

    <router-view></router-view>
  </v-app>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: "app",

  methods: {
    ...mapActions(['goBack',])
  },
};
</script>
