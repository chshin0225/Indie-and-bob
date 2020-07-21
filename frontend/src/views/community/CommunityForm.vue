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
      </v-col>
    </v-row>

    <v-row class="justify-center">
      <v-col class="py-0 text-right" sm=10>
        <v-btn
          @click="createArticle({title: articleData.title, content: articleData.content})"
          :disabled="!isSubmit"
          :class="{disabled : !isSubmit}"
          depressed
          large
          color="accent"
        >글쓰기</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "CommunityForm",

  data() {
    return {
      articleData: {
        title: null,
        content: null
      }
    };
  }
};
</script>

<style>
</style>