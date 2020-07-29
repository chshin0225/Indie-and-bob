<template>
  <v-container>
    <h1 class="text-center">내 팔로우 정보</h1>
    <p>followers: {{ followerList }}</p>
    <p>following: {{ followingList }}</p>

  </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: 'FollowInfo',

  computed: {
    ...mapState(['followerList', 'followingList']),
  },

  methods: {
    ...mapActions(['fetchFollowers', 'fetchFollowings',]),
  },

  created() {
    let username = this.$route.params.username
    this.fetchFollowers(username)
    this.fetchFollowings(username)
  },
}
</script>

<style>

</style>