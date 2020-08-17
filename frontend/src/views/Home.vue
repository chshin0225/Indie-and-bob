<template>
  <div>
    
    <!-- header -->
    <div class="header d-flex align-center px-10">
      <h1 class="white--text text-h4 text-left">
        인디게임 크라우드 펀딩 플랫폼 <br>
        Indie and Bob에 오신 것을 환영합니다<br>
      </h1>
    </div>

    <!-- body -->
    <v-container>
      <v-row>
        <v-col cols=8>
          <h2>이런 프로젝트는 어때요?</h2>
          <v-row>
            <router-link v-for="project in mostFundedList" :key="project.gameId">
              <v-col>
                
              </v-col>
            </router-link>
          </v-row>
        </v-col>

        <v-col cols=4>
          <h2>가장 좋아요가 많은 프로젝트</h2>
          <RankedList :projectList="mostLikedList" />
        </v-col>
        
      </v-row>

      <v-row>
        <v-spacer></v-spacer>

        <v-col cols=4>
          <h2>마감임박 프로젝트</h2>
          <RankedList :projectList="almostFinishedList" />
        </v-col>

        <!-- <v-col cols=4>
          <h3>가장 달성금액 많은 프로젝트</h3>
          <RankedList :projectList="mostFundedList" />
        </v-col> -->

        <!-- <v-col cols=4>
          <h3>가장 달성률 높은 프로젝트</h3>
          <RankedList :projectList="highestPercentList" />
        </v-col> -->

        <v-col v-if="isLoggedIn && genreRecommendationList.length > 0">
          <h3>장르 기반 추천</h3>
          <RankedList :projectList="genreRecommendationList" />
        </v-col>
        
        <v-col v-if="isLoggedIn && userRecommendationList.length > 0">
          <h3>유저 기반 추천</h3>
          <RankedList :projectList="userRecommendationList" />
        </v-col>
      </v-row>

    </v-container>

    <!-- 새 프로젝트 생성 버튼 -->
      <v-tooltip right v-if="isLoggedIn">
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
    ...mapState(['mostLikedList', 'almostFinishedList', 'mostFundedList', 'highestPercentList', 'genreRecommendationList', 'userRecommendationList',]),
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
  .header {
    height: 400px;
    background-image: url("../assets/background_img2.jpg");
    background-position: center center;
    background-size: cover;
  }
</style>

