<template>
  <v-container>
    <h1>{{ project.title }} 제작 페이지</h1>
    <p>{{ project.content }}</p>
    <v-btn>프로젝트 수정</v-btn>
    <hr />
    <h2>리워즈 목록</h2>
    <ul v-if="rewards">
      <div v-for="reward in rewards" :key="reward.id">{{reward.title}}</div>
    </ul>

    <!-- 리워드 만들기 -->
    <v-row justify="center">
      <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
        <template v-slot:activator="{ on, attrs }">
          <v-btn color="info" dark v-bind="attrs" v-on="on">리워드 추가</v-btn>
        </template>
        <v-card>
          <v-toolbar dark color="primary">
            <v-btn icon dark @click="dialog = false; onRewardSave()">
              <v-icon>mdi-close</v-icon>
            </v-btn>
            <v-toolbar-title>Settings</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn dark text @click="dialog = false">Save</v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-container>
            <v-row class="justify-center">
              <v-col class="py-0 mt-5" sm="6">
                <label for="title">리워드 제목</label>
                <v-text-field
                  v-model="title"
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
                <label for="thumbnail">썸네일</label>
                <v-file-input
                  id="thumbnail"
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
                  v-model="content"
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
                  v-model="price"
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
                  v-model="left"
                  id="left"
                  placeholder="판매 가능한 수량을 입력해주세요."
                  type="number"
                  hide-details="true"
                  outlined
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row justify=center>
            </v-row>
          </v-container>
        </v-card>
      </v-dialog>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import SERVER from '../../api/base';


export default {
  created() {
    this.id = this.$route.params.id;
    axios
      .get(SERVER.BASE + SERVER.GAMEDETAIL + this.id)
      .then(res => {
        this.project = res.data;
        if (this.project.rewards) {
          this.rewards = this.project.rewards;
        } else {
          this.rewards = [];
        }
      })
      .catch(err => console.error(err));
    axios.get(SERVER.BASE + SERVER.REWARDS+ this.id )
  },
  data() {
    return {
      dialog: false,
      notifications: false,
      sound: true,
      widgets: false,
      r_thumbnailUrl: "",
      r_left: 0,
      r_price: 0,
      r_title: "",
      r_content: ""
    };
  },
  methods: {
    uploadImgPreview() {
      let fileInfo = document.getElementById("thumbnail").files[0];
      let reader = new FileReader();
      reader.onload = function() {
        this.thumbnailUrl = reader.result;
      };
      if (fileInfo) {
        reader.readAsDataURL(fileInfo);
      }
    },
    onRewardSave() {

    }
  }
};
</script>

<style>
</style>
