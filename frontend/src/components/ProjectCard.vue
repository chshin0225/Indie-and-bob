<template>
  <v-container>

        <v-card>
          <v-list-item>
            <v-list-item-avatar color="secondary"></v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title class="headline">프로젝트 이름</v-list-item-title>
              <v-list-item-subtitle>개발자 이름</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
          <v-img src="https://cdn.vuetifyjs.com/images/cards/mountain.jpg" height="194"></v-img>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon>
              <v-icon>mdi-heart</v-icon>
            </v-btn>
            <v-btn icon>
              <v-icon>mdi-share-variant</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>

  </v-container>
</template>

<script>
export default {
  name: "ProjectCard"
};
</script>

<style>
</style>