<template>
  <v-container>
    <h2 class="text-center mb-3">내 개인정보</h2>
    <div>
      <!-- <p>{{userInfo}}</p> -->
      <h3 class="my-2">이름 <span class="text-body-1 ml-2 my-auto">{{ userInfo.name }}</span></h3>
      <h3 class="my-2">닉네임 <span class="text-body-1 ml-2 my-auto">{{ userInfo.nickname }}</span></h3>
      <h3 class="my-2">주사용자 유형 <span class="text-body-1 ml-2 my-auto" v-text="userInfo.developer ? '개발자' : '일반 사용자'"></span></h3>
      <h3 class="my-2">좋아하는 장르 <span class="text-body-1 ml-2 my-auto">{{ genreData }}</span></h3>
      <h3 class="my-2">이메일 <span class="text-body-1 ml-2 my-auto">{{ userInfo.email }}</span></h3>
      <h3 class="my-2">휴대전화 <span class="text-body-1 ml-2 my-auto">{{ userInfo.phoneNumber }}</span></h3>
      <h3 class="my-2">주소 <span class="text-body-1 ml-2 my-auto">{{ userInfo.postcode }} {{ userInfo.address }} {{ userInfo.extraAddress }}</span></h3>
      <h3 class="my-2" v-if="userInfo.developer">계좌 정보 <span class="text-body-1 ml-2 my-auto">{{ userInfo.bankName }} {{ userInfo.bankAccount }}</span></h3>
    </div>
    <div class="text-right">
      <v-btn :to="`/user/edit/${userInfo.nickname}`" color="accent" depressed class="mr-3" >회원정보 수정</v-btn>
      <!-- <v-btn to="/user/password" color="accent" depressed>비밀번호 변경</v-btn> -->
    </div>

  </v-container>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: "MyInfo",

  data() {
    return {
      genres: '',
    }
  },
  
  computed: {
    ...mapState(['userInfo']),

    genreData: function() {
      this.userInfo.genreName.forEach(item => {
        this.genres += item + ', '
      })
      return this.genres.slice(0, this.genres.length-2)
    },
  },

};
</script>

<style>
</style>