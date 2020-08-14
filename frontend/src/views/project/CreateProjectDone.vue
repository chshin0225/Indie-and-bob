<template>
  <v-container>
      <h1 class='my-5 text-center'>프로젝트 등록 신청이 완료 완료되었습니다!</h1>
      <h4 class="text-center font-weight-medium">관리자가 프로젝트를 검토하는 데에 최대 일주일의 시간이 걸리며, 심사 현황은 마이 페이지 > 만든 프로젝트에서 확인할 수 있습니다.</h4>
      <v-row class='justify-center my-6'>
        <v-btn col='auto' :to="{ name: 'Home' }" depressed color="primary">메인 페이지로 돌아가기</v-btn>
      </v-row>
      
  </v-container>
</template>

<script>
export default {
  name: 'CreateProjectDone',
}
</script>

<style>

</style>
