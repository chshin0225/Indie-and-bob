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
                  <v-img v-if="game.profile" :src="game.profile" :alt="game.nickname"></v-img>
                  <v-img v-else src="../../assets/default_profile.png"></v-img>
                </v-avatar>
                <v-list-item-content class="ml-4">
                  <v-list-item-title class="headline">{{ game.name }}</v-list-item-title>
                  <router-link class="text-decoration-none" :to="`/user/mypage/${game.nickname}`">{{ game.nickname }}</router-link>
                  <v-list-item-subtitle>{{ $moment(game.deadline).format('YYYY.MM.DD') }}까지</v-list-item-subtitle>
                  <v-list-item-subtitle>{{ game.genreName }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
              <v-img v-if="game.thumbnail" :src="game.thumbnail" height="194"></v-img>
              <v-img v-else src="../../assets/default_project.png"></v-img>
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

    <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
      <div slot="no-more"></div>
      <div slot="no-results">프로젝트가 없네요!</div>
    </infinite-loading>
  </v-container>
</template>
<script>
import axios from "axios";
import SERVER from "../../api/base";

import InfiniteLoading from "vue-infinite-loading";
import firebase from "firebase"

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

  // computed: {
  //   genreData(genreName) {
  //     let genres = ''
  //     genreName.forEach(item => {
  //       genres += item + ' | '
  //     })
  //     return genres.slice(0, this.genres.length-2)
  //   },
  // },

  methods: {
    infiniteHandler($state) {
      const storageRef = firebase.storage().ref()
      axios.get(SERVER.BASE + SERVER.GAMELIST + this.gameNum + "/")
        .then(res => {
          // console.log(res);
          if (res.data.object.length > 0) {
            this.gameNum += 10;
            console.log(res.data);
            res.data.object.forEach(item => {
              console.log(item)
              if (item.profile !== null) {
                storageRef.child(item.profile).getDownloadURL().then(url => {
                  item.profile = url
                })
              }
              if (item.thumbnail !== null) {
                storageRef.child(item.thumbnail).getDownloadURL().then(url => {
                  item.thumbnail = url
                })
              } 
              this.games.push(item);
              
              let genres = ''
              item.genreName.forEach(genre => {
                genres += genre + ' | '
              })
              item.genreName = genres.slice(0, genres.length-2)
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