<template>
  <v-container v-if="projectDataFetched">
    <h1 class="text-center my-5">프로젝트 세부정보</h1>

    <!-- thumbnail -->
    <v-row v-if="project.thumbnail" class="justify-center">
      <v-col class="py-0 my-4" sm="10">
        <v-img contain :src="project.thumbnail" :alt="project.name" height="300"></v-img>
      </v-col>
    </v-row>

    <!-- 기본정보 -->
    <h2 class="d-flex justify-space-between">
      기본 정보
      <span>
        <v-btn @click="projectEdit()" color="primary" text class="mb-1 mr-2">프로젝트 수정</v-btn>
        <v-btn @click="projectDelete()" color="primary" text class="mb-1">프로젝트 삭제</v-btn>
      </span>
    </h2>
    <v-divider></v-divider>
    <v-container>
      <h4 class="my-2">이름 <span class="text-body-1 ml-2 my-auto">{{ project.name }}</span></h4>
      <h4 class="my-2">장르 <span class="text-body-1 ml-2 my-auto">{{ genreData }}</span></h4>
      <h4 class="my-2">펀딩 마감 날짜 <span class="text-body-1 ml-2 my-auto">{{ $moment(project.deadline).format('YYYY.MM.DD') }}</span></h4>
      <h4 class="my-2">펀딩 목표 금액<span class="text-body-1 ml-2 my-auto">{{ project.aim | numFormat }}원</span></h4>
    </v-container>

    <!-- 소개 -->
    <h2 class="mt-5">프로젝트 소개</h2>
    <v-divider></v-divider>
    <v-container>
      <v-card flat>
        <Viewer ref="toastuiEditor" :initialValue="project.content" />
      </v-card>
    </v-container>

    <!-- 리워드 -->
    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
      <template v-slot:activator="{ on, attrs }">
        <h2 class="mt-5">
          리워드 목록 
          <span><v-btn color="primary" small outlined class="ml-2 mb-1" v-bind="attrs" v-on="on">+ 리워드 추가</v-btn></span>
        </h2>
      </template>

      <!-- 리워드 생성 -->
      <v-card>
        <v-toolbar dense flat color="primary">
          <v-btn icon @click="dialog = false;">
            <v-icon class="white--text">mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title class="white--text">리워드 생성</v-toolbar-title>
        </v-toolbar>

        <v-container>
          <v-row class="justify-center my-7">
            <v-col class="py-0" sm="10">
              <label for="title">리워드명</label>
              <v-text-field
                v-model="r_title"
                id="title"
                placeholder="리워드 이름"
                type="text"
                hide-details="true"
                required
                class="pt-0"
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row class="justify-center my-7">
            <v-col class="py-0" sm="10">
              <label for="content">내용</label>
              <v-textarea
                v-model="r_content"
                id="content"
                placeholder="리워드 설명"
                type="text"
                hide-details="true"
                required
                class="pt-0"
              ></v-textarea>
            </v-col>
          </v-row>

          <v-row class="justify-center my-7">
            <v-col class="py-0" sm="10">
              <label for="price">리워드 가격 (단위: 원)</label>
              <v-text-field
                v-model="r_price"
                id="price"
                placeholder="희망 판매가격 "
                hide-details="true"
                required
                class="pt-0"
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row class="justify-center my-7">
            <v-col class="py-0" sm="10">
              <label for="left">판매 가능 수량</label>
              <v-text-field
                v-model="r_left"
                id="left"
                placeholder="판매 가능한 수량을 입력해주세요."
                hide-details="true"
                required
                class="pt-0"
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row class="justify-center text-right">
            <v-col class="py-0 mt-5" sm="10">
              <v-btn @click="rewardSave()" color="accent" depressed>리워드 저장</v-btn>
            </v-col>
          </v-row>

        </v-container>
      </v-card>
    </v-dialog>

    <!-- 리워드 목록 -->
    <v-divider class="mt-1"></v-divider>
    <div v-if="rewards.length > 0">
      <v-row>
        <v-col cols="12" v-for="reward in rewards" :key="reward.rewardId">
          <v-card tile max-width="400px">  
            <v-card-title class="font-weight-bold">{{reward.rewardName}}</v-card-title>
            <v-card-subtitle class="pb-0">{{reward.price | numFormat}}원</v-card-subtitle>
            <v-card-text class="black--text mt-2">
              <div v-html="reward.content"></div>
            </v-card-text>
            <v-card-actions>
              <v-card-text class="px-3 py-0">수량 {{reward.leftCount}}개</v-card-text>
              <v-btn @click="rewardDelete(reward.rewardId)" color="accent" text>삭제</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </div>

    <div v-else>
      <p class="py-6 text-center">리워드가 없네요!</p>
    </div>

    <v-row class="text-right my-5">
      <v-col>
        <v-btn @click="finalSubmit()" color="accent" depressed large>프로젝트 최종 제출</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import router from "../../router";
import SERVER from "../../api/base";
import { mapGetters, mapActions, mapState } from "vuex";

import axios from "axios";
import { Viewer } from "@toast-ui/vue-editor";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
// import firebase from 'firebase'

export default {
  name: 'ProjectDetail',

  components: {
    Viewer,
  },

  computed: {
    ...mapGetters(["headersConfig", 'projectDataFetched']),

    ...mapState(['project']),

    genreData: function() {
      this.project.genreName.forEach(item => {
        this.genres += item + ' | '
      })
      return this.genres.slice(0, this.genres.length-2)
    },
  },

  data() {
    return {
      rewards: [],
      dialog: false,
      genres: '',
      notifications: false,
      sound: true,
      widgets: false,
      r_left: null,
      r_price: null,
      r_title: "",
      r_content: "",
      id: this.$route.params.id
    };
  },

  methods: {
    ...mapActions(['getProject']),

    uploadImgPreview() {
      let fileInfo = document.getElementById("thumbnail").files[0];
      let reader = new FileReader();
      reader.onload = function () {
        this.thumbnailUrl = reader.result;
      };
      if (fileInfo) {
        reader.readAsDataURL(fileInfo);
      }
    },

    rewardSave() {
      const PARAMS = {
        gameId: this.id,
        rewardName: this.r_title,
        content: this.r_content,
        price: this.r_price,
        leftCount: this.r_left,
      };

      axios.post(SERVER.BASE + SERVER.ALLREWARD, PARAMS, this.headersConfig)
        .then((res) => {
          console.log(res);
          axios
            .get(SERVER.BASE + SERVER.REWARDS + this.id)
            .then((res) => {
              // console.log("rewards", res.data.object); 
              if (res.data.object.length > 0) {
                res.data.object.forEach(item => {
                  item.content = item.content.replace(/(?:\r\n|\r|\n)/g, '<br />')
                })
              }
              this.rewards = res.data.object;
              this.dialog = false;
              this.r_thumbnailUrl = "";
              this.r_left = null;
              this.r_price = null;
              this.r_title = "";
              this.r_content = "";
            })
            .catch((err) => {
              console.error(err);
              this.rewards = [];
            });
        });
    },

    rewardDelete(reward_id) {
      axios.delete(SERVER.BASE + SERVER.ALLREWARD + reward_id, this.headersConfig)
        .then(() => {
          // console.log(res)
          axios.get(SERVER.BASE + SERVER.REWARDS + this.id)
            .then((res) => {
              this.rewards = res.data.object;
              alert("리워드가 삭제되었습니다.");
            })
            .catch((err) => {
              console.error(err);
              this.rewards = [];
            });
        });
    },

    projectEdit() {
      router.push("/pjt/edit/" + this.id);
    },

    projectDelete() {
      let answer = confirm('프로젝트를 삭제하시겠습니까?')
      if (answer) {
        axios.delete(SERVER.BASE + SERVER.GAME + this.id, this.headersConfig)
          .then(router.push({ name: 'Home' }))
          .catch((err) => console.error(err))
      } 
    },

    finalSubmit() {
      let answer = confirm('프로젝트를 제출하시겠습니까?')
      if (answer) {
        axios.get(SERVER.BASE + SERVER.GAMEREGISTER + `/${this.$route.params.id}`)
          .then(() => router.push({ name: 'CreateProjectDone'}))
          .catch(err => console.error(err))
      } 
    }

  },

  created() {
    this.getProject(this.$route.params.id)
    axios.get(SERVER.BASE + SERVER.REWARDS + this.$route.params.id)
      .then((res) => {
        // console.log("rewards", res.data.object);
        if (res.data.object.length > 0) {
          res.data.object.forEach(item => {
            item.content = item.content.replace(/(?:\r\n|\r|\n)/g, '<br />')
          })
        }
        this.rewards = res.data.object;
      })
      .catch((err) => {
        console.error(err);
        this.rewards = [];
    });  
  },
};
</script>

<style>
</style>
