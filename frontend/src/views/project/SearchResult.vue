<template>
  <div>
    <v-tabs v-model="tab" background-color="primary" class="elevation-0" dark centered grow>
      <v-tabs-slider></v-tabs-slider>
      <v-tab>통합검색</v-tab>
      <v-tab>프로젝트</v-tab>
      <v-tab>유저</v-tab>
      <v-tab>커뮤니티</v-tab>

      <v-tab-item>
        <v-container>
          <v-card flat>
            <v-subheader>유저 검색 결과</v-subheader>
            <!-- <p>{{ searchResult }}</p> -->
            <v-list v-if="searchUser!==null && searchUser.length > 0">
              <v-list-item
                v-for="user in searchUser"
                :key="user.nickname"
                :to="`/user/mypage/${user.nickname}`"
              >
                <v-list-item-avatar>
                  <v-img v-if="user.profile" :src="user.profile"></v-img>
                  <v-img v-else src="../../assets/default_profile.png"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>{{ user.nickname }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <div v-else class="mt-5">
              <p class="text-center">유저 결과없음</p>
            </div>
            <v-divider class="my-4" color="#FFC0CB" :inset="inset"></v-divider>

            <v-subheader>프로젝트 검색 결과</v-subheader>
            <v-row v-if="searchGame !== null && searchGame.length>0">
            <v-col v-for="game in searchGame" :key="game.gameId" cols=12 sm=6 md=4>
              <v-card tile class="card">
                <router-link :to="`/game/${game.gameId}`" class="text-decoration-none">
                    <v-list-item>
                      <v-avatar>
                        <v-img v-if="game.profile" :src="game.profile" :alt="game.nickname"></v-img>
                        <v-img v-else src="../../assets/default_profile.png"></v-img>
                      </v-avatar>
                      <v-list-item-content class="ml-4">
                        <v-list-item-title class="font-weight-bold game-name">{{ game.name }}</v-list-item-title>
                        <router-link class="text-decoration-none" :to="`/user/mypage/${game.nickname}`">{{ game.nickname }}</router-link>
                        <v-list-item-subtitle v-if="!game.end" class="d-none d-sm-block">{{ game.leftDay }}일 남음</v-list-item-subtitle>
                        <v-list-item-subtitle v-else class="d-none d-sm-block">{{ $moment(game.deadline).format('YYYY.MM.DD') }}까지</v-list-item-subtitle>
                        <v-list-item-subtitle>{{ game.genreName }}</v-list-item-subtitle>
                      </v-list-item-content>
                    </v-list-item>
                    <v-img v-if="game.thumbnail" :src="game.thumbnail" height="194"></v-img>
                    <v-img v-else height="194" src="../../assets/default_project.png"></v-img>
                    
                    <v-list-item class="py-1 mx-1">
                      <v-row v-if="!game.end">
                        <p class="mb-1 ml-1 funding-progress">{{ fundingProgress(game.aim, game.leftPrice) }}% 달성</p>
                        <v-progress-linear :value="fundingProgress(game.aim, game.leftPrice)" height="7"></v-progress-linear>
                      </v-row>

                      <v-row v-else>
                      <v-chip
                        class="ma-2"
                        color="primary"
                      >
                        종료됨
                      </v-chip>
                    </v-row>
                    </v-list-item>
                </router-link>
              </v-card>
            </v-col>
          </v-row>
          <div v-else class="mt-5">
            <p class="text-center">프로젝트 결과없음</p>
          </div>
          <v-divider class="my-4" color="#FFC0CB" :inset="inset"></v-divider>

          <v-subheader>게시글 검색 결과</v-subheader>
          <v-row v-if="searchCommunity !== null && searchCommunity.length>0" class="justify-center">
            <v-col class="py-0" cols=10 v-for="article in searchCommunity" :key="article.communityId">
              <v-card class="mx-auto" :to="`/community/${article.communityId}`" flat>
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title class="headline mb-1">{{ article.title }}</v-list-item-title>
                    <v-list-item-subtitle class="pa-0 col-10">{{ $moment(article.updatedAt).format('YYYY.MM.DD hh:mm') }} by <span><router-link :to="`/user/mypage/${article.nickname}`" class="text-decoration-none">{{article.nickname}}</router-link></span></v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </v-card>
              <v-divider></v-divider>
            </v-col>
          </v-row>
          <div v-else class="mt-5">
            <p class="text-center">게시글 결과없음</p>
          </div>
          </v-card>
        </v-container>
      </v-tab-item>

      <!-- 프로젝트 -->
      <v-tab-item>
        <v-container>
          <v-card flat>
            <v-card-text>프로젝트 검색 결과입니다.</v-card-text>
            <v-row v-if="searchGame!==null && searchGame.length>0">
            <v-col v-for="game in searchGame" :key="game.gameId" cols=12 sm=6 md=4>
              <v-card tile class="card">
                <router-link :to="`/game/${game.gameId}`" class="text-decoration-none">
                    <v-list-item>
                      <v-avatar>
                        <v-img v-if="game.profile" :src="game.profile" :alt="game.nickname"></v-img>
                        <v-img v-else src="../../assets/default_profile.png"></v-img>
                      </v-avatar>
                      <v-list-item-content class="ml-4">
                        <v-list-item-title class="font-weight-bold game-name">{{ game.name }}</v-list-item-title>
                        <router-link class="text-decoration-none" :to="`/user/mypage/${game.nickname}`">{{ game.nickname }}</router-link>
                        <v-list-item-subtitle v-if="!game.end" class="d-none d-sm-block">{{ game.leftDay }}일 남음</v-list-item-subtitle>
                        <v-list-item-subtitle v-else class="d-none d-sm-block">{{ $moment(game.deadline).format('YYYY.MM.DD') }}까지</v-list-item-subtitle>
                        <v-list-item-subtitle>{{ game.genreName }}</v-list-item-subtitle>
                      </v-list-item-content>
                    </v-list-item>
                    <v-img v-if="game.thumbnail" :src="game.thumbnail" height="194"></v-img>
                    <v-img v-else height="194" src="../../assets/default_project.png"></v-img>
                    
                    <v-list-item class="py-1 mx-1">
                      <v-row v-if="!game.end">
                        <p class="mb-1 ml-1 funding-progress">{{ fundingProgress(game.aim, game.leftPrice) }}% 달성</p>
                        <v-progress-linear :value="fundingProgress(game.aim, game.leftPrice)" height="7"></v-progress-linear>
                      </v-row>

                      <v-row v-else>
                        <v-chip
                          class="ma-2"
                          color="primary"
                        >
                          종료됨
                        </v-chip>
                      </v-row>
                    </v-list-item>
                </router-link>
              </v-card>
            </v-col>
          </v-row>
          <div v-else class="mt-5">
            <p class="text-center">프로젝트 결과없음</p>
          </div>
          </v-card>
        </v-container>
      </v-tab-item>
      
      <!-- 유저 -->
      <v-tab-item>
        <v-container>
          <v-card flat>
            <v-card-text>유저 검색 결과입니다.</v-card-text>
            <v-list v-if="searchUser!==null && searchUser.length > 0">
              <v-list-item
                v-for="user in searchUser"
                :key="user.nickname"
                :to="`/user/mypage/${user.nickname}`"
              >
                <v-list-item-avatar>
                  <v-img v-if="user.profile" :src="user.profile"></v-img>
                  <v-img v-else src="../../assets/default_profile.png"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>{{ user.nickname }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <div v-else class="mt-5">
              <p class="text-center">유저 결과없음</p>
            </div>
          </v-card>
        </v-container>
      </v-tab-item>
      
      <!-- 커뮤니티 -->
      <v-tab-item>
        <v-container>
          <v-card flat>
            <v-card-text>커뮤니티 검색 결과입니다.</v-card-text>
            <v-row v-if="searchCommunity !== null && searchCommunity.length > 0" class="justify-center">
              <v-col class="py-0" cols=10 v-for="article in searchCommunity" :key="article.communityId">
                <v-card class="mx-auto" :to="`/community/${article.communityId}`" flat>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title class="headline mb-1">{{ article.title }}</v-list-item-title>
                      <v-list-item-subtitle class="pa-0 col-10">{{ $moment(article.updatedAt).format('YYYY.MM.DD hh:mm') }} by <span><router-link :to="`/user/mypage/${article.nickname}`" class="text-decoration-none">{{article.nickname}}</router-link></span></v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </v-card>
                <v-divider></v-divider>
              </v-col>
            </v-row>
            <div v-else class="mt-5">
              <p class="text-center">게시글 결과없음</p>
            </div>
          </v-card>
        </v-container>
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import _ from 'lodash'

export default {
  name: "SearchResult",

  data() {
    return {
      tab: null,
      tabs: 4,
      inset: false,
    };
  },

  computed: {
    ...mapState(["searchUser", "searchGame", "searchCommunity"]),
    
    fundingProgress() {
      return (aim, leftPrice) => {
        if (aim === leftPrice) {
          return 0
        } else {
          return _.round((aim - leftPrice) / aim * 100)
        }
      }
    },
  },

  methods: {
    ...mapActions(['search']),
  },

  watch: {
    '$route.params.keyword': function() {
      this.search(this.$route.params.keyword)
    },
  },

  created() {
    this.search(this.$route.params.keyword)
  }
};
</script>

<style>
</style>