<template>
  <v-container>
    <h1 class="text-center">Community</h1>
    <!-- <p>{{ articleList }}</p> -->

    <!-- article list (v-for 적용해야함) -->
    <v-row class="justify-center" v-for="article in articleList" :key="article.communityId">
      <v-col class="py-1" cols=10>
        <v-card class="mx-auto" :to="`/community/${article.communityId}`" outlined>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title class="headline mb-1">{{ article.title }}</v-list-item-title>
              <v-list-item-subtitle class="pa-0 col-10">{{ $moment(article.updatedAt).format('YYYY.MM.DD HH:MM') }} by <span><router-link :to="`/user/mypage/${article.nickname}`" class="text-decoration-none">{{article.nickname}}</router-link></span></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-col>
    </v-row>

    <!-- new btn -->
    <v-row class="justify-center">
      <v-col class="text-right" cols=10>
        <v-btn depressed color="accent" to="/community/new">New</v-btn>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: "CommunityMain",
  
  computed: {
    ...mapState(['articleList',])
  },

  methods: {
    ...mapActions(['fetchArticles',])
  },

  created() {
    // 모든 글 가져오기 
    this.fetchArticles()
  },
};
</script>

<style>
</style>