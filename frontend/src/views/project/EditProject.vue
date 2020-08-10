<template>
  <v-container>
    <h1 class="my-5 text-center">프로젝트 수정</h1>
    <v-row class="justify-center">
      <v-col class="py-0" sm="10">
        <label for="title">제목</label>
        <v-text-field
          v-model="name"
          id="title"
          placeholder="프로젝트 제목을 입력해주세요"
          type="title"
          hide-details="true"
          outlined
          required
        ></v-text-field>
      </v-col>
    </v-row>

    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="usertype">장르</label>
        <v-select
          hide-details="true"
          :items="genres"
          id="usertype"
          placeholder="게임의 장르를 선택해주세요."
          v-model="genre"
          outlined
          chips
          multiple
        ></v-select>
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
          <v-date-picker v-model="deadline" @input="menu2 = false"></v-date-picker>
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
      <v-row class="justify-center">
        <v-col class="py-0 mt-5" sm="6">
          <label for="thumbnail">썸네일</label>
          <v-file-input
            id="thumbnail"
            accept="image/*"
            v-model="newThumbnail"
            label="썸네일 이미지를 입력해주세요"
            prepend-icon="mdi-camera"
          ></v-file-input>
        </v-col>
      </v-row>
    <!-- original thumbnail image -->
    <v-row v-if="originalThumbnail" class="justify-center">
      <v-col class="py-0" sm="6">
        <label for="profile-image">기존에 사용하던 이미지</label>
        <v-img contain :src="originalThumbnail" :alt="name"></v-img>
        <!-- <small class="d-block" v-if="error.email">{{ error.email }}</small> -->
      </v-col>
    </v-row>
    <v-btn @click="changeProjectInfo()" class="primary">수정하기</v-btn>
  </v-container>
</template>


<script>
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { mapGetters, mapState } from "vuex"; 
import axios from 'axios'
import SERVER from '../../api/base'
import firebase from 'firebase'

import { Editor } from "@toast-ui/vue-editor";

export default {
  components: {
    editor: Editor
  },
  data() {
    return {
      name: "",
      aim: "",
      deadline: null,
      menu2: false,
      genre: null,
      genreId: [],

      editorOptions: {
        hideModeSwitch: true
      },
      newThumbnail: null,
      originalThumbnail: null,
      originalThumbnailURL: null,
    };
  },
  computed: {
    ...mapGetters(['headersConfig']),
    ...mapState(['genres', 'genreToId'])
  },
  methods: {
    changeProjectInfo() {
      this.genre.forEach((item) => {
        console.log(this.genreId)
        this.genreId.push(this.genreToId[item])
      })
      let PARAMS = {
        "aim": this.aim,
        "deadline": this.deadline,
        "gameId": this.$route.params.id,
        "isApprove": 0,
        "name": this.name,
        "thumbnail": null,
        "genre": this.genreId,
      }
      firebase.storage().ref(`game/${this.$route.params.id}/content/${this.$route.params.id}`).put(new Blob([this.$refs.toastuiEditor.invoke("getHtml")]))
      if (this.newThumbnail !== null) {
        var extension = this.newThumbnail.name.split('.').reverse()[0];
        firebase.storage().ref(`game/${this.$route.params.id}/thumbnail/${this.$route.params.id}.${extension}`).put(this.thumbnail)
        PARAMS.thumbnail = `game/${this.$route.params.id}/thumbnail/${this.$route.params.id}.${extension}`
      } else {
        PARAMS.thumbnail = this.originalThumbnailURL
      }
      axios.put(SERVER.BASE + SERVER.GAMEEDIT, PARAMS, this.headersConfig)
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.error(err)
        })
    }
  },
  created() {
    axios.get(SERVER.BASE + SERVER.GAME + this.$route.params.id, this.headersConfig)
      .then(res => {
        console.log(res)
        this.name = res.data.object.name
        const storageRef = firebase.storage().ref()
        storageRef.child('game/15/content/15').getDownloadURL().then(url => {
          var xhr = new XMLHttpRequest();
          if (xhr) {
            xhr.open('GET', url, false)
            xhr.send();
            var result = (xhr.response)
          }
          this.content = result
          this.$refs.toastuiEditor.invoke('setHtml', result)
        })
        this.aim = res.data.object.aim
        this.deadline = res.data.object.deadline.slice(0, 10)
        this.genre = res.data.object.genreName
        this.originalThumbnailURL = res.data.object.thumbnail
        storageRef.child(res.data.object.thumbnail).getDownloadURL()
        .then(url => {
          this.originalThumbnail = url
        })
      })
    .catch(err => console.log(err))
  },
  watch: {
    newThumbnail() {
      if (this.newThumbnail !== null) {
        this.originalThumbnail = null
      }
    },
  },
}
</script>

<style>
</style>