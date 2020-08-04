<template>
  <v-container>
    <!-- <p>{{ projectList }}</p> -->
    <h1 class="text-center mb-3">Projects</h1>

    <!-- projects  -->
    <v-row>
      <v-col v-for="game in games" :key="game.gameId" cols=6 sm=4>
        <v-card>
          <router-link :to="`/game/${game.gameId}`" class="text-decoration-none">
              <v-list-item>
                <v-avatar>
                  <img src="../../assets/default_profile.png" :alt="game.nickname" />
                </v-avatar>
                <v-list-item-content class="ml-4">
                  <v-list-item-title class="headline">{{ game.name }}</v-list-item-title>
                  <router-link class="text-decoration-none" :to="`/user/mypage/${game.nickname}`">{{ game.nickname }}</router-link>
                  <v-list-item-subtitle>{{ $moment(game.deadline).format('YYYY.MM.DD') }}까지</v-list-item-subtitle>
                  <v-list-item-subtitle>목표액: {{ game.aim }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
              <v-img src="../../assets/default_project.png" height="194"></v-img>
          </router-link>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon>
              <v-icon>mdi-heart</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>

    <infinite-loading @infinite="infiniteHandler"></infinite-loading>
  </v-container>
</template>
<script>
import axios from "axios";
import SERVER from "../../api/base";

import InfiniteLoading from "vue-infinite-loading";

export default {
  name: "GameMain",

  components: {
    InfiniteLoading
  },

  data() {
    return {
      gameNum: 0,
      games: []
    }
  },

  methods: {
    infiniteHandler($state) {
      axios.get(SERVER.BASE + SERVER.GAMELIST + this.gameNum + "/")
        .then(res => {
          // console.log(res);
          if (res.data.object.length > 0) {
            this.gameNum += 10;
            // console.log(res.data);
            res.data.object.forEach(item => {
              this.games.push(item);
            });
            // console.log("게임즈");
            // console.log(this.games);
            $state.loaded();
          } else {
            $state.complete();
          }
        })
        .catch(err => console.error(err));
    }
  }
};
</script>

<style>
</style>