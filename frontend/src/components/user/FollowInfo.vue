<template>
  <v-container>
    <v-row>
      <!-- 팔로워 리스트 -->
      <v-col cols=12 sm=5>
        <h2 class="mb-3">나를 팔로우하는 사람들 {{ followerCount }}명</h2>
        <v-list v-if="followerList.length > 0">
          <v-list-item
            v-for="follower in followerList"
            :key="follower.nickname"
            :to="`/user/mypage/${follower.nickname}`"
          >
            <v-list-item-avatar>
              <v-img src="../../assets/default_profile.png" :alt="follower.nickname"></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title>{{ follower.nickname }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <div v-else class="mt-5">
          <p class="text-center">정보없음</p>
        </div>
      </v-col>

      <v-col cols=1 class="d-none d-sm-flex">
        <v-divider vertical class="secondary"></v-divider>
      </v-col>

      <!-- 팔로잉 리스트 -->
      <v-col cols=12 sm=5>
        <h2 class="mb-3">내가 팔로우하는 사람들 {{ followingCount }}명</h2>
        <v-list v-if="followingList.length > 0">
          <v-list-item
            v-for="following in followingList"
            :key="following.nickname"
            :to="`/user/mypage/${following.nickname}`"
          >
            <v-list-item-avatar>
              <v-img src="../../assets/default_profile.png"></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title>{{ following.nickname }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <div v-else class="mt-5">
          <p class="text-center">정보없음</p>
        </div>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'FollowInfo',

  computed: {
    ...mapState(['followerList', 'followingList']),

    followerCount: function() {
      return this.followerList.length;
    },

    followingCount: function() {
      return this.followingList.length;
    },
  },
}
</script>

<style>

</style>