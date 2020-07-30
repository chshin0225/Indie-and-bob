<template>
  <div>
    <!-- header -->
    <div class="header">
      <v-container v-if="render">
        <h1>{{ project.name }}</h1>
        <v-row>
          <v-col cols="12" sm="6">
            <p>기간: {{ project.createdAt }} ~ {{ project.deadline }}</p>
            <p>목표: {{ project.aim }}원</p>
          </v-col>
          <v-col cols="12" sm="6">
            <p class="font-weight-bold">이 프로젝트를 좋아한 사람들</p>

            <v-dialog v-model="likeDialog" scrollable max-width="400">
              <template v-slot:activator="{ on, attrs }">
                <v-btn color="primary" x-small v-bind="attrs" v-on="on">더보기</v-btn>
              </template>
              <v-card>
                <v-card-title class="headline">이 프로젝트를 좋아한 사람들</v-card-title>
                <v-card-text>
                  <GameLike :project="project" />
                  </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="dark" text @click="likeDialog = false">CLOSE</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

            <!-- 좋아하는 사람들 프로필 사진을 앞에 30개 정도만 작게 올리고 ... + 이런식으로 할까봐요 -->
          </v-col>
        </v-row>
      </v-container>
    </div>

    <!-- content -->
    <v-container v-if="render">
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
                    <Viewer :initialValue="project.content"/>
                  </v-card>
                </v-card-text>
              </v-card>
            </v-tab-item>

            <!-- Q&A -->
            <v-tab-item>
              <h2>Q&A</h2>
              <QuestionandAnswer v-bind:project="project" />
            </v-tab-item>

            <!-- Community -->
            <v-tab-item>
              <h2>Community</h2>
              <GameCommunity v-bind:project="project" />
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
                  <v-expansion-panel-header>{{reward.rewardName}}</v-expansion-panel-header>
                  <v-expansion-panel-content>
                    {{reward.content}}
                    <br />
                    가격:{{reward.price}}원
                    <br />
                    남은 수량 : {{reward.leftCount}}
                    <v-btn @click="rewardBuy(reward.rewardId)" color="primary">구매하러 가기</v-btn>
                  </v-expansion-panel-content>
                </v-expansion-panel>
              </v-expansion-panels>
            </v-list>
          </v-card>
        </v-col>
      </v-row>
      <div fixed bottom right class="mr-5 mb-5">
        <v-btn cols="auto" fab large @click="likeButton()" color="accent" class="mr-3">
          <v-icon :color="iconColor">fas fa-heart</v-icon>
        </v-btn>
        <v-menu v-model="menu" :close-on-content-click="false" :nudge-width="200" offset-x>
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="accent" v-bind="attrs" v-on="on" fab large cols="auto" class="ml-3">
              <v-icon :color="shareIcon" @click="shareButton()">fas fa-share-alt</v-icon>
            </v-btn>
          </template>

          <v-card>
            <p ref="shareUrl">{{url}}</p>
            <v-btn x-small @click="copy()">복사하기</v-btn>
            <v-card-actions>
              <v-spacer></v-spacer>

              <v-btn text @click="menu = false;shareIcon='white';">close</v-btn>
            </v-card-actions>
          </v-card>
        </v-menu>
      </div>
      <div v-if="isAdmin && project.isApprove === 0">
        <v-btn cols="auto" @click="approve" class="mr-3">승인</v-btn>
        <v-btn cols="auto" @click="disapprove" class="mr-3">거절</v-btn>
      </div>
    </v-container>
  </div>
</template>

// <script>
import axios from "axios";
import router from "../../router";
import SERVER from "../../api/base";
import GameCommunity from "../../components/GameDetail/GameCommunity.vue";
import QuestionandAnswer from "../../components/GameDetail/QuestionandAnswer.vue";
import GameLike from "../../components/GameDetail/GameLike.vue";
import { mapActions } from "vuex";

import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";

export default {
  mounted() {
    axios
      .get(
        SERVER.BASE + SERVER.GAME + this.$route.params.id,
        this.headersConfig
      )
      .then((res) => {
        this.project = res.data.object;
        this.render = true;
        this.project.deadline = this.project.deadline.substr(0, 10);
        this.project.createdAt = this.project.createdAt.substr(0, 10);
        let PARAMS =
          "?nickname=" +
          localStorage.getItem("username") +
          "&gameId=" +
          this.project.gameId;
        axios
          .get(SERVER.BASE + SERVER.ISLIKE + PARAMS, this.headersConfig)
          .then((res) => {
            console.log(res);
            if (res.data.status) {
              
              this.isLike = true;
              this.iconColor = "primary";
            } else this.iconColor = "white";
          });
        axios.get(SERVER.BASE + SERVER.REWARDS + this.project.gameId)
        .then(res => {
          console.log(res.data)
          this.rewards = res.data.object
        })
      })
      .catch((err) => {
        console.error(err);
      });
    if (localStorage.getItem("username") === "admin") {
      this.isAdmin = true;
    }
  },
  components: {
    GameCommunity,
    QuestionandAnswer,
    GameLike,
    Viewer,
  },

  data() {
    return {
      likeDialog: false,
      shareIcon: "white",
      menu: false,
      isLike: false,
      url: "http://localhost:3000" + window.location.pathname,
      //후에 바꿀 예정데스
      isAdmin: false,
      iconColor: "white",
      render: false,
      project: {
        name: "",
        startedAt: "",
        createdAt: "",
        aim: "",
        deadline: "",
        content: "",
      },
      rewards: [],
    };
  },
  methods: {
    ...mapActions(["getProject"]),

    rewardBuy(id) {
      router.push("/fund/" + id);
    },
    likeButton() {
      if (this.iconColor === "white") {
        axios.post(
          SERVER.BASE + SERVER.LIKE,
          {
            nickname: localStorage.getItem("username"),
            gameId: this.project.gameId,
          },
          this.headersConfig
        );
        this.iconColor = "primary";
      } else {
        const deleteLike =
          SERVER.BASE +
          SERVER.LIKE +
          "?nickname=" +
          localStorage.getItem("username") +
          "&gameId=" +
          this.project.gameId;
        axios.delete(deleteLike, this.headersConfig);
        this.iconColor = "white";
      }
    },
    shareButton() {
      this.shareIcon = "primary";
    },
    copy() {
      this.$clipboard(this.url);
      this.$alert("url이 복사되었습니다.");
      this.menu = false;
      this.shareIcon = "white";
    },
    approve() {
      axios
        .put(SERVER.BASE + SERVER.APPROVE, {
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
      axios
        .put(SERVER.BASE + SERVER.APPROVE, {
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
};
</script>

<style scoped>
.header {
  background-color: #e4dfda;
}
</style>