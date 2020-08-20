<template>
  <v-container>
    <v-row>
      <!-- 팔로워 리스트 -->
      <v-col cols=12 sm=5>
        <h2 class="mb-3">팔로워 {{ followerCount }}명</h2>
        <v-list v-if="followerList.length > 0">
          <v-list-item
            v-for="follower in followerList"
            :key="follower.nickname"
            :to="`/user/mypage/${follower.nickname}`"
          >
            <v-list-item-avatar>
              <v-img v-if="follower.profile" :src="follower.profile" :alt="follower.nickname"></v-img>
              <v-img v-else src="../../assets/default_profile.png" :alt="follower.nickname"></v-img>
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

      <v-col cols=1 class="d-none d-sm-flex pr-0">
        <v-divider vertical class="secondary"></v-divider>
      </v-col>

      <!-- 팔로잉 리스트 -->
      <v-col cols=12 sm=5 class="pl-0">
        <h2 class="mb-3">팔로잉 {{ followingCount }}명</h2>
        <v-list v-if="followingList.length > 0">
          <v-list-item
            v-for="following in followingList"
            :key="following.nickname"
            :to="`/user/mypage/${following.nickname}`"
          >
            <v-list-item-avatar>
              <v-img v-if="following.profile" :src="following.profile"></v-img>
              <v-img v-else src="../../assets/default_profile.png"></v-img>
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

import firebase from 'firebase'

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

  watch: {
    followerList: function() {
      const storageRef = firebase.storage().ref()

      if (this.followerList.length > 0) {
          this.followerList.forEach(item => {
            if (item.profile !== null) {
              storageRef.child(item.profile).getDownloadURL()
                .then(url => item.profile = url)
                .catch(err => console.error(err))
            }
          })
      }
    },

    followingList: function() {
      const storageRef = firebase.storage().ref()
      
      if (this.followingList.length > 0) {
        this.followingList.forEach(item => {
          if (item.profile !== null) {
            storageRef.child(item.profile).getDownloadURL()
              .then(url => item.profile = url)
              .catch(err => console.error(err))
          }
        })
      }
    },
  },

  created() {
    const storageRef = firebase.storage().ref()

    if (this.followerList.length > 0) {
        this.followerList.forEach(item => {
          if (item.profile !== null) {
            storageRef.child(item.profile).getDownloadURL()
              .then(url => item.profile = url)
              .catch(err => console.error(err))
          }
        })
    }

    if (this.followingList.length > 0) {
        this.followingList.forEach(item => {
          if (item.profile !== null) {
            storageRef.child(item.profile).getDownloadURL()
              .then(url => item.profile = url)
              .catch(err => console.error(err))
          }
        })
    }
  }


}
</script>

<style>

</style>