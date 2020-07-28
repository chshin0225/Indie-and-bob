<template v-if="render">
  <v-container>
    <h1>{{ this.project.name }} 제작페이지</h1>
    <Viewer v-if="content != null" :initialValue="content" />
    <hr />
    <h2>리워즈 목록</h2>
    <div v-if="rewards">
      <div v-for="reward in rewards" :key="reward.id">{{reward.title}}</div>
    </div>
    <v-btn>최종 제출</v-btn>

    <!-- 리워드 만들기 -->
    <v-row justify="center">
      <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
        <template v-slot:activator="{ on, attrs }">
          <v-btn color="info" dark v-bind="attrs" v-on="on">리워드 추가</v-btn>
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
import { mapGetters } from 'vuex';
import "codemirror/lib/codemirror.css"; 
import "@toast-ui/editor/dist/toastui-editor.css"; 
import { Viewer } from "@toast-ui/vue-editor";


export default {
  components: {
    Viewer
  },
  created() {
    this.id = this.$route.params.id;
    console.log(this.id)
    axios
      .get(SERVER.BASE + SERVER.GAME + this.id)
      .then(res => {
        console.log(res)
        this.project = res.data.object;
        console.log(this.project)
        console.log(this.project.name)
      })
      .catch(err => console.error(err));
    axios.get(SERVER.BASE + SERVER.REWARDS+ this.id)
    .then(res => {
      console.log('rewards',res)
      this.rewards = res.data;
      this.render = true
      console.log(this.rewards)
    })
    .catch(err =>{
    console.error(err)
    this.rewards = [];
    })
  },
  computed: {
    ...mapGetters([ 'headersConfig' ])
  },
  data() {
    return {
      project: {},
      content: `<h1>프로젝트 컨텐츠에ㅣ긩</h1><br><br><p>우룰루루루루html태그를 이용했다긔</p>`,
      rewards: [],
      dialog: false,
      notifications: false,
      sound: true,
      widgets: false,
      r_thumbnailUrl: "",
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
      reader.onload = function() {
        this.thumbnailUrl = reader.result;
      };
      if (fileInfo) {
        reader.readAsDataURL(fileInfo);
      }
    },
    rewardSave() {
      const PARAMS =  {
        game_id: this.id,
        reward_name: this.r_title,
        content: this.r_content,
        price : this.r_price,
        left_count: this.r_left,
        r_img: this.r_thumbnailUrl,
      }
      axios.post(SERVER.BASE + SERVER.REWARDREGISTER, PARAMS, this.headersConfig)
      .then(res => {
        console.log(res)
            axios.get(SERVER.BASE + SERVER.REWARDS+ this.id)
    .then(res => {
      console.log('rewards',res)
      this.rewards = res.data;
      this.render = true
      console.log(this.rewards)
    })
    .catch(err =>{
    console.error(err)
    this.rewards = [];
    })

      })
    }
  }
};
</script>

<style>
</style>
