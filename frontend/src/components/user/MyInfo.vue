<template>
  <v-container>
    <h1 class="text-center">내 개인정보</h1>
    <p>{{ userInfo }}</p>
    <v-btn to="/user/edit" color="accent" depressed class="mr-3">회원정보 수정</v-btn>
    <v-btn to="/user/password" color="accent" depressed>비밀번호 변경</v-btn>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: "MyInfo",
  
  computed: {
    ...mapState(['userInfo']),
  },
};
</script>

<style>
</style>