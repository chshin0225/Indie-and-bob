<template>
  <v-container>
    <h1>Main page</h1>
    <div>
      <router-link to="/newproject">새 프로젝트 만들기</router-link>
    </div>
    <div>
      <router-link to="/user/mypage/test100">test100</router-link><br>
      <router-link to="/user/mypage/test200">test200</router-link><br>
      <router-link to="/user/mypage/test300">test300</router-link>
    </div>
  </v-container>
</template>

<script>
export default {
  
};
</script>
