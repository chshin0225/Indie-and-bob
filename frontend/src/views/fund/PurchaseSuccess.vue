<template>
  <v-container>
    <div v-if="rewardDataFetched">
      <v-row justify='center'>
        <v-col cols='auto'>
            <h2>리워드 구매가 완료되었습니다.</h2>
        </v-col>
      </v-row>

      <v-row class="justify-center">
        <v-col sm=8> 
          <!-- <p>{{ rewardData }}</p> -->
          <v-card class="mx-auto" outlined tile>
            <div class="overline px-4 pt-3">{{ rewardData.game.name }}</div>
            <v-card-title class="headline pt-1">{{ rewardData.reward.rewardName }}</v-card-title>
            <v-card-text>{{ rewardData.reward.content }}</v-card-text>
            <v-card-text class="subtitle-1 text-right pt-0 black--text">{{ rewardData.reward.price }} 원</v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <v-row class="justify-center">
        <v-col cols="auto">
            <v-btn @click="myPage()" color="accent" class="mr-6" depressed>마이 페이지로 가기</v-btn>
            <v-btn :to="`/game/${rewardData.reward.gameId}`" color="accent" depressed>프로젝트로 돌아가기</v-btn>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex'
import router from "../../router";

export default {
  name: 'PurchaseSuccess',

  computed: {
    ...mapState(['rewardData',]),
    ...mapGetters(['rewardDataFetched',]),
  },

  methods: {
    ...mapActions(['getReward',]),

    myPage() {
      const username = localStorage.getItem('username')
      router.push(`/user/mypage/${username}`)
    },
  },

  created() {
    this.getReward(this.$route.params.rewardId)
  },
}
</script>

<style>

</style>