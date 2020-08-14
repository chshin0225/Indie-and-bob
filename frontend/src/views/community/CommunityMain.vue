<template>
  <v-container>
    <h1 class="text-center">커뮤니티</h1>
    <!-- <p>{{ articleList }}</p> -->

    <!-- new btn -->
    <v-row class="justify-center">
      <v-col class="text-right py-0" cols=10>
        <v-btn depressed color="accent" to="/community/new">글쓰기</v-btn>
      </v-col>
    </v-row>

    <!-- article list -->
    <v-row class="justify-center" v-for="article in articleList" :key="article.communityId">
      <v-col class="py-0" cols=10>
        <v-card class="mx-auto" :to="`/community/${article.communityId}`" flat>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title class="headline mb-1">{{ article.title }}</v-list-item-title>
              <v-list-item-subtitle class="pa-0 col-10">{{ $moment(article.updatedAt).format('YYYY.MM.DD hh:mm') }} by <span><router-link :to="`/user/mypage/${article.nickname}`" class="text-decoration-none">{{article.nickname}}</router-link></span></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-card>
        <v-divider></v-divider>
      </v-col>
    </v-row>

    <!-- pagination -->
    <div class="text-center my-4">
      <v-pagination
        v-model="page"
        :length="paginationLength"
        color="accent"
      ></v-pagination>
    </div>

  </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: "CommunityMain",

  data() {
    return {
      page: 1,
    }
  },
  
  computed: {
    ...mapState(['articleList', 'articleCount',]),
    paginationLength() {
      return Math.ceil(this.articleCount / 10)
    },
  },

  watch: {
    page: function() {
      this.fetchArticles(this.page)
    },
  },

  methods: {
    ...mapActions(['fetchArticles',]),
  },

  created() {
    this.fetchArticles(this.page)
  },
};
</script>

<style>
</style>