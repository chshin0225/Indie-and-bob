<template>
  <v-container>
    <v-row class="justify-center">
      <v-col cols="12">
        <h1 class="text-center">없는 페이지입니다 :(</h1>
      </v-col>
      <v-col class="justify-center" cols="auto">
        <v-btn @click="goBack" color="primary" depressed>back</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import router from "../../router";

export default {
  name: 'PageNotFound',

  methods: {
    goBack() {
      router.go("-1");
    },
  },
};
</script>

<style>
</style>