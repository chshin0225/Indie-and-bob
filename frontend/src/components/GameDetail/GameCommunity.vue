<template>
  <v-container>

      <!-- comment submit section -->
      <v-row class="justify-center" v-if="isLoggedIn">
        <v-col cols=12 sm="10" class="pb-0">
          <v-text-field
            v-model="communityComment"
            id="communityComment"
            outlined
            required
            hide-detail="true"
            placeholder="응원의 한마디를 해주세요!"
            class="align-self-end"
          ></v-text-field>
        </v-col>
        <v-col cols=12 sm="2" class="pt-0 pt-sm-3 d-flex justify-end justify-sm-start">
          <v-btn 
            color="accent" 
            depressed 
            large 
            @click="submitComment"
            class="mt-1"
          >글쓰기</v-btn>
        </v-col>
      </v-row>
      <v-divider v-if="isLoggedIn"></v-divider>

      <!-- comments -->
      <v-list v-if="commentRender && commentList.length > 0" two-line>
        <template v-for="article in commentList">
          <v-list-item :key="article.id">
            <v-list-item-content>
              <!-- 내가 쓴 댓글일 경우 -->
              <v-row v-if="article.nickname === myName">
                <v-col cols="10">
                  <p class="my-0">{{article.content}}</p>
                </v-col>
                <v-spacer></v-spacer>
                <v-col>
                  <v-btn @click="deleteComment(article.gcId)" icon x-small>
                    <i class="fas fa-times fa-lg"></i>
                  </v-btn>
                </v-col>
              </v-row>

              <!-- 내가 쓴 댓글 아닌 경우 -->
              <v-row v-else>
                <v-col cols="11">
                  <v-list-item-title>{{article.content}}</v-list-item-title>
                </v-col>
              </v-row>

              <!-- date/writer -->
              <v-list-item-subtitle>
                {{ $moment(article.createdAt).format('YYYY.MM.DD hh:mm') }} by <span><router-link :to="`/user/mypage/${article.nickname}`" class="text-decoration-none">{{article.nickname}}</router-link></span>
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </template>.
      </v-list>

      <div v-else>
        <p class="py-6 text-center">아직 응원이 없네요. 첫 응원자가 되어주세요!</p>
      </div>

  </v-container>
</template>

<script>
import axios from "axios";
import SERVER from "../../api/base";
import { mapGetters } from 'vuex'

import cookies from 'vue-cookies'

export default {
  name: 'GameCommunity',

  data() {
    return {
      communityComment: "",
      myName: cookies.get('username'),
      commentRender : false,
      commentList: [],
    }
  },

  computed: {
    ...mapGetters(['headersConfig', 'isLoggedIn',])
  },

  methods: {
    fetchComments() {
      axios.get(SERVER.BASE + SERVER.GAMECOMMUNITY + '/'+ this.$route.params.id)
        .then((res) => this.commentList = res.data.object)
        .catch((err) => console.error(err));
    },

    submitComment() {
      if (this.communityComment.trim().length > 0){
        let PARAMS = {
          nickname: cookies.get('username'),
          content: this.communityComment,
          gameId : this.$route.params.id
        };
        axios.post(SERVER.BASE + SERVER.GAMECOMMUNITY, PARAMS, this.headersConfig)
          .then(() => {
            this.communityComment = '',
            this.fetchComments()
          })
          .catch((err) => console.error(err.data));
      } else {
        alert("내용을 작성해주세요.")
      }
    },

    deleteComment(id) {
      axios.delete(SERVER.BASE + SERVER.GAMECOMMUNITY + '?gcId=' + id, this.headersConfig)
        .then(() => {
          this.fetchComments()
        })
        .catch((err) => console.error(err.data));
    },
  },

  mounted() {
    this.fetchComments()
    this.commentRender = true
  },
};
</script>

<style scoped>

</style>