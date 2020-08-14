<template>
  <v-container>
    <h1 class="my-5 text-center">새 프로젝트 만들기</h1>
    
    <v-row class="justify-center mb-7">
      <v-col class="py-0" sm="10">
        <label for="title">프로젝트명</label>
        <v-text-field
          v-model="name"
          id="title"
          placeholder="프로젝트 이름을 입력해주세요."
          hide-details="true"
          class="pt-0"
          required
        ></v-text-field>
        <small class="d-block primary--text" v-if="error.name">{{ error.name }}</small>
      </v-col>
    </v-row>

    <v-row class="justify-center my-7">
      <v-col class="py-0" sm="10">
        <label for="genres">프로젝트 장르</label>
        <v-select
         v-model="genre" 
         :items="genres" 
         chips 
         id="genres" 
         multiple 
         placeholder="복수 선택도 가능합니다."
         hide-details="true"
         class="pt-0"
        ></v-select>
        <small class="d-block primary--text" v-if="error.genre">{{ error.genre }}</small>
      </v-col>
    </v-row>

    <v-row class="justify-center my-7">
      <v-col class="py-0" sm="10">
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

    <v-row class="justify-center my-7">
      <v-col sm="10" class="py-0">
        <v-menu
          v-model="menu2"
          :close-on-content-click="false"
          nudge-right="70"
          transition="scale-transition"

          min-width="290px"
        >
          <template v-slot:activator="{ on, attrs }">
            <label for="deadline">펀딩 마감 날짜</label>
            <v-text-field
              id="deadline"
              v-model="deadline" 
              readonly 
              prepend-icon="mdi-calendar-month"
              v-bind="attrs" 
              v-on="on" 
              hide-details="true"
              class="pt-1"
            ></v-text-field>
            <small class="d-block primary--text" v-if="error.deadline">{{ error.deadline }}</small>
          </template>
            <v-date-picker 
              v-model="deadline" 
              :min="today" 
              @input="menu2 = false"
            ></v-date-picker>
        </v-menu>
      </v-col>
    </v-row>

    <v-row class="justify-center my-7">
      <v-col class="py-0" sm="10">
        <label for="aim">목표 펀딩 금액 (단위: 원)</label>
        <v-text-field
          v-model="aim"
          id="aim"
          placeholder="상세 리워드 금액은 다음 페이지에서 추가/수정 가능합니다."
          hide-details="true"
          required
          class="pt-1"
        ></v-text-field>
        <small class="d-block primary--text" v-if="error.aim">{{ error.aim }}</small>
      </v-col>
    </v-row>

    <v-row class="justify-center mb-4">
      <v-col class="py-0" sm="10">
        <label for="thumbnail">썸네일</label>
        <v-file-input
          id="thumbnail"
          accept="image/*"
          placeholder="프로젝트의 썸네일 이미지를 입력해주세요."
          prepend-icon="mdi-camera"
          v-model="thumbnail"
          class="pt-1"
        ></v-file-input>
      </v-col>
    </v-row>

    <v-row class="justify-center mb-10 text-right">
      <v-col class="py-0" sm="10">
        <v-btn 
          cols=auto 
          @click="onButtonClick" 
          :disabled="!isSubmit" 
          class="accent" 
          depressed
          large
        >
          프로젝트 생성
        </v-btn>
      </v-col>
    </v-row>
     
  </v-container>
</template>

<script>
import router from '../../router'
import axios from "axios";
import { mapGetters, mapState } from 'vuex';

import { Editor } from "@toast-ui/vue-editor";
import SERVER from "../../api/base";
import firebase from 'firebase'
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";

export default {
  name: 'CreateProject',

  components: {
    editor: Editor,
  },

  data() {
    return {
      text: "",
      genre: "",
      content_upload: false,
      thumbnail_upload: false,
      genreId: [],
      today: new Date().toISOString().substr(0, 10),
      deadline: new Date().toISOString().substr(0, 10),
      name: "",
      menu2: false,
      editorOptions: {
        hideModeSwitch: true,
      },
      content: "",
      aim: null,
      thumbnail: null,
      error: {
        name: false,
        aim: false,
        genre: false,
        deadline: false,
      },
      isSubmit: false,
    };
  },

  computed: {
    ...mapGetters([ 'headersConfig' ]),
    ...mapState(['genres', 'genreToId'])
  },

  methods: {
    onButtonClick() {
      this.genre.forEach( item => {
        this.genreId.push(this.genreToId[item])
      })
      let PARAMS = {
        content: null,
        name: this.name,
        deadline: this.deadline,
        genre: this.genreId,
        aim: this.aim,
        thumbnail: this.thumbnail.name,
      };
      axios.post(SERVER.BASE + SERVER.GAMEREGISTER, PARAMS, this.headersConfig)
        .then(res => {
          console.log(res.data.object.gameId)
          this.isSubmit = false
          const storageRef1 = firebase.storage().ref(`game/${res.data.object.gameId}/content/${res.data.object.gameId}`).put(new Blob([this.$refs.toastuiEditor.invoke("getHtml")]))
          storageRef1.on(`state_changed`, snapshot => {
            if ((snapshot.bytesTransferred/snapshot.totalBytes)*100 === 100) {
              this.content_upload = true
              if (this.thumbnail_upload === true) {
                router.push({name: 'ProjectDetail', params: { id: res.data.object.gameId }})
              }
            }
          })
          const storageRef2 = firebase.storage().ref(`game/${res.data.object.gameId}/thumbnail/${res.data.object.gameId}.${res.data.object.extension}`).put(this.thumbnail)
          storageRef2.on(`state_changed`, snapshot => {
            if ((snapshot.bytesTransferred/snapshot.totalBytes)*100 === 100) {
              this.thumbnail_upload = true
              if (this.content_upload === true) {
                router.push({name: 'ProjectDetail', params: { id: res.data.object.gameId }})
              }
            }
          })
        })
        .catch((err) => console.error(err));
    },

    checkForm() {
      if (this.name.length <= 0) {
        this.error.name = "프로젝트 이름을 작성해주세요."
      } else {
        this.error.name = false
      }

      if (this.genre.length <= 0) {
        this.error.genre = "프로젝트의 장르를 선택해주세요."
      } else {
        this.error.genre = false
      }

      if (this.deadline === null) {
        this.error.deadline = "프로젝트 펀딩 기간을 선택해주세요."
      } else {
        this.error.deadline = false
      }

      if (this.aim <= 0) {
        this.error.aim = "프로젝트 펀딩 목표를 작성해주세요."
      } else {
        this.error.aim = false
      }

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    }
  },

  watch: {
    name() {
      this.checkForm()
    },

    genre() {
      this.checkForm()
    },

    deadline() {
      this.checkForm()
    },

    aim() {
      this.checkForm()
    }
  }
};
</script>

<style>
</style>
