<template>
  <v-container>
    <h1 class="text-center">Community</h1>

    <!-- article list (v-for 적용해야함) -->
    <v-row class="justify-center">
      <v-col cols=10>
        <v-card class="mx-auto" to="/community/1" outlined>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title class="headline mb-1">Lorem ipsum dolor sit amet</v-list-item-title>
              <v-list-item-subtitle class="pa-0 col-10 text-truncate">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec leo sed arcu mattis mollis sodales ac neque. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aliquam vel pharetra leo, sed placerat mi. Curabitur nec pulvinar orci, nec luctus urna. Sed porttitor tempus lacus, sit amet vulputate est pulvinar in. Vestibulum condimentum nibh mi, vel pulvinar ligula fermentum non. Donec sollicitudin aliquet tempor. Cras egestas egestas metus, ut iaculis nisl vestibulum non. Nullam euismod tortor in quam commodo sollicitudin.</v-list-item-subtitle>
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