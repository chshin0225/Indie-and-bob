<template>
  <v-container>
    <h1 class="my-5 text-center">새 프로젝트 만들기</h1>
    <v-row class="justify-center">
      <v-col class="py-0" sm="10">
        <label for="title">제목</label>
        <v-text-field
          v-model="name"
          id="extraAddress"
          placeholder="프로젝트 제목을 입력해주세요"
          type="name"
          hide-details="true"
          outlined
          required
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row class="justify-center">
      <v-col class="py-0 mt-5" sm="10">
        <label for="genres">장르선택(복수의 선택도 가능합니다)</label>
        <v-select v-model="genre" :items="genres" chips id="genres" multiple outlined></v-select>
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
            <v-text-field v-model="deadline" label="마감날짜" readonly v-bind="attrs" v-on="on"></v-text-field>
          </template>
          <v-card>
            <v-date-picker v-model="deadline" :min="today" @input="menu2 = false"></v-date-picker>
          </v-card>
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
            accept="image/*"
            label="썸네일 이미지를 입력해주세요"
            prepend-icon="mdi-camera"
            v-model="thumbnail"
          ></v-file-input>
        </v-col>
      </v-row>
      <v-row class='justify-center'>
        <v-btn cols=auto @click="onButtonClick" :disable="!isSubmit" class="accent" depressed>프로젝트 생성</v-btn>
      </v-row>
     
    </v-row>
  </v-container>
</template>

<script>
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import router from '../../router'
import axios from "axios";
// import router from "../../router";
import { Editor } from "@toast-ui/vue-editor";
import SERVER from "../../api/base";
import { mapGetters, mapState } from 'vuex';
import firebase from 'firebase'
export default {
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
      aim: 0,
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
