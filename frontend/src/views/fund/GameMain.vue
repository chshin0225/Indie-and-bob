<template>
  <div>
    <div class="header d-flex justify-center align-center">
      <h1 class="text-center white--text header-text">모든 프로젝트</h1>
    </div>

    <v-container class="ProjectMain">
      <!-- projects  -->
      <v-row>
        <!-- <p>{{games[12]}}</p> -->
        <v-col v-for="game in games" :key="game.gameId" cols=12 sm=6 md=4>
          <v-card tile class="card">
            <router-link :to="`/game/${game.gameId}`" class="text-decoration-none">
                <v-list-item>
                  <v-avatar>
                    <v-img v-if="game.profile" :src="game.profile" :alt="game.nickname"></v-img>
                    <v-img v-else src="../../assets/default_profile.png"></v-img>
                  </v-avatar>
                  <v-list-item-content class="ml-4">
                    <v-list-item-title class="font-weight-bold game-name">{{ game.name }}</v-list-item-title>
                    <router-link class="text-decoration-none" :to="`/user/mypage/${game.nickname}`">{{ game.nickname }}</router-link>
                    <v-list-item-subtitle v-if="!game.end" class="d-none d-sm-block">{{ game.leftDay }}일 남음</v-list-item-subtitle>
                    <v-list-item-subtitle v-else class="d-none d-sm-block">{{ $moment(game.deadline).format('YYYY.MM.DD') }}까지</v-list-item-subtitle>
                    <v-list-item-subtitle>{{ game.genreName }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
                <v-img v-if="game.thumbnail" :src="game.thumbnail" height="194"></v-img>
                <v-img v-else src="../../assets/default_project.png" height="194"></v-img>
                
                <v-list-item class="py-1 mx-1">
                  <!-- 진행중 -->
                  <v-row v-if="!game.end">
                    <p class="mb-1 ml-1 funding-progress">{{ fundingProgress(game.aim, game.leftPrice) }}% 달성</p>
                    <v-progress-linear :value="fundingProgress(game.aim, game.leftPrice)" height="7"></v-progress-linear>
                  </v-row>

                  <!-- 종료됨 -->
                  <v-row v-else>
                    <v-chip
                      class="ma-2"
                      color="primary"
                    >
                      종료됨
                    </v-chip>
                  </v-row>
                </v-list-item>
            </router-link>
          </v-card>
        </v-col>
      </v-row>

      <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
        <div slot="no-more"></div>
        <div slot="no-results">프로젝트가 없네요!</div>
      </infinite-loading>
    </v-container>
  </div>
</template>
<script>
import axios from "axios";
import SERVER from "../../api/base";

import InfiniteLoading from "vue-infinite-loading";
import firebase from "firebase"
import _ from 'lodash'

export default {
  name: "GameMain",

  components: {
    InfiniteLoading
  },

  data() {
    return {
      gameNum: 0,
      games: [],
    }
  },

  computed: {
    fundingProgress() {
      return (aim, leftPrice) => {
        if (aim === leftPrice) {
          return 0
        } else {
          return _.round((aim - leftPrice) / aim * 100)
        }
      }
    },
  },

  methods: {
    infiniteHandler($state) {
      const storageRef = firebase.storage().ref()
      axios.get(SERVER.BASE + SERVER.GAMELIST + this.gameNum + "/")
        .then(res => {
          if (res.data.object.length > 0) {
            this.gameNum += 10;
            // console.log(res.data);
            res.data.object.forEach(item => {
              if (item.profile !== null) {
                storageRef.child(item.profile).getDownloadURL().then(url => {
                  item.profile = url
                })
                .catch(() => item.profile = null)
              }
              if (item.thumbnail !== null) {
                storageRef.child(item.thumbnail).getDownloadURL().then(url => {
                  item.thumbnail = url
                })
                .catch(() => item.thumbnail = null)
              } 
              this.games.push(item);
              
              let genres = ''
              item.genreName.forEach(genre => {
                genres += genre + ' | '
              })
              item.genreName = genres.slice(0, genres.length-2)
            });
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

<style scoped>    
.header {
  height: 200px;
  background-image: url("../../assets/mypage_img2.jpg");
  background-position: center center;
  background-size: cover;
}

.header-text {
  font-size: 40px;
}

.game-name {
  font-size: 18px;
}

</style>