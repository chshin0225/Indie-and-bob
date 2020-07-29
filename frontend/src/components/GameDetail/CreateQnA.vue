<template>
  <v-container>
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
      <v-col class="py-0 text-right" sm="10">
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
</template>

<script>
export default {
  name: "CreateQnA",
};
</script>

<style>
</style>