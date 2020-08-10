<template>
  <v-container>

    <div v-if="articleDataFetched">
      <!-- article header -->
      <v-row>
        <v-col>
          <h1>{{ article.title }}</h1>
        </v-col>
        <v-col class="d-flex justify-end align-end">
          <p class="mb-0">
            {{ $moment(article.createdAt).format('YYYY.MM.DD hh:mm') }} by <span><router-link :to="`/user/mypage/${article.nickname}`" class="text-decoration-none">{{article.nickname}}</router-link></span>
          </p>
        </v-col>
      </v-row>
      <hr>

      <!-- article content -->
      <v-row>
        <v-col>
          <p>
            {{ article.content }}
            <!-- {{ article }} -->
          </p>
        </v-col>
      </v-row>

      <!-- buttons -->
      <v-row v-if="article.nickname === myName">
        <v-col class="d-flex justify-end">
          <v-btn :to="`/community/edit/${article.communityId}`" class="mr-2" color="accent" text small>수정</v-btn>
          <v-btn @click="deleteArticle(article.communityId)" color="accent" text small>삭제</v-btn>
        </v-col>
      </v-row>
    </div>
    
    <hr>

    <!-- comment section -->
    <v-row class="justify-center">
      <v-col cols=12 sm="10" class="pb-0">
        <v-text-field
          v-model="comment"
          id="comment"
          outlined
          hide-detail="true"
          placeholder="댓글"
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
    
    <!-- comment list -->
    <v-row>
      <v-col class="py-0">
        <v-list v-if="commentList.length > 0" class="py-0" two-line>
          <template v-for="comment in commentList">
            <v-list-item :key="comment.id">
              <v-list-item-content>
                <!-- 내가 쓴 댓글일 경우 -->
                <v-row v-if="comment.nickname === myName">
                  <v-col cols="10">
                    <p class="my-0">{{comment.content}}</p>
                  </v-col>
                  <v-spacer></v-spacer>
                  <v-col>
                    <v-btn @click="deleteComment(comment.ccId)" icon x-small>
                      <i class="fas fa-times fa-lg"></i>
                    </v-btn>
                  </v-col>
                </v-row>

                <!-- 내가 쓴 댓글 아닌 경우 -->
                <v-row v-else>
                  <v-col cols="11">
                    <v-list-item-title>{{comment.content}}</v-list-item-title>
                  </v-col>
                </v-row>

                <!-- date/writer -->
                <v-list-item-subtitle>
                  {{ $moment(comment.createdAt).format('YYYY.MM.DD hh:mm') }} by <span><router-link :to="`/user/mypage/${comment.nickname}`" class="text-decoration-none">{{comment.nickname}}</router-link></span>
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </template>.
        </v-list>
        <div v-else>
          <p class="py-3 text-center">아직 댓글이 없네요. 첫 댓글을 달아주세요!</p>
        </div>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex'
import axios from 'axios'
import SERVER from '../../api/base'

export default {
  name: 'CommunityArticle',

  data() {
    return {
      comment: null,
      commentList: [],
      myName: localStorage.getItem('username'),
    }
  },

  computed: {
    ...mapState(['article',]),
    ...mapGetters(['headersConfig', 'articleDataFetched',])
  },

  methods: {
    ...mapActions(['getArticle', 'deleteArticle']),

    submitComment() {
      const PARAMS = {
        content: this.comment,
        communityId: this.$route.params.articleId
      }
      axios.post(SERVER.BASE + SERVER.COMMUNITYCOMMENT, PARAMS, this.headersConfig)
        .then(() => {
          this.comment = null
          this.fetchComments(this.$route.params.articleId)
        })
        .catch(err => console.error(err))
    },

    fetchComments(articleId) {
      axios.get(SERVER.BASE + SERVER.COMMUNITYCOMMENT + `/${articleId}`)
        .then(res => this.commentList = res.data.object)
        .catch(err => console.error(err))
    },

    deleteComment(ccId) {
      axios.delete(SERVER.BASE + SERVER.COMMUNITYCOMMENT + `/${ccId}`)
        .then(() => this.fetchComments(this.$route.params.articleId))
        .catch(err => console.error(err))
    }
  },

  created() {
    this.getArticle(this.$route.params.articleId)
    this.fetchComments(this.$route.params.articleId)
  },
}
</script>

<style>

</style>