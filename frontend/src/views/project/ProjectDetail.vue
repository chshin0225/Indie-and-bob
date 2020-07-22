<template>
  <v-container>
    <h1>{{ project.title }} 제작 페이지</h1>
    <p>{{ project.content }}</p>
    <v-btn>프로젝트 수정</v-btn>
    <hr>
    <h2>리워즈 목록</h2>
    <v-btn>리워드 추가</v-btn>
  </v-container>
</template>

<script>
import axios from 'axios'
export default {

    created() {
      this.id = this.$route.params.id
      axios.get('getProject/'+this.id)
      .then(res => {
        this.project = res.data
      })
      .catch(err => console.error(err))

    }

}
</script>

<style>

</style>