<template>
  <div>
    <!-- header -->
    <div class="header">
      <v-container>
        <v-row>
          <h1>Username</h1>
          <v-btn outlined small color="primary" class="align-self-center ml-3">follow</v-btn>
        </v-row>
        <p class="mb-0">introduction: 한줄소개</p>
        <p class="mb-0">following: 0</p>
        <p class="mb-0 pb-3">followers: 0</p>  
        <p>{{ user }}</p>
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
        <v-tab>
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
            </v-card-text>
          </v-card>
        </v-tab-item>
        <v-tab-item class="Follow">
          <v-card flat>
            <v-card-text>
              <h2>내 팔로우 정보</h2>
            </v-card-text>
          </v-card>
        </v-tab-item>
        <v-tab-item class="myInfo">
          <v-card flat>
            <v-card-text>
             <h2>내 개인정보</h2>
              <router-link to="/user/edit">회원정보 수정</router-link>
              <br />
              <router-link to="/user/password">비밀번호 변경</router-link>
            </v-card-text>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </v-card>

    <router-link to="/project/new">새 프로젝트 만들기</router-link>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  created() {
    this.getUserInfo('test100@naver.com')
  },
  methods: {
    ...mapActions([ 'getUserInfo' ]),
  },
  computed: {
    ...mapState([ 'user' ]),
  }
};
</script>

<style scoped>
  .header {
    background-color: #e4dfda;
  }
</style>