<template>
  <div>
    <!-- header 부분 -->
    <div class="header">
      <v-container v-if="projectDataFetched">
        <v-row>
          <!-- 프로젝트 정보 -->
          <v-col cols="12" sm="6">
            <h1>{{ project.name }}</h1>
            <!-- <p>{{ rewards }}</p> -->
            <!-- <p>{{ project }}</p> -->
            <p>장르: {{ genreData }}</p>
            <p>개발자: <span><router-link class="text-decoration-none" :to="`user/mypage/${project.nickname}`">{{ project.nickname }}</router-link></span></p>
            <p>기간: {{ $moment(project.createdAt).format("YYYY.MM.DD") }} ~ {{ $moment(project.deadline).format("YYYY.MM.DD") }}</p>
            <p>목표: {{ project.aim }}원</p>
            <v-container class="pb-0">
              <v-row>
                <p> {{ this.fundingProgress }}% 달성</p>
                <v-spacer></v-spacer>
                <p>남은 금액: {{ leftPriceData }}원</p>
              </v-row>
            </v-container>

            <v-progress-linear v-model="fundingProgress"></v-progress-linear>
          </v-col>
          <v-spacer></v-spacer>

          <!-- thumbnail -->
          <v-col cols="12" sm="6">
            <v-img :src="project.thumbnail" contain></v-img>
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

            <!-- 프로젝트 소개 -->
            <v-tab-item>
              <v-card flat>
                <v-card-text>
                  <v-card outlined>
                    <Viewer :initialValue='project.content'/>
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
                  {{ reward.content }}
                  <v-divider class="mt-3"></v-divider>
                  <v-row>
                    <v-col class="d-flex align-center">
                      <h4 class="font-weight-medium">{{ reward.leftCount }}개 남음</h4>
                    </v-col>
                    <v-spacer></v-spacer>
                    <v-col class="d-flex justify-end">
                      <v-btn @click="rewardBuy(reward.rewardId)" color="accent" depressed rounded>구매</v-btn>
                    </v-col>
                  </v-row>
                </v-expansion-panel-content>
              </v-expansion-panel>
            </v-expansion-panels>

          </v-card>
        </v-col>
      </v-row>

 
      <!-- admin -->
      <div v-if="isAdmin && project.isApprove === 0">
        <v-btn cols="auto" @click="approve" class="mr-3">승인</v-btn>
        <v-btn cols="auto" @click="disapprove" class="mr-3">거절</v-btn>
      </div>  

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

import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";
import _ from 'lodash'
import firebase from 'firebase'

export default {
  name: 'GameDetail',

  components: {
    GameCommunity,
    QuestionandAnswer,
    GameLike,
    Viewer,
  },

  data() {
    return {
      isLike: false,
      likeDialog: false,
      shareIcon: "white",
      // iconColor: "white",
      iconColor: "accent",
      menu: false,
      url: "http://i3a105.p.ssafy.io:3000" + window.location.pathname,
      rewards: [],
      isAdmin: false,
    };
  },

  computed: {
    ...mapState(['project']),
    ...mapGetters(['headersConfig', 'projectDataFetched', 'likedPeopleCount',]),
    fundingProgress: function() {
      if (this.project.aim === this.project.leftPrice) {
        return 0
      } else {
        return _.round((this.project.aim - this.leftPriceData) / this.project.aim * 100)
      }
    },
    genreData: function() {
      return this.project.genreName.join()
    },
    leftPriceData: function() {
      if (this.project.leftPrice > 0) {
        return this.project.leftPrice
      } else {
        return 0
      }
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
        this.rewards = res.data.object
      })
      .catch(err => console.error(err))
    },

    likeButton() {
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
    },

    copy() {
      this.$clipboard(this.url);
      this.$alert("url이 복사되었습니다.");
      this.menu = false;
      this.shareIcon = "white";
    },

    approve() {
      axios.put(SERVER.BASE + SERVER.APPROVE, {
        gameId: this.$route.params.id,
        isApprove: 1,
      })
        .then((res) => {
          console.log(res);
          router.push({ name: "GameMain" });
        })
        .catch((err) => console.error(err));
    },

    disapprove() {
      axios.put(SERVER.BASE + SERVER.APPROVE, {
        gameId: this.$route.params.id,
        isApprove: -1,
      })
        .then((res) => {
          console.log(res);
          router.push({ name: "GameMain" });
        })
        .catch((err) => console.error(err));
    },
  },

  created() {
    this.getProject(this.$route.params.id)
    this.isLiked()
    this.fetchRewards()
    this.fetchLikedUsers(this.$route.params.id)
    
    if (localStorage.getItem("username") === "admin") {
      this.isAdmin = true;
    }    
  },

  updated() {
    if (this.projectDataFetched) {
      const storageRef = firebase.storage().ref()
      const ref = this.project.thumbnail
      storageRef.child(ref).getDownloadURL()
        .then(url => {
          this.project.thumbnail = url
        })
    }
  },
};
</script>

<style scoped>
.header {
  background-color: #e4dfda;
}
</style>