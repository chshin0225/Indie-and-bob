<template>
  <v-container>
    <h1>내 팔로우 정보</h1>
    <p>{{ followerList }}</p>

  </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: 'FollowInfo',

  computed: {
    ...mapState(['followerList']),
  },

  methods: {
    ...mapActions(['fetchFollowers']),
  },

  created() {
    this.fetchFollowers()
  },
}
</script>

<style>

</style>