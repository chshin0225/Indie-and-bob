<template>
  <div>
    <v-card flat>
      <template v-for="project in projectList">
        <v-list-item three-line class="px-3" :key="project.gameId" :to="`/game/${project.gameId}`">
          <v-list-item-content class="py-0">
            <v-col cols="7" class="px-0">
              <v-list-item-subtitle class="font-weight-bold black--text text-subtitle-1">{{ project.name }}</v-list-item-subtitle>
              <v-list-item-subtitle>{{ project.genreName }}</v-list-item-subtitle>
              <v-list-item-subtitle class="mt-1"> {{ $moment(project.deadline).format('YYYY.MM.DD') }}까지 <span class="primary--text">{{ fundingProgress(project.aim, project.leftPrice) }}% 달성</span></v-list-item-subtitle>
            </v-col>
            <v-col cols="5">
              <v-img max-height="70px" :src="project.thumbnail"></v-img>
            </v-col>
          </v-list-item-content>
        </v-list-item>
        <v-divider :key="project.gameId"></v-divider>
      </template>
    </v-card>
  </div>
</template>

<script>
import _ from 'lodash'

export default {
  name: 'RankedList',

  props: ['projectList'],

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

<style>

</style>