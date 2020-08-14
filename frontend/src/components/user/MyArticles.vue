<template>
  <v-container>
    <div v-if="myArticleList.length > 0">
      <!-- article list -->
      <v-row class="justify-center" v-for="article in myArticleList" :key="article.communityId">
        <v-col class="py-0" cols="10">
          <v-card class="mx-auto" :to="`/community/${article.communityId}`" flat>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title class="headline mb-1">{{ article.title }}</v-list-item-title>
                <v-list-item-subtitle class="pa-0 col-10">
                  {{ $moment(article.updatedAt).format('YYYY.MM.DD hh:mm') }} by
                  <span>
                    <router-link
                      :to="`/user/mypage/${article.nickname}`"
                      class="text-decoration-none"
                    >{{article.nickname}}</router-link>
                  </span>
                </v-list-item-subtitle>
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
    </div>

    <div v-else class="d-flex justify-center">
      <p>아직 작성한 글이 없네요!</p>
    </div>
  </v-container>
</template>

<script>
import SERVER from '../../api/base'

import axios from 'axios'

export default {
  name: "MyArticles",

  data() {
    return {
      page: 1,
      myArticleList: [],
      articleCount: null,
    };
  },

  computed: {
    paginationLength() {
      return Math.ceil(this.articleCount / 10)
    },
  },

  watch: {
    page: function() {
      this.fetchMyArticles(this.page)
    },
  },

  methods: {
    fetchMyArticles(page) {
      axios.get(SERVER.BASE + SERVER.COMMUNITY + `/${this.$route.params.username}/${page}`)
        .then(res => {
          this.myArticleList = res.data.object.list
          this.articleCount = res.data.object.count
        })
        .catch(err => console.error(err))
    },
  },

  created() {
    this.fetchMyArticles(this.page)
  },
};
</script>

<style>
</style>