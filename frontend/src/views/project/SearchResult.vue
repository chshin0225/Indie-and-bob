<template>
  <div>
    <v-tabs v-model="tab" background-color="primary" class="elevation-0" dark centered grow>
      <v-tabs-slider></v-tabs-slider>
      <v-tab>통합검색</v-tab>
      <v-tab>게임/프로젝트</v-tab>
      <v-tab>유저</v-tab>
      <v-tab>커뮤니티</v-tab>
      <v-tab-item>
        <v-card flat>
          <v-card-text>통합검색 부분입니다.</v-card-text>
          <!-- <p>{{ searchResult }}</p> -->
          <v-list v-if="searchResult.length > 0">
            <v-list-item
              v-for="result in searchResult"
              :key="result.nickname"
              :to="`/user/mypage/${result.nickname}`"
            >
              <v-list-item-avatar>
                <v-img src="../../assets/default_profile.png"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title>{{ result.nickname }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <div v-else class="mt-5">
            <p class="text-center">결과없음</p>
          </div>
        </v-card>
      </v-tab-item>

      <v-tab-item>
        <v-card flat outlined>
          <v-card-text>게임 / 프로젝트 부분입니다.</v-card-text>
        </v-card>
      </v-tab-item>

      <v-tab-item>
        <v-card flat outlined>
          <v-card-text>커뮤니티 부분입니다.</v-card-text>
        </v-card>
      </v-tab-item>

      <v-tab-item>
        <v-card flat outlined>
          <v-card-text>유저검색 부분입니다.</v-card-text>
        </v-card>
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "SearchResult",

  data() {
    return {
      tab: null,
      tabs: 4
    };
  },

  computed: {
    ...mapState(["searchResult"])
  }
};
</script>

<style>
</style>