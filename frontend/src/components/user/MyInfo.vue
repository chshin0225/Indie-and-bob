<template>
  <v-container>
    <h1>내 개인정보</h1>
    <router-link to="/user/edit">회원정보 수정</router-link>
    <br />
    <router-link to="/user/password">비밀번호 변경</router-link>
  </v-container>
</template>

<script>
export default {
  name: "MyInfo"
};
</script>

<style>
</style>