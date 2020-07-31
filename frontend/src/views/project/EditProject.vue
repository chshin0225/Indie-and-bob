<template>
  <v-container>
    <h1 class="my-5 text-center">프로젝트 수정</h1>
    <v-row class="justify-center">
      <v-col class="py-0" sm="10">
        <label for="title">제목</label>
        <v-text-field
          v-model="project.name"
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
        <label for="content">프로젝트 내용</label>
        <editor
          ref="toastuiEditor"
          :options="editorOptions"
          id="content"
          :initial-value="project.content"
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
            <v-text-field v-model="project.deadline" label="마감날짜" readonly v-bind="attrs" v-on="on"></v-text-field>
          </template>
          <v-date-picker v-model="project.deadline" :min="today" @input="menu2 = false"></v-date-picker>
        </v-menu>
      </v-col>
    </v-row>
    <v-row class="justify-center">
      <v-col class="py-0 mt-5" sm="10">
        <label for="aim">목표 모금금액(단위:원)</label>
        <v-text-field
          v-model="project.aim"
          id="aim"
          placeholder="목표 모금금액을 작성해주세요. 상세 리워드는 프로젝트 상세 페이지에서 추가/수정 가능합니다."
          type="number"
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
            @change="uploadImage"
            accept="image/*"
            v-model = "project.thumbnail"
            label="썸네일 이미지를 입력해주세요"
            prepend-icon="mdi-camera"
          ></v-file-input>
        </v-col>
      </v-row>
    <v-btn @click="onButtonClick" class="primary">수정하기</v-btn>
  </v-container>
</template>


<script>
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import axios from 'axios'
import SERVER from '../../api/base'

import { Editor } from "@toast-ui/vue-editor";

export default {
  components: {
    editor: Editor
  },
  mounted() {
      this.id = this.$route.params.id
      axios.get(SERVER.BASE+SERVER.GAME+this.id)
      .then(res => {
          console.log(res.data.object)
          this.project = res.data.object
          this.project.deadline = this.project.deadline.substr(0,10)

     })
      .catch(err => console.error(err))
          
      },
  data() {
    return {
      project : {},

      menu2: false,
      editorOptions: {
        hideModeSwitch: true
      },
    };
  },
  methods: {
    onButtonClick() {
      this.project.content = this.$refs.toastuiEditor.invoke("getHtml");


    }
  }
};
</script>

<style>
</style>
