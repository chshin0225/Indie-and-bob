<template>
  <div>
    <div v-if="userDataFetched">
      <!-- header -->
      <div class="header">
        <v-container class="ml-5">
          <v-row>
            <v-avatar size=100 class="mr-5 mr-sm-9">
              <img src="../../assets/default_profile.png" :alt="userInfo.nickname" />
            </v-avatar>
            <v-col>
              <v-row>
                <h1>{{ userInfo.nickname }}</h1>
                <div class="d-flex" v-if="!isSelf">
                  <v-btn
                    outlined
                    small
                    color="primary"
                    class="align-self-center ml-3"
                    @click="follow({'following': userInfo.nickname,})"
                    v-if="!isFollowing"
                  >follow</v-btn>

                  <v-btn
                    outlined
                    small
                    color="accent"
                    class="align-self-center ml-3"
                    @click="unfollow(userInfo.nickname)"
                    v-if="isFollowing"
                  >unfollow</v-btn>
                </div>
                
              </v-row>
              <v-row>
                <p class="mb-0">introduction: {{ userInfo.introduction }}</p>
                <!-- <p class="mb-0">following: {{ this.followerCount }}</p><br>
                <p class="mb-0 pb-3">followers: {{ this.followingCount }}</p><br> -->
              </v-row>

            </v-col>

          </v-row>
        </v-container>
      </div>
      <v-divider></v-divider>

      <!-- tab menu -->
      <v-card elevation="0">
        <v-tabs vertical>
          <v-tab>
            <i class="fas fa-laptop mr-3"></i>
            내 프로젝트
          </v-tab>
          <v-tab>
            <i class="fas fa-money-check-alt mr-3"></i>
            후원한 프로젝트
          </v-tab>
          <v-tab>
            <i class="fas fa-edit mr-3"></i>
            내가 작성한 글
          </v-tab>
          <v-tab>
            <i class="fas fa-thumbs-up mr-3"></i>
            좋아요 한 프로젝트
          </v-tab>
          <v-tab>
            <i class="fas fa-users mr-3"></i>
            팔로우 정보
          </v-tab>
          <v-tab v-if="isSelf">
            <i class="fas fa-user-edit mr-3"></i>
            내 정보확인/변경
          </v-tab>

        <!-- tab menu content -->
          <!-- 내 프로젝트들 -->
          <v-tab-item class="myProjects">
            <v-card flat>
              <v-card-text>
                <MyProjects />
              </v-card-text>
            </v-card>
          </v-tab-item>

          <!-- 내가 후원한 프로젝트들 -->
          <v-tab-item class="myFundings">
            <v-card flat>
              <v-card-text>
                <FundedProjects />
              </v-card-text>
            </v-card>
          </v-tab-item>

          <v-tab-item class="myArticles">
            <v-card flat>
              <v-card-text>
                <h2>내가 작성한 글들</h2>
              
              </v-card-text>
            </v-card>
          </v-tab-item>

          <!-- 내가 좋아한 프로젝트들 -->
          <v-tab-item class="myLikes">
            <v-card flat>
              <v-card-text>
                <LikedProjects />
              </v-card-text>
            </v-card>
          </v-tab-item>

          <!-- 팔로우 정보 -->
          <v-tab-item class="Follow">
            <v-card flat>
              <v-card-text>
                <FollowInfo />
              </v-card-text>
            </v-card>
          </v-tab-item>

          <!-- 내 정보 -->
          <v-tab-item class="myInfo" v-if="isSelf">
            <v-card flat>
              <v-card-text>
                <MyInfo />
              </v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs>
      </v-card>

      <!-- 새 프로젝트 생성 버튼 -->
      <v-tooltip right>
        <template v-slot:activator="{ on, attrs }">
          <v-btn color="primary" to="/newproject" class="mb-4 ml-4" depressed fab fixed bottom left v-bind="attrs" v-on="on">
            <v-icon>fas fa-plus</v-icon>
          </v-btn>
        </template>
        <span>새 프로젝트 생성</span>
      </v-tooltip>
    </div>

    <!-- loading page -->
    <div v-if="!userDataFetched">
      <h2 class="text-center mt-10">Loading...</h2>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState, mapGetters } from "vuex";

import MyProjects from '../../components/user/MyProjects.vue'
import FundedProjects from '../../components/user/FundedProjects.vue'
import LikedProjects from "../../components/user/LikedProjects.vue";
import FollowInfo from "../../components/user/FollowInfo.vue";
import MyInfo from "../../components/user/MyInfo.vue";

export default {
  name: "MyPage",

  components: {
    MyProjects,
    FundedProjects,
    LikedProjects,
    FollowInfo,
    MyInfo
  },

  computed: {
    ...mapState(["userInfo", "followerList", "followingList", "isFollowing",]),
    ...mapGetters(["userDataFetched"]),

    isSelf: function() {
      return this.userInfo.nickname === localStorage.getItem("username");
    },
    followInfo: function() {
      return {
        following: this.userInfo.username
      };
    },
    followerCount: function() {
      return this.followerList.length;
    },
    followingCount: function() {
      return this.followingList.length;
    },
    // isFollowing: function() {
    //   currentUser = localStorage.getItem("username")
    //   return 
    // },
  },

  watch: {
    '$route.params.username': function() {
      let username = this.$route.params.username;
      this.getUserInfo(username);
      this.fetchFollowers(username);
      this.fetchFollowings(username);
    },
  },

  methods: {
    ...mapActions([
      "getUserInfo",
      "follow",
      "unfollow",
      "fetchFollowers",
      "fetchFollowings",
      "checkFollowing"
    ]),
    ...mapMutations(["setUserInfo"]),
  },

  created() {
    let username = this.$route.params.username;
    this.getUserInfo(username);
    this.fetchFollowers(username);
    this.fetchFollowings(username);
    
    this.checkFollowing(username)
    
  }
};
</script>

<style scoped>
.header {
  background-color: #e4dfda;
}
</style>