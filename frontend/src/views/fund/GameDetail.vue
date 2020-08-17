<template>
  <div class="game-detail">
    <!-- admin -->
    <v-alert 
      color="secondary" 
      elevation="2"
      tile 
      class="mb-0" 
      v-if="isAdmin && project.isApprove === 0"
    >
      <v-row>
        <span class="text-h5 mx-4 mt-1">프로젝트 심사</span>
        <v-spacer></v-spacer>
        <v-btn cols="auto" @click="approve" class="mr-3" color="primary" depressed>승인</v-btn>
        
        <!-- 거절 및 거절 사유 -->
        <v-menu offset-y nudge-bottom="5px" :close-on-content-click="false">
          <template v-slot:activator="{ on, attrs }">
            <v-btn 
              cols="auto" 
              class="mr-3" 
              color="accent" 
              depressed
              v-bind="attrs"
              v-on="on"
            >
              거절
            </v-btn>
          </template>
          <v-card width="500px">
            <v-card-title>거절 사유</v-card-title>
            <v-card-text>
              <v-text-field
                label="거절 사유"
                v-model="reasonOfRejection"
                hide-details="true"
              ></v-text-field>
              <small v-if="error" class="primary--text">{{ error }}</small>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn @click="disapprove" color="primary" depressed>거절</v-btn>
            </v-card-actions>
          </v-card>
        </v-menu>
      </v-row>
    </v-alert>

    <!-- header 부분 -->
    <div class="header">
      <v-container v-if="projectDataFetched">
        <v-row class="d-flex flex-column-reverse flex-sm-row">
          <!-- 프로젝트 정보 -->
          <v-col cols="12" sm="6"> 
            <h1 class="mb-4 my-sm-4">{{ project.name }}</h1>
            <!-- <p>{{ rewards }}</p> -->
            <!-- <p>{{ project }}</p> -->
            <p class="mb-2">{{ genreData }}</p>
            <p class="mb-2">개발자: <span><router-link class="text-decoration-none" :to="`/user/mypage/${project.nickname}`">{{ project.nickname }}</router-link></span></p>
            <p class="mb-2">기간: {{ $moment(project.createdAt).format("YYYY.MM.DD") }} ~ {{ $moment(project.deadline).format("YYYY.MM.DD") }}</p>
            <p>목표: {{ project.aim }}원</p>

            <v-container class="pb-0">
              <v-row v-if="project.isApprove===-1">
                <v-col cols=12 class="px-0">
                  <v-alert outlined text color="primary" dense>
                    <div class="d-flex justify-space-between">
                      <p class="my-0 mt-1">거절사유: <span class="black--text ml-1">{{ project.reason }}</span></p>
                      <v-btn color="accent" depressed :to="`/project/${project.gameId}`">수정하기</v-btn>
                    </div>
                  </v-alert>
                </v-col>
              </v-row>

              <v-row v-else>
                <p class="mb-2"> {{ this.fundingProgress }}% 달성</p>
                <v-spacer></v-spacer>
                <p class="mb-2">현재 모금액: {{ project.aim - project.leftPrice }}원</p>
                <v-progress-linear :value="fundingProgress" height="7"></v-progress-linear>
              </v-row>
            </v-container>

          </v-col>
          <v-spacer></v-spacer>

          <!-- thumbnail -->
          <v-col cols="12" sm="6" class="d-flex justify-center align-center">
            <v-img :src="project.thumbnail" contain max-height="300"></v-img>
          </v-col>
        </v-row>
      </v-container>
    </div>

    <!-- content 부분 -->
    <v-container v-if="projectDataFetched">
      <v-row>
        <!-- tab section -->
        <v-col cols=12 sm="8">
          <v-tabs fixed-tabs>
            <v-tab>소개</v-tab>
            <v-tab>Q&A</v-tab>
            <v-tab>응원하기</v-tab>
            <v-tab v-if="isDeveloper">프로젝트 관리</v-tab>

            <!-- 프로젝트 소개 -->
            <v-tab-item>
              <v-card flat>
                <v-card-text>
                  <v-card flat>
                    <Viewer class="content-viewer" :initialValue='project.content'/>
                  </v-card>
                </v-card-text>
              </v-card>
            </v-tab-item>

            <!-- Q&A -->
            <v-tab-item>
              <QuestionandAnswer />
            </v-tab-item>

            <!-- 응원하기 -->
            <v-tab-item>
              <GameCommunity />
            </v-tab-item>

            <!-- 프로젝트 관리 -->
            <v-tab-item v-if="isDeveloper">
              <GameSettings />
            </v-tab-item>
          </v-tabs>
        </v-col>

        <v-col cols=12 sm="4">

          <!-- 좋아요/공유하기 btn -->
          <v-card tile class="pa-4 mb-4">
            <v-row>

              <v-col class="d-flex justify-center">
                <v-btn @click="likeButton()" color="primary" outlined class="mr-2">
                  <i :color="iconColor" class="fas fa-heart fa-lg mr-2" v-if="isLike"></i> 
                  <i :color="iconColor" class="far fa-heart fa-lg mr-2" v-if="!isLike"></i> 
                  좋아요
                </v-btn>

                <v-menu v-model="menu" :close-on-content-click="false" nudge-width="200" nudge-bottom=20 nudge-left=100 offset-y bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn color="accent" outlined v-bind="attrs" v-on="on">
                      공유하기
                    </v-btn>
                  </template>

                  <v-card>
                    <v-container>
                      <h3>이 프로젝트를 널리널리 소문내주세요!</h3>
                      <p ref="shareUrl" class="my-3">{{url}}</p>
                      <v-spacer></v-spacer>
                      <div>
                        <v-btn small depressed color="accent" @click="copy()">복사하기</v-btn>
                      </div>
                    </v-container>
                  </v-card>
                </v-menu>
              </v-col>

              <v-col cols=12 class="d-flex justify-center">
                <v-dialog v-model="likeDialog" scrollable max-width="400">
                  <template v-slot:activator="{ on, attrs }">
                    <h4 class="font-weight-medium">{{ likedPeopleCount }}명이 이 프로젝트를 좋아합니다<span class="ml-1"><v-btn color="accent" x-small depressed v-bind="attrs" v-on="on">더보기</v-btn></span></h4>
                  </template>
                  <v-card>
                    <v-card-title class="headline">이 프로젝트를 좋아한 사람들</v-card-title>
                    <v-card-text>
                      <GameLike />
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="dark" text @click="likeDialog = false">닫기</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-col>

            </v-row>
          </v-card>

          <!-- rewards section -->
          <v-card tile>
            <!-- header -->
            <v-subheader><h3>리워드 종류</h3></v-subheader>
            <v-divider></v-divider>

            <!-- rewards -->
            <v-expansion-panels accordion multiple>
              <v-expansion-panel v-for="reward in rewards" :key="reward.rewardId">
                <v-expansion-panel-header>
                  <div>
                    <h3 class="font-weight-medium">{{ reward.rewardName }}</h3>
                    <p class="mb-0 mt-1 text-subtitle-2 font-weight-regular">{{ reward.price }}원</p>
                  </div>
                </v-expansion-panel-header>
                <v-expansion-panel-content>
                  <div v-html="reward.content"></div>
                  <v-divider class="mt-3"></v-divider>
                  <v-row>
                    <v-col class="d-flex align-center">
                      <h4 class="font-weight-medium">{{ reward.leftCount }}개 남음</h4>
                    </v-col>
                    <v-spacer></v-spacer>
                    <v-col class="d-flex justify-end">
                      <v-btn v-if="project.isApprove === 1 && reward.leftCount > 0" @click="rewardBuy(reward.rewardId)" color="accent" depressed rounded>구매</v-btn>
                      <v-btn v-else color="accent" disabled rounded>매진</v-btn>
                    </v-col>
                  </v-row>
                </v-expansion-panel-content>
              </v-expansion-panel>
            </v-expansion-panels>

          </v-card>
        </v-col>
      </v-row>
    </v-container>

  </div>
</template>

<script>
import axios from "axios";
import router from "../../router";
import SERVER from "../../api/base";
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";

import GameCommunity from "../../components/GameDetail/GameCommunity.vue";
import QuestionandAnswer from "../../components/GameDetail/QuestionandAnswer.vue";
import GameLike from "../../components/GameDetail/GameLike.vue";
import GameSettings from "../../components/GameDetail/GameSettings.vue"

import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";
import _ from 'lodash'

export default {
  name: 'GameDetail',

  components: {
    GameCommunity,
    QuestionandAnswer,
    GameLike,
    GameSettings,
    Viewer,
  },

  data() {
    return {
      isLike: false,
      likeDialog: false,
      shareIcon: "white",
      iconColor: "accent",
      menu: false,
      url: "http://i3a105.p.ssafy.io" + window.location.pathname,

      genres: '',
      rewards: [],

      reasonOfRejection: null,
      error: false,
    };
  },

  computed: {
    ...mapState(['project', 'username']),
    ...mapGetters(['headersConfig', 'projectDataFetched', 'likedPeopleCount', 'isAdmin', 'isLoggedIn']),

    fundingProgress: function() {
      if (this.project.aim === this.project.leftPrice) {
        return 0
      } else {
        return _.round((this.project.aim - this.project.leftPrice) / this.project.aim * 100)
      }
    },

    genreData: function() {
      this.project.genreName.forEach(item => {
        this.genres += item + ' | '
      })
      return this.genres.slice(0, this.genres.length-2)
    },

    leftPriceData: function() {
      if (this.project.leftPrice > 0) {
        return this.project.leftPrice
      } else {
        return 0
      }
    },

    isDeveloper: function() {
      return this.project.nickname === localStorage.getItem("username");
    },

  },

  methods: {
    ...mapActions(['getProject', 'fetchLikedUsers',]),
    ...mapMutations(['setContent']),

    rewardBuy(id) {
      router.push("/fund/" + id);
    },

    isLiked() {
      let PARAMS = "?nickname=" + localStorage.getItem("username") + "&gameId=" + this.$route.params.id
      axios.get(SERVER.BASE + SERVER.ISLIKE + PARAMS, this.headersConfig)
        .then((res) => {
          if (res.data.status) {    
            this.isLike = true;
            this.iconColor = "primary";
          } else {
            this.iconColor = "accent"
          }
        })
        .catch(err => console.error(err))
    },

    fetchRewards() {
      axios.get(SERVER.BASE + SERVER.REWARDS + this.$route.params.id)
      .then(res => {
        if (res.data.object.length > 0) {
          res.data.object.forEach(item => {
            item.content = item.content.replace(/(?:\r\n|\r|\n)/g, '<br />')
          })
        }
        this.rewards = res.data.object
      })
      .catch(err => console.error(err))
    },

    likeButton() {
      if (this.isLoggedIn) {
        if (this.iconColor === "accent") {
          axios.post(SERVER.BASE + SERVER.LIKE,
            {
              nickname: localStorage.getItem("username"),
              gameId: this.project.gameId,
            },
            this.headersConfig
            )
            .then(() => {
              this.iconColor = "primary"
              this.isLike = true
              this.fetchLikedUsers(this.project.gameId)
            })
            .catch(err => console.error(err))
        } else {
          const deleteLike = SERVER.BASE + SERVER.LIKE + "?nickname=" + localStorage.getItem("username") + "&gameId=" + this.project.gameId;
          axios.delete(deleteLike, this.headersConfig)
            .then(() => {
              this.iconColor = "accent"
              this.isLike = false
              this.fetchLikedUsers(this.project.gameId)
            })
            .catch(err => console.error(err))
        }
      } else {
        alert("로그인을 해야 좋아요를 누를 수 있습니다.")
      }
    },

    copy() {
      this.$clipboard(this.url);
      alert("url이 복사되었습니다.");
      this.menu = false;
      this.shareIcon = "white";
    },

    approve() {
      axios.put(SERVER.BASE + SERVER.APPROVE, {
        gameId: this.$route.params.id,
        isApprove: 1,
      })
        .then(() => {
          router.push({ name: "NewProjectRequest" });
        })
        .catch((err) => console.error(err));
    },

    disapprove() {
      if (this.reasonOfRejection) {
        axios.put(SERVER.BASE + SERVER.APPROVE, {
          gameId: this.$route.params.id,
          isApprove: -1,
          reason: this.reasonOfRejection
        })
          .then((res) => {
            console.log(res);
            router.push({ name: "NewProjectRequest" });
          })
          .catch((err) => console.error(err)); 
      } else {
        this.error = '거절사유를 써주세요'
      }
    },
  },

  created() {
    this.getProject(this.$route.params.id)
    this.isLiked()
    this.fetchRewards()
    this.fetchLikedUsers(this.$route.params.id)
  },
};
</script>

<style scoped>   
</style>