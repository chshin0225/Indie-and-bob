<template v-if="render">
  <v-container v-if="project">
    <h1>{{ project.name }} 제작페이지</h1>

    <v-row v-if="project.thumbnail" class="justify-center">
      <v-col class="py-0" sm="6">
        <label for="profile-image">프로젝트 썸네일</label>
        <v-img contain :src="project.thumbnail" :alt="project.name"></v-img>
        <!-- <small class="d-block" v-if="error.email">{{ error.email }}</small> -->
      </v-col>
    </v-row>

    <v-btn @click="projectDelete()">프로젝트 삭제</v-btn>
    <v-btn @click="projectEdit()">프로젝트 수정</v-btn>
    <h2>프로젝트 소개내용</h2>
    <v-card outlined>
      <Viewer ref="toastuiEditor" :initialValue="project.content" />
    </v-card>
    <v-row class="justify-around">
      <v-col cols="auto">
        <h2>리워드 목록</h2>
      </v-col>
    </v-row>

    <!-- 리워드 만들기 -->
    <v-row class="justify-around">
      <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
        <template v-slot:activator="{ on, attrs }">
          <v-btn color="primary" class="ml-4" dark v-bind="attrs" v-on="on">리워드 추가</v-btn>
        </template>
        <v-card>
          <v-toolbar dark color="primary">
            <v-btn icon dark @click="dialog = false;">
              <v-icon>mdi-close</v-icon>
            </v-btn>
            <v-toolbar-title>Settings</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn dark text @click="rewardSave()">Save</v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-container>
            <v-row class="justify-center">
              <v-col class="py-0 mt-5" sm="6">
                <label for="title">리워드 제목</label>
                <v-text-field
                  v-model="r_title"
                  id="title"
                  placeholder="리워드 이름"
                  type="text"
                  hide-details="true"
                  outlined
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row class="justify-center">
              <v-col class="py-0 mt-5" sm="6">
                <label for="content">내용</label>
                <v-text-field
                  v-model="r_content"
                  id="content"
                  placeholder="제품 설명"
                  type="text"
                  hide-details="true"
                  outlined
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row class="justify-center">
              <v-col class="py-0 mt-5" sm="6">
                <label for="price">가격(단위:원)</label>
                <v-text-field
                  v-model="r_price"
                  id="price"
                  placeholder="희망 판매가격 "
                  type="number"
                  hide-details="true"
                  outlined
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row class="justify-center">
              <v-col class="py-0 mt-5" sm="6">
                <label for="left">판매 가능 수량</label>
                <v-text-field
                  v-model="r_left"
                  id="left"
                  placeholder="판매 가능한 수량을 입력해주세요."
                  type="number"
                  hide-details="true"
                  outlined
                  required
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card>
      </v-dialog>
    </v-row>

    <div v-if="rewards">
      <v-row>
        <v-col cols="12" sm="6" v-for="reward in rewards" :key="reward.rewardId">
          <v-card color="secondary" class="mx-auto" max-width="400">
            <v-img
              v-if="reward.rImg"
              class="white--text align-end"
              height="200px"
              :src="reward.rImg"
            ></v-img>
            <v-img v-else class="white--text align-end" height="200px" src="rnn"></v-img>
            <v-card-title>{{reward.rewardName}}</v-card-title>
            <v-card-subtitle class="pb-0">{{reward.price}}원</v-card-subtitle>

            <v-card-text class="text--primary">
              <div>{{reward.content}}</div>

              <div>{{reward.leftCount}}개 남음</div>
            </v-card-text>

            <v-card-actions>
              <v-btn @click="rewardDelete(reward.rewardId)" color="accent" text>Delete</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-btn @click="finalSubmit()" dark text>프로젝트 최종 제출하기</v-btn>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<script>
import axios from "axios";
import router from "../../router";
import SERVER from "../../api/base";
import { mapGetters, mapActions, mapState } from "vuex";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";
// import firebase from 'firebase'

export default {
  components: {
    Viewer,
  },
  created() {
    this.getProject(this.$route.params.id)
    axios
      .get(SERVER.BASE + SERVER.REWARDS + this.$route.params.id)
      .then((res) => {
        console.log("rewards", res.data.object);
        this.rewards = res.data.object;
        this.render = true;
      })
      .catch((err) => {
        console.error(err);
        this.rewards = [];
    });  
  },
  computed: {
    ...mapGetters(["headersConfig"]),
    ...mapState(['project'])
  },
  data() {
    return {
      rewards: [],
      dialog: false,
      notifications: false,
      sound: true,
      widgets: false,
      r_left: 0,
      r_price: 0,
      r_title: "",
      r_content: "",
      render: false,
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
        rImg: this.r_thumbnailUrl,
      };
      console.log(PARAMS);
      axios
        .post(SERVER.BASE + SERVER.REWARD, PARAMS, this.headersConfig)
        .then((res) => {
          console.log(res);
          axios
            .get(SERVER.BASE + SERVER.REWARDS + this.id)
            .then((res) => {
              console.log("rewards", res.data.object);
              this.rewards = res.data.object;
              this.dialog = false;
              this.r_thumbnailUrl = "";
              this.r_left = 0;
              this.r_price = 0;
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
      this.$prompt(
        "Please type 'Delete the reward'.",
        "",
        "Are you sure?",
        "question"
      ).then((text) => {
        if (text === "Delete the reward") {
          axios
            .delete(SERVER.BASE + SERVER.REWARD + reward_id, this.headersConfig)
            .then((res) => {
              console.log(res)
              axios
                .get(SERVER.BASE + SERVER.REWARDS + this.id)
                .then((res) => {
                  console.log("rewards", res.data.object);
                  this.rewards = res.data.object;
                  this.$alert("Reward Deleted");
                })
                .catch((err) => {
                  console.error(err);
                  this.rewards = [];
                });
            });
        } else {
          this.$alert("Wrong input!");
        }
      });
    },
    projectEdit() {
      router.push("/pjt/edit/" + this.id);
    },
    projectDelete() {
      this.$prompt(
        "If you want to delete your project, please type 'Delete the project'.",
        "",
        "Are you sure?",
        "question"
      ).then((text) => {
        // do somthing with text
        if (text === "Delete the project") {
          axios
            .delete(SERVER.BASE + SERVER.GAME + this.id, this.headersConfig)
            .then(router.push("/home"))
            .catch((err) => {
              console.error(err);
            });
        } else {
          this.$alert("Wrong input!");
        }
      });
    },
    finalSubmit() {
      this.$prompt(
        "프로젝트를 제출하고 싶으시다면 '최종제출'을 입력해주세요.",
        "",
        "(최종제출 이후에는 프로젝트를 수정할 수 없습니다.)",
        "정말 최종제출을 하시겠습니까?"
      ).then((text) => {
        if (text === "최종제출") {
          router.push({ name: 'CreateProjectDone'})
        } else {
          this.$alert("잘못된 입력입니다.")
        }
      })

    },
  },
};
</script>

<style>
</style>
