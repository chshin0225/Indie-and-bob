<template>
  <v-container>
    <div v-if="fundingList">
      <v-list>
        <!-- <p>{{ fundingList[0] }}</p> -->
        <v-list-item two-line v-for="funding in fundingList" :key="funding.fundingId">
          <v-list-item-content>
            <v-list-item-title>{{ funding.nickname }}님이 {{ funding.money | numFormat }}원을 펀딩했습니다.</v-list-item-title>
            <v-list-item-subtitle>{{ $moment(funding.fundedAt).format('YYYY.MM.DD hh:mm') }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </div>
    <div v-else>
      <p class="py-6 text-center">아직 펀딩한 사람이 없네요!</p>
    </div>
  </v-container>
</template>

<script>
import SERVER from '../../api/base'
import axios from 'axios'

export default {
  name: 'GameSettings',

  data() {
    return {
      fundingList: [],
    }
  },

  methods: {
    fetchFundingList(gameId) {
      axios.get(SERVER.BASE + SERVER.FUNDLIST + gameId)
        .then(res => {
          this.fundingList = res.data.object
        })
        .catch(err => console.error(err))
    },
  },

  created() {
    this.fetchFundingList(this.$route.params.id)
  },
}
</script>

<style>

</style>