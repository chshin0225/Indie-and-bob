<template>
  <v-container>
      <h1 class='my-5 text-center'>신청 완료! 관리자가 프로젝트를 검토하는 데에 최대 일주일의 시간이 걸립니다.</h1>
      <v-row class='justify-center'>
        <v-btn col='auto' @click="onButtonClick" dark>메인 페이지로 돌아가기</v-btn>
      </v-row>
      
  </v-container>
</template>

<script>
import router from '../../router'
export default {
  methods: {
      onButtonClick() {
          router.push({name: 'Home'})
      }
  }
}
</script>

<style>

</style>
