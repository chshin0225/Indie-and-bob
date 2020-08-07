<template>
  <div>
    <div class="header d-flex align-center justify-center">
      <h1 class="white--text text-h4 text-center">
        인디게임 크라우드 펀딩 플랫폼 <br>
        Indie and Bob에 오신 것을 환영합니다<br>
        <span class="text-h6 font-weight-light">아직 서비스 출시 준비 중입니다. 조금만 더 기다려주세요!</span>
      </h1>
    </div>

    <!-- <div>
      <router-link to="/user/mypage/test100">test100</router-link><br>
      <router-link to="/user/mypage/test200">test200</router-link><br>
      <router-link to="/user/mypage/test300">test300</router-link><br>
      <router-link to="/user/mypage/test400">test400</router-link>
    </div> -->

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
</template>

<script>
export default {
  name: 'Home',

};
</script>

<style scoped>
  .header {
    height: 100vh;
    background-image: url("../assets/background_img.jpg");
    background-position: center center;
    background-size: cover;
  }
</style>

