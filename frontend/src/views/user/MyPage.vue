<template>
  <div>
    <div v-if="dataFetched">
      <!-- header -->
      <div class="header">
        <v-container>
          <v-row>
            <h1>{{ userInfo.nickname }}</h1>
            <v-btn 
              outlined 
              small 
              color="primary" 
              class="align-self-center ml-3" 
              @click="follow({'following': userInfo.nickname})"
              v-if="!isSelf"
              >follow
            </v-btn>
          </v-row>
          <p class="mb-0">introduction: {{ userInfo.introduction }}</p>
          <p class="mb-0">following: 0</p>
          <p class="mb-0 pb-3">followers: 0</p>  
          <p>{{ userInfo }}</p>
        </v-container>
      </div>
      <v-divider></v-divider>

      <!-- tab menu -->
      <v-card elevation=0>
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
          <v-tab-item class="myProjects">
            <v-card flat>
              <v-card-text>
              <h2>내 프로젝트들</h2>
              </v-card-text>
            </v-card>
          </v-tab-item>

          <v-tab-item class="myFundings">
            <v-card flat>
              <v-card-text>
                <h2>내가 후원한 프로젝트들</h2>
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

          <v-tab-item class="Like">
            <v-card flat>
              <v-card-text>
                <h2>내가 좋아요한 프로젝트들</h2>
                <ProjectCard />
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
      <router-link to="/newproject">새 프로젝트 만들기</router-link>
    </div>

    <div v-if="!dataFetched">
      <h3 class="text-center">Loading...</h3>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState, mapGetters } from 'vuex'

import ProjectCard from '../../components/ProjectCard.vue'
import FollowInfo from '../../components/user/FollowInfo.vue'
import MyInfo from '../../components/user/MyInfo.vue'

export default {
  name: 'MyPage',

  components: {
    ProjectCard,
    FollowInfo,
    MyInfo,
  },

  computed: {
    ...mapState(['userInfo']),
    ...mapGetters(['dataFetched']),
    isSelf: function() {
      return this.userInfo.nickname === localStorage.getItem('username')
    },
  },

  methods: {
    ...mapActions([ 'getUserInfo', 'follow',]),
    ...mapMutations(['setUserInfo',])
  },

  created() {
    this.getUserInfo(this.$route.params.username)
  },
};
</script>

<style scoped>
  .header {
    background-color: #e4dfda;
  }
</style>