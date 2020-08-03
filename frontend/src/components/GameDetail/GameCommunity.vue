<template>
  <v-container>

      <!-- comment submit section -->
      <v-row class="justify-center">
        <v-col sm="10">
          <v-text-field
            v-model="communityComment"
            id="communityComment"
            outlined
            required
            hide-detail="true"
            placeholder="응원의 한마디를 해주세요!"
          ></v-text-field>
        </v-col>
        <v-col sm="2">
          <v-btn 
            color="accent" 
            depressed 
            large 
            @click="submitComment"
            class="mt-1"
          >글쓰기</v-btn>
        </v-col>
      </v-row>
      <v-divider></v-divider>

      <!-- comments -->
      <v-list v-if="commentRender&&commentList" two-line>
        <template v-for="article in commentList">
          <v-list-item :key="article.id">
            <v-list-item-content>
              <!-- 내가 쓴 댓글일 경우 -->
              <v-row v-if="article.nickname === myName">
                <v-col cols="10">
                  <v-list-item-title>{{article.content}}</v-list-item-title>
                </v-col>
                <v-spacer></v-spacer>
                <v-col>
                  <v-btn @click="deleteComment(article.commentId)" icon x-small>
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
                {{article.createdAt}} by <span><router-link :to="`/user/mypage/${article.nickname}`" class="text-decoration-none">{{article.nickname}}</router-link></span>
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </template>.
      </v-list>

  </v-container>
</template>

<script>
import axios from "axios";
import SERVER from "../../api/base";

export default {
  name: 'GameCommunity',

  props: ['project'],

  data() {
    return {
      communityComment: "",
      myName: localStorage.getItem('username'),
      commentRender : false,
      commentList: null,
    }
  },

  methods: {
    fetchComments() {
      axios.get(SERVER.BASE + SERVER.GAMECOMMUNITY + '/'+ this.$route.params.id)
        .then((res) => {
          // console.log(res.data.object)
          this.commentList = res.data.object;
        })
        .catch((err) => console.error(err));
    },

    submitComment() {
      let PARAMS = {
        nickname: localStorage.getItem("username"),
        content: this.communityComment,
        gameId : this.$route.params.id
      };
      axios.post(SERVER.BASE + SERVER.GAMECOMMUNITY, PARAMS, this.headersConfig)
        .then(() => {
          // console.log(res.data);
          this.communityComment = '',
          this.fetchComments()
        })
        .catch((err) => console.error(err.data));
    },

    deleteComment(id) {
      axios.delete(SERVER.BASE + SERVER.GAMECOMMUNITY + '/' + id, this.headersConfig)
        .then(() => {
          // console.log(res.data);
          this.fetchComments()
        })
        .catch((err) => console.error(err.data));
    },
  },

  mounted() {
    // console.log(this.project)
    this.fetchComments()
    this.commentRender = true
  },
};
</script>

<style scoped>

</style>