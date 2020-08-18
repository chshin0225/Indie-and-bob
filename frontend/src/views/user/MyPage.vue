<template>
  <div>
    <div v-if="userDataFetched">
      <!-- header -->
      <div class="header d-flex align-center">
        <v-container class="ml-5">
          <v-row>
            <v-avatar size=100 class="mr-5 mr-sm-9 align-self-center">
              <img v-if="profileImage" :src="profileImage" alt="프로필 사진" />
              <img v-else src="../../assets/default_profile.png" alt="프로필 사진" />
            </v-avatar>
            <v-col>
              <v-row>
                <h1 class="white--text">{{ userInfo.nickname }}</h1>
                <div class="d-flex" v-if="!isSelf">
                  <v-btn
                    depressed
                    rounded
                    small
                    color="primary"
                    class="align-self-center ml-3"
                    @click="follow({'following': userInfo.nickname,})"
                    v-if="!isFollowing"
                  >follow</v-btn>

                  <v-btn
                    depressed
                    rounded
                    small
                    color="accent"
                    class="align-self-center ml-3"
                    @click="unfollow(userInfo.nickname)"
                    v-if="isFollowing"
                  >unfollow</v-btn>
                </div>
                
              </v-row>
              <v-row>
                <p class="mb-0 white--text">
                  {{ userInfo.introduction }}
                </p>
              </v-row>

            </v-col>

          </v-row>
        </v-container>
      </div>
      <!-- <v-divider></v-divider> -->

      <!-- tab menu -->
      <v-card elevation="0">
        <v-tabs vertical>
          <v-tab>
            <i class="fas fa-laptop mr-3"></i>
             만든 프로젝트
          </v-tab>
          <v-tab v-if="isSelf">
            <i class="fas fa-money-check-alt mr-3"></i>
            내가 펀딩한 프로젝트
          </v-tab>
          <v-tab>
            <i class="fas fa-edit mr-3"></i>
            작성한 글
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

          <!-- 내가 펀딩한 프로젝트들 -->
          <v-tab-item class="myFundings" v-if="isSelf">
            <v-card flat>
              <v-card-text>
                <FundedProjects />
              </v-card-text>
            </v-card>
          </v-tab-item>

          <!-- 내가 작성한 글들 -->
          <v-tab-item class="myArticles">
            <v-card flat>
              <v-card-text>
                <MyArticles />
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
      <v-tooltip right v-if="userInfo.developer">
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
import MyArticles from '../../components/user/MyArticles.vue'
import LikedProjects from "../../components/user/LikedProjects.vue";
import FollowInfo from "../../components/user/FollowInfo.vue";
import MyInfo from "../../components/user/MyInfo.vue";

import firebase from "firebase"
import cookies from 'vue-cookies'

export default {
  name: "MyPage",

  components: {
    MyProjects,
    FundedProjects,
    MyArticles,
    LikedProjects,
    FollowInfo,
    MyInfo
  },
  
  data() {
    return {
      profileImage: null,
    }
  },

  computed: {
    ...mapState(["userInfo", "followerList", "followingList", "isFollowing",]),
    ...mapGetters(["userDataFetched"]),

    isSelf: function() {
      return this.userInfo.nickname === cookies.get('username');
    },
    followInfo: function() {
      return {
        following: this.userInfo.username
      };
    },
  },

  watch: {
    '$route.params.username': function() {
      let username = this.$route.params.username;
      this.getUserInfo(username);
      this.fetchFollowers(username);
      this.fetchFollowings(username);
    },
    
    userDataFetched() {
      if (this.userDataFetched) {
        const storageRef = firebase.storage().ref()
        if (this.userInfo.profile !== null) {
          storageRef.child(this.userInfo.profile).getDownloadURL().then(url => {
            this.profileImage = url
          }) 
        }
      }
    }
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
  /* background-color: #e4dfda; */
  height: 150px;
  background-image: url('../../assets/mypage_img2.jpg');
  background-position: center center;
  background-size: cover;
}

.v-tab {
  justify-content: left;
}
</style>