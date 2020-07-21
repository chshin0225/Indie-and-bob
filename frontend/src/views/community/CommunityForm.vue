<template>
  <v-container>
    <h1 class="text-center">New</h1>

    <!-- title -->
    <v-row class="justify-center">
      <v-col class="py-0" sm="10">
        <v-text-field
          class="my-3"
          hide-details="true"
          v-model="articleData.title"
          id="title"
          placeholder="제목"
          type="text"
          outlined
        />
        <div class="error-text primary--text" v-if="error.title">{{error.title}}</div>
      </v-col>
    </v-row>

    <!-- content -->
    <v-row class="justify-center">
      <v-col class="py-0" sm="10">
        <v-textarea
          class="my-3"
          hide-details="true"
          v-model="articleData.content"
          id="content"
          placeholder="내용"
          type="text"
          outlined
        ></v-textarea>
        <div class="error-text primary--text" v-if="error.content">{{error.content}}</div>
      </v-col>
    </v-row>

    <!-- btn -->
    <v-row class="justify-center">
      <v-col class="py-0 text-right" sm=10>
        <v-btn
          @click="submit({title: articleData.title, content: articleData.content})"
          depressed
          large
          color="accent"
        >글쓰기</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'CommunityForm',

  data() {
    return {
      articleData: {
        title: '',
        content: ''
      },
      error: {
        title: false,
        content: false,
      },
    };
  },

  methods: {
    ...mapActions(['createArticle',]),
    submit(articleData) {
      // form check
      if (articleData.title.length <= 0)
        this.error.title = "제목이 있어야 합니다.";
      else this.error.title = false;

      if (articleData.content.length <= 0)
        this.error.content = "내용이 있어야 합니다.";
      else this.error.content = false;

      console.log(articleData)
      console.log(this.error)

      // submit
      if (!this.error.title && !this.error.content) {
        this.createArticle(articleData)
      }

    },
  },
};
</script>

<style>
</style>