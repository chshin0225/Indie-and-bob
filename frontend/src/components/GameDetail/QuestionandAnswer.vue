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
              <tr>
                <td>질문질문</td>
                <td>사람ㅅ람</td>
                <td>날짜날짜</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>

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
                <v-col cols="12">
                  <v-text-field 
                    label="제목" 
                    required
                    v-model="questionData.title"
                  ></v-text-field>
                </v-col>

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
                  <!-- <small class="error-text primary--text" v-if="error.content">{{error.content}}</small> -->
                </v-col>

                <v-col class="py-0" cols=12>
                  <v-checkbox
                    v-model="questionData.isSecret"
                    label="비밀글로 하기"
                  ></v-checkbox>
                </v-col>
                
              </v-row>
              <p>{{ questionData }}</p>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" text @click="dialog = false">문의하기</v-btn>
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
import { mapState } from 'vuex'

export default {
    name:'QuestionandAnswer',

    data() {
      return {
        questionForm: false,
        questionData: {
          gameId: this.$route.params.id,
          title: null,
          content: null,
          isSecret: false,
        }
      }
    },

    computed: {
      ...mapState(['project',])
    },

    created() {
      axios.get(SERVER.BASE + "QuestionandAnswer")
      .then(res => {
          this.questions = res.data.questions
      })
      .catch(err => console.error(err))
    },
}
</script>

<style>

</style>