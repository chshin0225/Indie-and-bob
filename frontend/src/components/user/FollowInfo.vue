<template>
  <v-container>
    <!-- <h1 class="text-center">내 팔로우 정보</h1> -->
    <!-- <p>followers: {{ followerList }}</p>
    <p>following: {{ followingList }}</p> -->
    <v-row>
      <!-- 팔로워 리스트 -->
      <v-col cols=12 sm=6>
        <h2>나를 팔로우하는 사람들</h2>
        <v-list v-if="followerList.length > 0">
          <v-list-item
            v-for="follower in followerList"
            :key="follower.nickname"
            :to="`/user/mypage/${follower.nickname}`"
          >
            <v-list-item-avatar>
              <v-img src="../../assets/default_profile.png"></v-img>
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

      <!-- 팔로잉 리스트 -->
      <v-col cols=12 sm=6>
        <h2>내가 팔로우하는 사람들</h2>
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

  data() {
    return {
      items: [
        { active: true, title: 'Jason Oner', avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg' },
        { active: true, title: 'Ranee Carlson', avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg' },
        { title: 'Cindy Baker', avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg' },
        { title: 'Ali Connors', avatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg' },
      ],
    }
  },

  computed: {
    ...mapState(['followerList', 'followingList']),
  },

  methods: {
    // ...mapActions(['fetchFollowers', 'fetchFollowings',]),
  },

  created() {
    // let username = this.$route.params.username
    // this.fetchFollowers(username)
    // this.fetchFollowings(username)
  },
}
</script>

<style>

</style>