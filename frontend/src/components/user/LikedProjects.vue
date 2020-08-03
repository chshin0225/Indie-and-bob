<template>
  <v-container>
    <!-- <h2 class="text-center">내가 좋아요한 프로젝트들</h2> -->
    <!-- <p>{{ likedProjectList }}</p> -->

    <v-row>
      <v-col v-for="project in likedProjectList" :key="project.gameId" cols=6 sm=4>
        <v-card>
          <router-link :to="`/game/${project.gameId}`" class="text-decoration-none">
              <v-list-item>
                <v-list-item-avatar color="secondary"></v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title class="headline">{{ project.name }}</v-list-item-title>
                  <v-list-item-subtitle>{{ project.nickname }}</v-list-item-subtitle>
                  <v-list-item-subtitle>{{ $moment(project.deadline).format('YYYY.MM.DD') }}까지</v-list-item-subtitle>
                  <v-list-item-subtitle>목표액: {{ project.aim }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
              <v-img src="https://cdn.vuetifyjs.com/images/cards/docks.jpg" height="194"></v-img>
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

  </v-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'LikedProjects',

  computed: {
    ...mapState(['likedProjectList',])
  },

  methods: {
    ...mapActions(['fetchLikedProjects',])
  },

  created() {
    this.fetchLikedProjects(this.$route.params.username)
  },
}
</script>

<style>

</style>