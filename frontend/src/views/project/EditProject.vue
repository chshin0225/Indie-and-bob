<template>
  <v-container>
    <h1 class="my-5 text-center">프로젝트 수정</h1>

    <v-row class="justify-center mb-7">
      <v-col class="py-0" sm="10">
        <label for="title">프로젝트명</label>
        <v-text-field
          v-model="name"
          id="title"
          placeholder="프로젝트 제목을 입력해주세요"
          hide-details="true"
          class="pt-0"
          required
        ></v-text-field>
        <small class="d-block primary--text" v-if="error.name">{{ error.name }}</small>
      </v-col>
    </v-row>

    <v-row class="justify-center my-7">
      <v-col class="py-0" sm="10">
        <label for="usertype">프로젝트 장르</label>
        <v-select
          hide-details="true"
          :items="genres"
          placeholder="복수 선택도 가능합니다."
          v-model="genre"
          chips
          multiple
          required
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
          required
          height="600px"
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
              required
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
          v-model="newThumbnail"
          placeholder="프로젝트의 썸네일 이미지를 입력해주세요."
          prepend-icon="mdi-camera"
          class="pt-1"
        ></v-file-input>
      </v-col>
    </v-row>

    <!-- original thumbnail image -->
    <v-row v-if="originalThumbnail" class="justify-center">
      <v-col class="py-0" sm="10">
        <h2 for="profile-image" class="text-center">기존에 사용하던 이미지</h2>
        <v-img contain :src="originalThumbnail" :alt="name" height="300" class="mt-4"></v-img>
      </v-col>
    </v-row>

    <v-row class="justify-center mb-10 text-right">
      <v-col class="py-0 my-10" sm="10">
        <v-btn 
          @click="changeProjectInfo" 
          :disabled="!isSubmit" 
          class="accent" 
          depressed
          large
        >
          수정하기
        </v-btn>
      </v-col>
    </v-row>

    <v-overlay :value="overlay">
      <h2 class="mb-5">프로젝트 수정 중...</h2>
      <div class="d-flex justify-center align-center">
        <v-progress-circular indeterminate size="64" color="primary"></v-progress-circular>
      </div>
    </v-overlay>
  </v-container>
</template>


<script>
import { mapGetters, mapState } from "vuex"; 
import router from '../../router'
import SERVER from '../../api/base'

import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import axios from 'axios'
import firebase from 'firebase'
import { Editor } from "@toast-ui/vue-editor";

export default {
  name: 'EditProject',

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
      isSubmit: false,
      today: new Date().toISOString().substr(0, 10),
      error: {
        name: false,
        aim: false,
        deadline: false,
        genre: false,
      },

      editorOptions: {
        hideModeSwitch: true
      },
      newThumbnail: null,
      originalThumbnail: null,
      originalThumbnailURL: null,
      overlay: false,
    };
  },

  computed: {
    ...mapGetters(['headersConfig']),
    ...mapState(['genres', 'genreToId'])
  },

  methods: {
    changeProjectInfo() {
      this.isSubmit = false
      this.overlay = true
      
      this.genre.forEach((item) => {
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
        const storageRef = firebase.storage().ref(`game/${this.$route.params.id}/thumbnail/${this.$route.params.id}.${extension}`).put(this.newThumbnail)
        PARAMS.thumbnail = `game/${this.$route.params.id}/thumbnail/${this.$route.params.id}.${extension}`
        storageRef.on(`state_changed`, snapshot => {
            if ((snapshot.bytesTransferred/snapshot.totalBytes)*100 === 100) {
              axios.put(SERVER.BASE + SERVER.GAMEEDIT, PARAMS, this.headersConfig)
              .then(() => {
                router.push({name: 'ProjectDetail', params:{ id:this.$route.params.id }})
              })
              .catch(err => {
                console.error(err)
              })
            }
          })
      } else {
        PARAMS.thumbnail = this.originalThumbnailURL
        axios.put(SERVER.BASE + SERVER.GAMEEDIT, PARAMS, this.headersConfig)
        .then(() => {
          router.push({name: 'ProjectDetail', params:{ id:this.$route.params.id }})
        })
        .catch(err => {
          console.error(err)
        })
      }
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

  created() {
    axios.get(SERVER.BASE + SERVER.GAME + this.$route.params.id, this.headersConfig)
      .then(res => {
        this.name = res.data.object.name
        const storageRef = firebase.storage().ref()
        storageRef.child(res.data.object.content).getDownloadURL().then(url => {
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
    .catch(err => console.error(err))
  },

  watch: {
    newThumbnail() {
      if (this.newThumbnail !== null) {
        this.originalThumbnail = null
      } else {
        const storageRef = firebase.storage().ref()
        storageRef.child(this.originalThumbnailURL).getDownloadURL()
        .then(url => {
          this.originalThumbnail = url
        })
      }
    },

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
  },
}
</script>

<style>
</style>