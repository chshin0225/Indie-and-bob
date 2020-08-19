<template>
  <v-container class="pa-0">
    <div class="d-flex recommendations-list">
        <v-col class="mx-1 px-0 pt-0" v-for="project in projectList" :key="project.gameId">
          <router-link :to="`/game/${project.gameId}`" class="text-decoration-none">
            <v-card width="200px" flat>
              <v-img :src="project.thumbnail"  height="150px"></v-img>
              <h4 class="px-1 mt-2">{{ project.name }}</h4>
              <v-card-subtitle class="py-0 px-1">{{ project.genreName }}</v-card-subtitle>
              <v-card-subtitle class="py-0 px-1">{{ project.leftDay }}일 남음</v-card-subtitle>
              <v-card-subtitle class="py-0 px-1 primary--text">{{ fundingProgress(project.aim, project.leftPrice) }}% 달성</v-card-subtitle>
            </v-card>
          </router-link>
        </v-col>
    </div>
  </v-container>

  <!-- <v-sheet>
    <v-slide-group class="recommendations-list" show-arrows>
        <v-slide-item class="mx-1" v-for="project in projectList" :key="project.gameId">
          <router-link :to="`/game/${project.gameId}`" class="text-decoration-none">
            <v-card width="200px" flat>
              <v-img :src="project.thumbnail"  height="150px"></v-img>
              <h4 class="px-1 mt-2">{{ project.name }}</h4>
              <v-card-subtitle class="py-0 px-1">{{ project.genreName }}</v-card-subtitle>
              <v-card-subtitle class="py-0 px-1">{{ project.leftDay }}일 남음</v-card-subtitle>
              <v-card-subtitle class="py-0 px-1 primary--text">{{ fundingProgress(project.aim, project.leftPrice) }}% 달성</v-card-subtitle>
            </v-card>
          </router-link>
        </v-slide-item>
    </v-slide-group>
  </v-sheet> -->
</template>

<script>
import _ from 'lodash'

export default {
  name: 'Recommendations',

  props: ['projectList',],

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
}
</script>

<style scoped>
  .recommendations-list {
    overflow-x: scroll;
    overflow-y: hidden;
    white-space: nowrap;
  }
</style>