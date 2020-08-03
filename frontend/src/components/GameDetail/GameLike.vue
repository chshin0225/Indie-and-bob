<template>
  <div>
    <v-list v-if="likedUserList.length > 0">
      <v-list-item
        v-for="likedUser in likedUserList"
        :key="likedUser.nickname"
        :to="`/user/mypage/${likedUser.nickname}`"
      >
        <v-list-item-avatar>
          <v-img src="../../assets/default_profile.png" :alt="likedUser.nickname"></v-img>
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title>{{ likedUser.nickname }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <div v-else class="mt-5">
      <p class="text-center">아직 좋아요한 사람이 없네요!</p>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'GameLike',

  computed: {
    ...mapState(['project', 'likedUserList'])
  },

  methods: {
    ...mapActions(['fetchLikedUsers',])
  },

  created() {
    this.fetchLikedUsers(this.$route.params.id)
  },
};
</script>

<style>
</style>