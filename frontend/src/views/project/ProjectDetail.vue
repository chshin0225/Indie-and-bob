<template v-if="render">
  <v-container>
    <h1>{{ this.project.name }} 제작페이지</h1>
    <v-btn @click="projectDelete">프로젝트 삭제</v-btn>
    <h2>프로젝트 소개내용</h2>
    <v-card outlined>
      <Viewer v-if="content != null" :initialValue="content" />
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
                <label for="r_thumbnailUrl">썸네일</label>
                <v-file-input
                  id="r_thumbnailUrl"
                  v-model="r_thumbnailUrl"
                  @change="uploadImgPreview"
                  accept="image/*"
                  label="썸네일 이미지를 입력해주세요"
                  prepend-icon="mdi-camera"
                ></v-file-input>
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
    </div>
    <v-row class="mt-5" justify="center">
      <v-btn @click="onSubmitButton()" color="primary">최종 제출</v-btn>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import router from "../../router";
import SERVER from "../../api/base";
import { mapGetters } from "vuex";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";

export default {
  components: {
    Viewer,
  },
  created() {
    this.id = this.$route.params.id;
    console.log(this.id);
    axios
      .get(SERVER.BASE + SERVER.GAME + this.id)
      .then((res) => {
        this.project = res.data.object;
        this.content = this.project.content
        console.log(this.project)
      })
      .catch(err => {
        console.error(err)
      router.push('/404')});
    axios
      .get(SERVER.BASE + SERVER.REWARDS + this.id)
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
  },
  data() {
    return {
      project: {},
      content: '',
      rewards: [],
      dialog: false,
      notifications: false,
      sound: true,
      widgets: false,
      r_thumbnailUrl: "n",
      r_left: 0,
      r_price: 0,
      r_title: "",
      r_content: "",
      render: false,
    };
  },
  methods: {
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
        .post(SERVER.BASE + SERVER.REWARDREGISTER, PARAMS, this.headersConfig)
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
    onSubmitButton(){
      //관리자 페이지한테 승인 신청하기
    },
    rewardDelete(reward_id){
      axios.delete(SERVER.BASE+ SERVER.REWARDDETAIL+reward_id, this.headersConfig )
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
    },
    projectEdit(){
      router.push('/')
    },
    projectDelete(){
    this.$prompt("If you want to delete your project, please type 'Delete the project'.", "", "Are you sure?", "question").then((text) => {
     // do somthing with text
     if(text==='Delete the project'){
     axios.delete(SERVER.BASE+SERVER.GAME+this.id, this.headersConfig)
     .then(router.push('/feed/main'))
     .catch(err=> {
       console.error(err)
     })
     }else{
       this.$alert("Wrong input!")}

});
    },
  },
};
</script>

<style>
</style>
