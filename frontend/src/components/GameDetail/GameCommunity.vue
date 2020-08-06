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
                {{ $moment(article.createdAt).format('YYYY.MM.DD HH:MM') }} by <span><router-link :to="`/user/mypage/${article.nickname}`" class="text-decoration-none">{{article.nickname}}</router-link></span>
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

export default {
  name: 'GameCommunity',

  data() {
    return {
      communityComment: "",
      myName: localStorage.getItem('username'),
      commentRender : false,
      commentList: [],
    }
  },

  computed: {
    ...mapGetters(['headersConfig',])
  },

  methods: {
    fetchComments() {
      axios.get(SERVER.BASE + SERVER.GAMECOMMUNITY + '/'+ this.$route.params.id)
        .then((res) => this.commentList = res.data.object)
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
          this.communityComment = '',
          this.fetchComments()
        })
        .catch((err) => console.error(err.data));
    },

    deleteComment(id) {
      axios.delete(SERVER.BASE + SERVER.GAMECOMMUNITY + '?gcId=' + id, this.headersConfig)
        .then(() => {
          // console.log('delete')
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