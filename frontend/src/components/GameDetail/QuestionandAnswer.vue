<template>
  <v-container>

    <v-row>
      <v-col cols=12>
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">제목</th>
                <th class="text-left">작성자</th>
                <th class="text-left">작성일</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="question in questionList" :key="question.qnaId">
                <td><i class="fas fa-lock mr-2" v-if="question.secret"></i>{{ question.title }}</td>
                <td><span><router-link :to="`/user/mypage/${question.nickname}`" class="text-decoration-none">{{question.nickname}}</router-link></span></td>
                <td>날짜날짜</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
        <p>{{ questionList }}</p>

      </v-col>
    </v-row>

    <v-row class="justify-end">
      <v-dialog v-model="questionForm" persistent max-width="600px">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="accent"
            depressed
            v-bind="attrs"
            v-on="on"
            class="mr-3"
          >
            문의하기
          </v-btn>
        </template>

        <v-card>
          <v-card-title>
            <span class="headline">문의하기</span>
          </v-card-title>
          <v-card-text class="pb-0">
            <v-container class="pt-0">
              <v-row>
                <!-- title -->
                <v-col cols="12" class="mb-5">
                  <v-text-field 
                    label="제목" 
                    required
                    v-model="questionData.title"
                    hide-details="true"
                  ></v-text-field>
                  <small class="error-text primary--text" v-if="error.title">{{error.title}}</small>
                </v-col>

                <!-- content -->
                <v-col class="py-0" cols=12>
                  <v-textarea
                    label="내용" 
                    required
                    v-model="questionData.content"
                    hide-details="true"
                    outlined
                    id="content"
                    placeholder="개발자에게 문의사항을 남겨주세요."
                    type="text"
                  ></v-textarea>
                  <small class="error-text primary--text" v-if="error.content">{{error.content}}</small>
                </v-col>

                <v-col class="py-0" cols=12>
                  <v-checkbox
                    v-model="questionData.secret"
                    label="비밀글로 하기"
                  ></v-checkbox>
                </v-col>
                
              </v-row>
              <p>{{ questionData }}</p>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="accent" depressed @click="submitQuestion()">문의하기</v-btn>
            <v-btn color="primary" text @click="questionForm = false">닫기</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>



    <!-- <v-expansion-panels>
      <v-expansion-panel>
        <v-expansion-panel-header>
            title
            content
        </v-expansion-panel-header>
        <v-expansion-panel-content>
        answer
        </v-expansion-panel-content>
        <v-expansion-panel-content>
        </v-expansion-panel-content>

      </v-expansion-panel>
    </v-expansion-panels> -->
  </v-container>
</template>

<script>
import axios from 'axios';
import SERVER from '../../api/base';
import { mapState, mapGetters } from 'vuex'

export default {
    name:'QuestionandAnswer',

    data() {
      return {
        questionForm: false,
        questionData: {
          gameId: this.$route.params.id,
          title: '',
          content: '',
          secret: false,
        },
        error: {
          title: false,
          content: false,
        },
        questionList: [],
      }
    },

    computed: {
      ...mapState(['project',]),
      ...mapGetters(['headersConfig', 'isLoggedIn'])
    },

    methods: {
      submitQuestion() {
        if (this.questionData.title.length <= 0) {
          this.error.title = "제목이 있어야 합니다."
        } else this.error.title = false

        if (this.questionData.content.length <= 0) {
          this.error.content = "내용이 있어야 합니다."
        } else this.error.content = false

        if (!this.error.title && !this.error.content) {
          axios.post(SERVER.BASE + SERVER.CREATEQNA, this.questionData, this.headersConfig)
            .then(() => {
              alert('문의사항을 개발자에게 보냈습니다.')
              this.questionData.title = ''
              this.questionData.content = ''
              this.questionData.secret = false
              this.questionForm = false
              this.fetchQuestions()
            })
            .catch(err => console.error(err))
        }
      },

      fetchQuestions() {
        if (this.isLoggedIn) {
          axios.get(SERVER.BASE + SERVER.QNA + this.$route.params.id, this.headersConfig)
            .then(res => {
              console.log('Login', this.isLoggedIn)
              this.questionList = res.data.object
            })
            .catch(err => console.error(err))
        } else {
          axios.get(SERVER.BASE + SERVER.NOSECRETQNA + this.$route.params.id)
            .then(res => {
              console.log('Login', this.isLoggedIn)
              this.questionList = res.data.object
            })
            .catch(err => console.error(err))
        }
      },
    },

    created() {
      this.fetchQuestions()
    },
}
</script>

<style>

</style>