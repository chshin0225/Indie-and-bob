<template>
  <v-container>

    <v-row v-if="questionList.length > 0">
      <v-col cols=12>
        <!-- questions -->
        <v-row>
          <v-col cols=6>
            <span class="ml-7 font-weight-bold">제목</span>
          </v-col>
          <v-col class="pl-0">
            <span class="font-weight-bold">작성자</span>
          </v-col>
          <v-col>
            <span class="font-weight-bold">작성일</span>
          </v-col>
        </v-row>
        <v-divider></v-divider>

        <v-expansion-panels accordion flat>
          <v-expansion-panel v-for="question in questionList" :key="question.qnaId">
            <v-expansion-panel-header @click="answerToQuestion = ''">
              <v-row>
                <v-col cols=6>
                  <i class="fas fa-lock mr-2" v-if="question.secret"></i>{{ question.title }}
                      <v-chip
                        v-if="question.answer"
                        color="secondary"
                        x-small
                        class="black--text ml-1"
                      >
                        답변 완료
                      </v-chip>
                </v-col>
                <v-col>
                  <span><router-link :to="`/user/mypage/${question.nickname}`" class="text-decoration-none">{{question.nickname}}</router-link></span>
                </v-col>
                <v-col>
                  <span class="pl-3">{{ $moment(question.createdAt).format('YYYY.MM.DD') }}</span>
                </v-col>
              </v-row>
            </v-expansion-panel-header>

            <v-expansion-panel-content>
              <!-- 개발자 아닌 사람이 보는 경우 -->
              <div v-if="!isDeveloper">
                <v-card flat>
                  <v-card-subtitle class="pb-1 pt-0"><span class="font-weight-bold">Q.</span></v-card-subtitle>
                  <v-card-text>{{ question.content }}</v-card-text>
                </v-card>
                <v-card flat>
                  <v-card-subtitle class="pb-1"><span class="font-weight-bold">A.</span></v-card-subtitle>
                  <v-card-text v-if="question.answer">{{ question.answer }}</v-card-text>
                  <v-card-text v-else>아직 답변이 없네요!</v-card-text>
                  <v-card-actions class="pr-0 py-0">
                      <v-spacer></v-spacer>
                      <v-btn
                       v-if = "question.nickname === currentUser"
                        color="primary" 
                        text 
                        @click="deleteQuestion(question.qnaId)"
                      >삭제</v-btn>
                  </v-card-actions>
                </v-card>
              </div>

              <!-- 개발자가 보는 경우 -->
              <div v-else>
                <v-card flat>
                  <v-card-subtitle class="pb-1 pt-0"><span class="font-weight-bold">Q.</span></v-card-subtitle>
                  <v-card-text>{{ question.content }}</v-card-text>
                </v-card>
                <v-card flat>
                  <v-card-subtitle class="pb-1"><span class="font-weight-bold">A.</span></v-card-subtitle>
                  <!-- 답변 완료 -->
                  <v-card-text v-if="question.answer" class="pb-0">
                    {{ question.answer }}
                    <v-card-actions class="pr-0 py-0">
                      <v-spacer></v-spacer>
                      <v-btn
                        color="primary" 
                        text 
                        @click="deleteQuestion(question.qnaId)"
                      >삭제</v-btn>
                    </v-card-actions>
                  </v-card-text>

                  <!-- 답변 작성해야함 -->
                  <v-card-text class="pb-0" v-else>
                    <v-textarea
                      v-model="answerToQuestion"
                      hide-details="true"
                      outlined
                      id="content"
                      placeholder="답변 작성"
                      type="text"
                    ></v-textarea>
                    <v-card-actions class="pr-0">
                      <v-spacer></v-spacer>
                      <v-btn 
                        color="primary" 
                        depressed 
                        @click="submitAnswer({'gameId': question.gameId, 'title': question.title, 'content': question.content, 'secret': question.secret, 'answer': answerToQuestion, 'qnaId': question.qnaId})"
                      >답변</v-btn>
                      <v-btn
                        color="primary" 
                        text 
                        @click="deleteQuestion(question.qnaId)"
                      >삭제</v-btn>
                    </v-card-actions>
                  </v-card-text>
                </v-card>
              </div>

            </v-expansion-panel-content>
            <v-divider></v-divider>

          </v-expansion-panel>
        </v-expansion-panels>
        
        <!-- pagination --> 
        <div class="text-center mt-3">
          <v-pagination
            v-if="questionList.length > 0"
            v-model="page"
            :length="paginationLength"
            color="accent"
          ></v-pagination>
        </div>
      </v-col>
    </v-row>

    <div v-else>
      <p class="py-6 text-center">아직 문의사항이 없네요!</p>
    </div>

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

                  <v-tooltip right>
                    <template v-slot:activator="{ on, attrs }">
                      <v-col class="py-0" cols="auto" v-bind="attrs" v-on="on">
                        <v-checkbox
                          v-model="questionData.secret"
                          label="비밀글로 하기"
                        ></v-checkbox>
                      </v-col>
                    </template>
                    <span>비밀글로 하면 문의 내용을 개발자와 나만 볼 수 있습니다.</span>
                  </v-tooltip>
              </v-row>
              <!-- <p>{{ questionData }}</p> -->
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="accent" depressed @click="submitQuestion">문의하기</v-btn>
            <v-btn color="primary" text @click="resetQuestionData">닫기</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>

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
        answerToQuestion: '',
        error: {
          title: false,
          content: false,
        },
        questionList: [],
        questionCount: null,
        page: 1,

        isDeveloper: false,
        currentUser: localStorage.getItem('username'),
      }
    },

    computed: {
      ...mapState(['project',]),
      ...mapGetters(['headersConfig', 'isLoggedIn']),
      paginationLength() {
        return Math.ceil(this.questionCount / 10)
      },
    },

    watch: {
      page: function() {
        this.fetchQuestions(this.page)
      },
    },

    methods: {
      resetQuestionData() {
        this.questionData.title = ''
        this.questionData.content = ''
        this.questionData.secret = false
        this.questionForm = false
      },

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
              this.fetchQuestions(1)
            })
            .catch(err => console.error(err))
        }
      },

      fetchQuestions(page) {
        if (this.isLoggedIn) {
          axios.get(SERVER.BASE + SERVER.QNA + this.$route.params.id + `/${page}`, this.headersConfig)
            .then(res => {
              // console.log(res.data.object)
              this.questionList = res.data.object
              this.questionCount = res.data.object.length
            })
            .catch(err => console.error(err))
        } else {
          axios.get(SERVER.BASE + SERVER.NOSECRETQNA + this.$route.params.id + `/${this.page}`)
            .then(res => {
              // console.log('Login', this.isLoggedIn)
              this.questionList = res.data.object
            })
            .catch(err => console.error(err))
        }
      },

      submitAnswer(answerData) {
        axios.put(SERVER.BASE + SERVER.ANSWER, answerData, this.headersConfig)
          .then(() => {
            this.answerToQuestion = ''
            this.fetchQuestions(this.page)
          })
          .catch(err => console.error(err))
      },

      deleteQuestion(qnaId) {
        axios.delete(SERVER.BASE + SERVER.DELETEQNA + qnaId)
          .then(() => this.fetchQuestions(this.page))
          .catch(err => console.error(err))
      },
    },

    created() {
      this.fetchQuestions(this.page)

      // 현재 유저가 개발자인지 확인
      if (this.project.nickname === localStorage.getItem('username')) {
        this.isDeveloper = true
      }
    },
}
</script>

<style>

</style>