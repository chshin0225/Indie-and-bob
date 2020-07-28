<template>
  <v-container>
    <h1 class="my-5 text-center">프로젝트 개요를 입력해주세요!</h1>
    <v-row class="justify-center">
      <v-col class="py-0" sm="10">
        <label for="title">제목</label>
        <v-text-field
          v-model="title"
          id="extraAddress"
          placeholder="프로젝트 제목을 입력해주세요"
          type="title"
          hide-details="true"
          outlined
          required
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row class="justify-center">
      <v-col class="py-0 mt-5" sm="10">
        <label for="genres">장르선택(복수의 선택도 가능합니다)</label>
        <v-select v-model="value" :items="genres" chips id="genres" multiple outlined></v-select>
      </v-col>
    </v-row>
    <v-row class="justify-center">
      <v-col class="py-0 mt-5" sm="10">
        <label for="content">프로젝트 내용</label>
        <editor
          ref="toastuiEditor"
          :options="editorOptions"
          id="content"
          initialEditType="wysiwyg"
          previewStyle="vertical"
        />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col sm="10">
        <v-menu
          v-model="menu2"
          :close-on-content-click="false"
          :nudge-right="40"
          transition="scale-transition"
          offset-y
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field v-model="date" label="마감날짜" readonly v-bind="attrs" v-on="on"></v-text-field>
          </template>
          <v-date-picker v-model="date" :min="today" @input="menu2 = false"></v-date-picker>
        </v-menu>
      </v-col>
    </v-row>
    <v-row class="justify-center">
      <v-col class="py-0 mt-5" sm="10">
        <label for="aim">목표 모금금액(단위:원)</label>
        <v-text-field
          v-model="aim"
          id="aim"
          placeholder="목표 모금금액을 작성해주세요. 상세 리워드는 프로젝트 상세 페이지에서 추가/수정 가능합니다."
          type="number"
          hide-details="true"
          outlined
          required
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row class="mt-5 justify-center">
      <v-row class="justify-center">
        <v-col class="py-0 mt-5" sm="6">
          <label for="thumbnail">썸네일</label>
          <v-file-input
            id="thumbnail"
            @change="uploadImage"
            accept="image/*"
            label="썸네일 이미지를 입력해주세요"
            prepend-icon="mdi-camera"
          ></v-file-input>
        </v-col>
      </v-row>
      <v-btn @click="onButtonClick" class="deep-purple accent-4">프로젝트 생성</v-btn>
    </v-row>
  </v-container>
</template>

<script>
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import axios from "axios";
import router from "../../router";
import { Editor } from "@toast-ui/vue-editor";
import SERVER from "../../api/base";
import { mapGetters } from 'vuex';
export default {
  components: {
    editor: Editor,
  },
  data() {
    return {
      text: "",
      value: "",
      genres: [
        "판타지",
        "캐쥬얼",
        "보드게임",
        "사행성",
        "가챠",
        "로또",
        "이것저것",
      ],
      today: new Date().toISOString().substr(0, 10),
      date: new Date().toISOString().substr(0, 10),
      title: "",
      menu2: false,
      editorOptions: {
        hideModeSwitch: true,
      },
      content: "",
      aim: 0,
      thumbnailUrl: "",
    };
  },
  computed: {
    ...mapGetters([ 'headersConfig' ])
  },
  methods: {
    onButtonClick() {
      let PARAMS = {
        content: this.$refs.toastuiEditor.invoke("getMarkdown"),
        name: this.title,
        deadline: this.date,
        aim: this.aim,
        thumbnail: this.thumbnailUrl,
        
      };
      console.log(this.headersConfig)
      axios
        .post(SERVER.BASE + SERVER.GAMEREGISTER, PARAMS, this.headersConfig)
        .then((res) => {
          router.push("/project/" + res.data.id);
        })
        .catch((err) => console.error(err));
    },
    uploadImage() {
      let fileInfo = document.getElementById("thumbnail").files[0];
      let reader = new FileReader();
      reader.onload = function () {
        this.thumbnailUrl = reader.result;
      };
      if (fileInfo) {
        reader.readAsDataURL(fileInfo);
      }
    },
  },
};
</script>

<style>
</style>
