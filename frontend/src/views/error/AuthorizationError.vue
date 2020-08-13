<template>
  <v-container>
      <v-row class="justify-center">
          <v-col cols=12>
              <h1 class="text-center">권한이 없는 페이지네요!</h1>
          </v-col>
          <v-col class="justify-center" cols=auto>
              <v-btn @click="goToMain" color="primary">메인 페이지로 돌아가기</v-btn>
          </v-col>
      </v-row>
  </v-container>
</template>

<script>
import router from "../../router"

export default {
    name: 'AuthorizationError',
    
    methods: {
        goToMain () {
            router.push('/home')
        }
    },
}
</script>

<style>

</style>