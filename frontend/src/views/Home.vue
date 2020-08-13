<template>
  <div>
    <!-- <div class="header d-flex align-center justify-center">
      <h1 class="white--text text-h4 text-center">
        인디게임 크라우드 펀딩 플랫폼 <br>
        Indie and Bob에 오신 것을 환영합니다<br>
        <span class="text-h6 font-weight-light">아직 서비스 출시 준비 중입니다. 조금만 더 기다려주세요!</span>
      </h1>
    </div> -->

    <v-container>
      <h1>안녕 나는 메인페이지야 룰루</h1>
      <v-row>
      
        <v-col cols=6>
          <h3>가장 좋아요가 많은 프로젝트</h3>
          <RankedList :projectList="mostLikedList" />
        </v-col>
        
        <v-col cols=6>
          <h3>마감임박 프로젝트</h3>
          <RankedList :projectList="almostFinishedList" />
        </v-col>

      </v-row>

      <v-row>
        <v-col cols=4>
          <h3>가장 달성금액 많은 프로젝트</h3>
          <RankedList :projectList="mostFundedList" />
        </v-col>

        <v-col cols=4>
          <h3>가장 달성률 높은 프로젝트</h3>
          <RankedList :projectList="highestPercentList" />
        </v-col>

        <v-col v-if="isLoggedIn && genreRecommendationList.length > 0">
          <h3>장르 기반 추천</h3>
          <RankedList :projectList="genreRecommendationList" />
        </v-col>

      </v-row>


      <!-- <p>{{ almostFinishedList }}</p> -->
      <!-- <p>{{ mostFundedList }}</p>
      <p>{{ highestPercentList }}</p> -->
    </v-container>

    <!-- 새 프로젝트 생성 버튼 -->
      <v-tooltip right>
        <template v-slot:activator="{ on, attrs }">
          <v-btn color="primary" to="/newproject" class="mb-4 ml-4" depressed fab fixed bottom left v-bind="attrs" v-on="on">
            <v-icon>fas fa-plus</v-icon>
          </v-btn>
        </template>
        <span>새 프로젝트 생성</span>
      </v-tooltip>
  </div>
</template>

<script>
import RankedList from '../components/Home/RankedList.vue'
import { mapState, mapActions, mapGetters } from 'vuex'

export default {
  name: 'Home',

  components: {
    RankedList,
  },

  computed: {
    ...mapState(['mostLikedList', 'almostFinishedList', 'mostFundedList', 'highestPercentList', 'genreRecommendationList',]),
    ...mapGetters(['isLoggedIn',])
  },

  methods: {
    ...mapActions(['fetchHomeData',])
  },

  created() {
    this.fetchHomeData()
  },
};
</script>

<style scoped>
  /* .header {
    height: 100vh;
    background-image: url("../assets/background_img.jpg");
    background-position: center center;
    background-size: cover;
  } */
</style>

