<template>
  <v-container>
    <h1 class="text-center">글 수정</h1>

    <!-- title -->
    <v-row class="justify-center my-3">
      <v-col class="py-0" sm="10">
        <v-text-field
          hide-details="true"
          v-model="article.title"
          id="title"
          placeholder="제목"
          type="text"
          outlined
        />
        <small class="error-text primary--text" v-if="error.title">{{error.title}}</small>
      </v-col>
    </v-row>

    <!-- content -->
    <v-row class="justify-center my-3">
      <v-col class="py-0" sm="10">
        <v-textarea
          hide-details="true"
          v-model="article.content"
          id="content"
          placeholder="내용"
          type="text"
          outlined
        ></v-textarea>
        <small class="error-text primary--text" v-if="error.content">{{error.content}}</small>
      </v-col>
    </v-row>

    <!-- btn -->
    <v-row class="justify-center">
      <v-col class="py-0 text-right" sm=10>
        <v-btn
          @click="submit({title: article.title, content: article.content})"
          depressed
          large
          color="accent"
        >수정하기</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: 'CommunityEdit',

  data() {
    return {
      error: {
        title: false,
        content: false,
      },
    };
  },

  computed: {
    ...mapState(['article',])
  },

  methods: {
    ...mapActions(['editArticle', 'getArticle']),
    submit(article) {
      // form check
      if (article.title.length <= 0)
        this.error.title = "제목이 있어야 합니다.";
      else this.error.title = false;

      if (article.content.length <= 0)
        this.error.content = "내용이 있어야 합니다.";
      else this.error.content = false;

      // submit
      if (!this.error.title && !this.error.content) {
        this.editArticle(this.article)
      }
    },
  },

  created() {
    this.getArticle(this.$route.params.articleId)
  },
};
</script>

<style>
</style>