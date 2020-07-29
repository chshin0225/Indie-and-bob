<template>
  <v-container>
    <h2 class="text-center">내가 좋아요한 프로젝트들</h2>
    <p>{{ likedProjectList }}</p>
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