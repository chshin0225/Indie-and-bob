<template>
  <div>
    <!-- header -->
    <div class="header">
      <v-container>
        <h1>{{ project.name }}</h1>
        <v-row>
          <v-col cols="12" sm="6">
            <p>기간: {{ project.startedAt }} ~ {{ project.deadline }}</p>
            <p>목표: {{ project.aim }}원</p>
          </v-col>
          <v-col cols="12" sm="6">
            <p class="font-weight-bold">이 프로젝트를 좋아한 사람들</p>

            <v-dialog v-model="likeDialog" persistent max-width="290">
              <template v-slot:activator="{ on, attrs }">
                <v-btn color="primary" x-small v-bind="attrs" v-on="on">더보기</v-btn>
              </template>
              <v-card>
                <v-card-title class="headline">Use Google's location service?</v-card-title>
                <v-card-text>Let Google help apps determine location. This means sending anonymous location data to Google, even when no apps are running.</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="green darken-1" text @click="likeDialog = false">Disagree</v-btn>
                  <v-btn color="green darken-1" text @click="likeDialog = false">Agree</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

            <!-- 좋아하는 사람들 프로필 사진을 앞에 30개 정도만 작게 올리고 ... + 이런식으로 할까봐요 -->
          </v-col>
        </v-row>
      </v-container>
    </div>

    <!-- content -->
    <v-container>
      <v-row>
        <!-- tab section -->
        <v-col cols="8">
          <v-tabs fixed-tabs>
            <v-tab>소개</v-tab>
            <v-tab>Q&A</v-tab>
            <v-tab>Community</v-tab>

            <!-- 프로젝트 소개 -->
            <v-tab-item>
              <v-card flat>
                <v-card-text>
                  <h2>프로젝트 소개</h2>
                  <v-card outlined>
                    <Viewer v-if="content != null" :initialValue="content" />
                  </v-card>
                </v-card-text>
              </v-card>
            </v-tab-item>

            <!-- Q&A -->
            <v-tab-item>
              <v-card flat>
                <v-card-text>
                  <h2>Q&A</h2>
                  <QuestionandAnswer />
                </v-card-text>
              </v-card>
            </v-tab-item>

            <!-- Community -->
            <v-tab-item>
              <v-card flat>
                <v-card-text>
                  <h2>Community</h2>
                  <GameCommunity />
                </v-card-text>
              </v-card>
            </v-tab-item>
          </v-tabs>
        </v-col>

        <!-- rewards section -->
        <v-col cols="4">
          <v-card tile>
            <v-list flat>
              <v-subheader>Rewards</v-subheader>
              <v-divider></v-divider>
              <v-expansion-panels>
                <v-expansion-panel v-for="reward in rewards" :key="reward.id">
                  <v-expansion-panel-header>{{reward.name}}</v-expansion-panel-header>
                  <v-expansion-panel-content>
                    {{reward.content}}
                    <br />
                    가격:{{reward.price}}원
                    <br />
                    남은 수량 : {{reward.left}}
                    <v-btn @click="rewardBuy(reward.id)" color="primary">구매하러 가기</v-btn>
                  </v-expansion-panel-content>
                </v-expansion-panel>
              </v-expansion-panels>
            </v-list>
          </v-card>
        </v-col>
      </v-row>
      <div fixed bottom right class="mr-5 mb-5">
        <v-btn cols="auto" fab large @click="likeButton()" :color="iconBgColor" class="mr-3">
          <v-icon :color="iconColor">fas fa-heart</v-icon>
        </v-btn>
        <v-btn
          cols="auto"
          fab
          large
          @click="shareButton()"
          :color="iconBgColor"
          class="ml-3 mr-auto"
        >
          <v-icon :color="primary">fas fa-share-alt</v-icon>
        </v-btn>
      </div>
      <div v-if="isAdmin">
        <v-btn cols='auto' @click="approve" class="mr-3">
          승인
        </v-btn>
        <v-btn cols='auto' @click="disapprove" class="mr-3">
          거절
        </v-btn>
      </div>
    </v-container>
  </div>
</template>

// <script>
// import axios from 'axios';
import router from "../../router";
// import SERVER from '../../api/base'
import GameCommunity from "./GameCommunity.vue";
import QuestionandAnswer from "./QuestionandAnswer.vue";
import { mapActions } from "vuex";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";
export default {
  created() {
   axios.get(SERVER.BASE + SERVER.GAME + this.$refs.params.id)
    .then(res => {
      this.project = res.data
    })
    .catch(err => {
      console.error(err)
    })
   if (localStorage.getItem('username') === 'admin') {
     this.isAdmin = true
   }
  },
  components: {
    GameCommunity,
    QuestionandAnswer,
    Viewer,
  },

  data() {
    return {
      likeDialog: false,
      iconColor: "white",
      iconBgColor: "accent",
      isAdmin: false,
      project: {
        name: "example",
        startedAt: "2020-07-14",
        aim: "1000000",
        deadline: "2020-08-14",
        content: "예시 프로젝트 매우 좋은 프로젝트 뜌듀듀듀",
      },
      rewards: [
        {
          id: 1,
          name: "1번리워드",
          content: "1번 리워드에 대한 설명입니다.",
          left: 999,
          price: 10000,
          thumbnail: "n",
        },
        {
          id: 2,
          name: "2번리워드",
          content: "2번 리워드에 대한 설명입니다.",
          left: 999,
          price: 10000,
          thumbnail: "n",
        },
        {
          id: 3,
          name: "3번리워드",
          content: "3번 리워드에 대한 설명입니다.",
          left: 99,
          price: 10000,
          thumbnail: "n",
        },
        {
          id: 4,
          name: "4번리워드",
          content: "4번 리워드에 대한 설명입니다.",
          left: 999,
          price: 10000,
          thumbnail: "n",
        },
        {
          id: 5,
          name: "5번리워드",
          content: "5번 리워드에 대한 설명입니다.",
          left: 999,
          price: 10000,
          thumbnail: "n",
        },
      ],
    };
  },
  methods: {
    ...mapActions(["getProject"]),

    rewardBuy(id) {
      router.push("/fund/" + id);
    },
    likeButton() {
      if (this.iconColor === "white") {
        this.iconColor = "primary";
      } else {
        this.iconColor = "white";
      }
    },
        approve() {
         axios.put(SERVER.BASE + SERVER.APPROVE, { gameId: this.$refs.params.id, isApprove: 1 })
            .then(res => {
                console.log(res)
            })
            .catch(err => console.error(err))
        },
        disapprove() {
         axios.put(SERVER.BASE + SERVER.APPROVE, { gameId: this.$refs.params.id, isApprove: -1 })
            .then(res => {
                console.log(res)
            })
            .catch(err => console.error(err))
        }
  },
};
</script>

<style scoped>
.header {
  background-color: #e4dfda;
}
</style>