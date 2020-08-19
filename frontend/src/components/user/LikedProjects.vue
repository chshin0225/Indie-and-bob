<template>
  <v-container>
    <!-- <h2 class="text-center">내가 좋아요한 프로젝트들</h2> -->
    <!-- <p>{{ likedProjectList }}</p> -->

    <v-row>
      <v-col v-for="project in likedProjectList" :key="project.gameId" cols=6 md=4>
        <v-card tile class="card">
          <router-link :to="`/game/${project.gameId}`" class="text-decoration-none">
              <v-list-item>
                <v-avatar>
                  <v-img v-if="project.profile" :src="project.profile" :alt="project.nickname"></v-img>
                  <v-img v-else src="../../assets/default_profile.png"></v-img>
                </v-avatar>
                <v-list-item-content class="ml-4">
                  <v-list-item-title class="font-weight-bold project-name">{{ project.name }}</v-list-item-title>
                  <router-link class="text-decoration-none" :to="`/user/mypage/${project.nickname}`">{{ project.nickname }}</router-link>
                  <v-list-item-subtitle v-if="!project.end" class="d-none d-sm-block">{{ project.leftDay }}일 남음</v-list-item-subtitle>
                  <v-list-item-subtitle v-else class="d-none d-sm-block">{{ $moment(project.deadline).format('YYYY.MM.DD') }}까지</v-list-item-subtitle>
                  <v-list-item-subtitle>{{ project.genreName }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
              <v-img v-if="project.thumbnail" :src="project.thumbnail" height="194"></v-img>
              <v-img v-else src="../../assets/default_project.png" height="194"></v-img>
              
              <v-list-item class="py-1 mx-1">
                <v-row v-if="!project.end">
                  <p class="mb-1 ml-1 funding-progress">{{ fundingProgress(project.aim, project.leftPrice) }}% 달성</p>
                  <v-progress-linear :value="fundingProgress(project.aim, project.leftPrice)" height="7"></v-progress-linear>
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

    <infinite-loading @infinite="fetchLikedProjects" spinner="waveDots">
      <div slot="no-more"></div>
      <div slot="no-results">아직 좋아요한 프로젝트가 없네요!</div>
    </infinite-loading>
  </v-container>
</template>

<script>
import SERVER from '../../api/base'

import axios from 'axios'
import firebase from 'firebase'
import InfiniteLoading from 'vue-infinite-loading'
import _ from 'lodash'

export default {
  name: 'LikedProjects',

  components: {
    InfiniteLoading,
  },

  data() {
    return {
      likedProjectList: [],
      projectNum: 0,
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
    fetchLikedProjects($state) {
      const storageRef = firebase.storage().ref()
      axios.get(SERVER.BASE + SERVER.LIKEDPROJECT + `/${this.$route.params.username}/${this.projectNum}`)
        .then(res => {
          if (res.data.object.length > 0) {
            this.projectNum += 9;
            res.data.object.forEach(item => {
              if (item.profile !== null) {
                storageRef.child(item.profile).getDownloadURL()
                  .then(url => {
                    item.profile = url
                  })
                  .catch(err => console.error(err))
              }
              if (item.thumbnail !== null) {
                storageRef.child(item.thumbnail).getDownloadURL()
                  .then(url => {
                    item.thumbnail = url
                  })
                  .catch(err => console.error(err))
              } 
              this.likedProjectList.push(item);

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
        .catch(err => console.error(err))
    },
  },
}
</script>

<style scoped>
.project-name {
  font-size: 18px;
}
</style>